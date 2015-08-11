package me.yangbajing.emailserver.service

import javax.jms._

import com.typesafe.scalalogging.StrictLogging
import me.yangbajing.emailserver.common.enums.MimeType
import me.yangbajing.emailserver.common.settings.SettingActivemq
import me.yangbajing.emailserver.domain.SendEmail
import me.yangbajing.emailserver.util.Utils
import org.apache.activemq.ActiveMQConnectionFactory

/**
 * 消息队列消费者
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-11.
 */
class MQConsumerService(setting: SettingActivemq, emailService: EmailService) extends StrictLogging {

  import Utils.system.dispatcher
  import Utils.timeout

  val connFactory = new ActiveMQConnectionFactory(setting.url)
  val conn =
    (for {
      userName <- setting.userName
      password <- setting.password
    } yield connFactory.createConnection("admin", "admin")) getOrElse connFactory.createConnection()

  def start() {

    conn.setClientID("ConsumerSynchronous")
    conn.start()

    val session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)
    val queue = session.createQueue(setting.emailQueueName)
    val consumer = session.createConsumer(queue)

    val listener = new MessageListener {
      override def onMessage(message: Message): Unit = message match {
        case msg: MapMessage => {
          val subject = msg.getString("subject")
          val sender = msg.getString("sender")
          val content = msg.getString("content")
          val to = msg.getString("to").split(";")
          val mimeType = Option(msg.getString("mimeType")).map(MimeType.withName).getOrElse(MimeType.Text)
          val sendEmail = SendEmail(sender, subject, to, content, None, mimeType)
          emailService.sendEmail(sendEmail).onComplete(result => logger.debug(result.toString))
        }

        case txt: TextMessage => {
          val replyProducer = session.createProducer(txt.getJMSReplyTo)
          replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT)

          logger.debug("Received message: " + txt.getText)

          val replyMessage = session.createTextMessage("Yes I received your message!")
          replyMessage.setJMSCorrelationID(txt.getJMSCorrelationID)

          logger.debug("Reply sent!")

          replyProducer.send(replyMessage)
        }

        case _ =>
          logger.error("Unhandled Message Type: " + message)
      }
    }

    consumer.setMessageListener(listener)
  }

  def close() {
    conn.close()
  }
}

package me.yangbajing.emailserver.demo

import javax.jms._

import org.apache.activemq.ActiveMQConnectionFactory

/**
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-11.
 */
object ConsumerSynchronous extends App {
  val activeMqUrl = "tcp://192.168.31.116:61616"

  val connFactory = new ActiveMQConnectionFactory(activeMqUrl)
  val conn = connFactory.createConnection()
  conn.setClientID("ConsumerSynchronous")
  conn.start()

  println("Started")

  val session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)
  val queue = session.createQueue("EmailQueue")
  val consumer = session.createConsumer(queue)

  val listener = new MessageListener {
    override def onMessage(message: Message): Unit = message match {
      case txt: TextMessage => {
        val replyProducer = session.createProducer(txt.getJMSReplyTo)
        replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT)

        println("Received message: " + txt.getText)

        val replyMessage = session.createTextMessage("Yes I received your message!")
        replyMessage.setJMSCorrelationID(txt.getJMSCorrelationID)

        println("Reply sent!")

        replyProducer.send(replyMessage)
      }

      case _ =>
        throw new RuntimeException("Unhandled Message Type: " + message)
    }
  }

  consumer.setMessageListener(listener)
}

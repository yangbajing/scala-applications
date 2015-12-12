package me.yangbajing.emailserver.demo

import javax.jms.{DeliveryMode, Session, TextMessage}

import org.apache.activemq.ActiveMQConnectionFactory

import scala.util.Random

/**
 * 邮件生产者
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-11.
 */
object EmailProducers extends App {
  val activeMqUrl = "tcp://192.168.31.116:61616"

  val connFactory = new ActiveMQConnectionFactory(activeMqUrl)
  val conn = connFactory.createConnection()
  conn.setClientID("ProducerEmail")
  conn.start()

  val session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)
  val sendQueue = session.createQueue("EmailQueue")
//  val replyQueue = session.createQueue("ReplyToSynchronousMsgQueue")

  val producer = session.createProducer(sendQueue)
  producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT)

  val correlationId = new Random().nextString(32)
//  val replyConsumer = session.createConsumer(replyQueue, s"JMSCorrelationID = '$correlationId'")

  val mapMessage = session.createMapMessage()
//  mapMessage.setJMSReplyTo(replyQueue)
  mapMessage.setJMSCorrelationID(correlationId)

  mapMessage.setString("subject", "Activemq jms 邮件测试")
  mapMessage.setString("to", "yang.xunjing@qq.com;jing.yang@socialcredits.cn")
  mapMessage.setString("content", "Activemq jms 邮件测试的内容")
  mapMessage.setString("sender", "Info@socialcredits.cn")
  mapMessage.setString("mimeType", "text")

  println("Sending message...")

  producer.send(mapMessage)

  println("Waiting for reply...")

//  val reply = replyConsumer.receive(1000)
//  replyConsumer.close()

//  reply match {
//    case txt: TextMessage => println("Received reply: " + txt.getText)
//    case _ => throw new RuntimeException("Invalid Response: " + reply)
//  }

  conn.close()
}

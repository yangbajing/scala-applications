package me.yangbajing.emailserver.demo

import javax.jms.{TextMessage, DeliveryMode, Session}

import org.apache.activemq.ActiveMQConnectionFactory

import scala.util.Random

/**
 * Active Producer
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-11.
 */
object ProducerSynchronous extends App {
  val activeMqUrl = "tcp://192.168.31.116:61616"

  val connFactory = new ActiveMQConnectionFactory(activeMqUrl)
  val conn = connFactory.createConnection()
  conn.setClientID("ProducerSynchronous")
  conn.start()

  val session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)
  val sendQueue = session.createQueue("EmailQueue")
  val replyQueue = session.createQueue("ReplyToSynchronousMsgQueue")

  val producer = session.createProducer(sendQueue)
  producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT)

  val correlationId = new Random().nextString(32)
  val replyConsumer = session.createConsumer(replyQueue, s"JMSCorrelationID = '$correlationId'")

  val textMessage = session.createTextMessage("Hello, please reply immediately to my message!")
  textMessage.setJMSReplyTo(replyQueue)
  textMessage.setJMSCorrelationID(correlationId)

  println("Sending message...")

  producer.send(textMessage)

  println("Waiting for reply...")

  val reply = replyConsumer.receive(1000)
  replyConsumer.close()

  reply match {
    case txt: TextMessage => println("Received reply: " + txt.getText)
    case _ => throw new RuntimeException("Invalid Response: " + reply)
  }

  conn.close()
}

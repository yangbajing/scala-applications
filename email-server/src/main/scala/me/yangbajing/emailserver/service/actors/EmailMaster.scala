package me.yangbajing.emailserver.service.actors

import akka.actor.{Actor, Props}
import me.yangbajing.emailserver.domain.{EmailSender, GetEmailSenders, SendEmail}

/**
 * 邮件发送Master
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-11.
 */
class EmailMaster(initEmailSenders: Seq[EmailSender]) extends Actor {
  val groups = initEmailSenders.groupBy(_.group)
  val emailSenders = initEmailSenders.map(s => s.userName -> s).toMap

  //  // TODO 自定义监管策略
  //  override def supervisorStrategy: SupervisorStrategy = super.supervisorStrategy

  override def preStart(): Unit = {
    for ((group, emailSenders) <- groups) {
      context.actorOf(
        EmailGroupActor.props(emailSenders.map(s => s.userName -> s).toMap),
        EmailGroupActor.ACTOR_NAME_PREFIX + group)
    }
  }

  override def receive = {
    case sendEmail: SendEmail => {
      val userName = sendEmail.sender
      emailSenders.get(userName) match {
        case Some(emailSender) =>
          context.child(EmailGroupActor.ACTOR_NAME_PREFIX + emailSender.group) match {
            case Some(ref) =>
              ref ! sendEmail
              sender() ! Right("email added send queue")

            case None =>
              sender() ! Left(s"actor: ${EmailGroupActor.ACTOR_NAME_PREFIX}-${emailSender.group} not found")
          }

        case None =>
          sender() ! Left(s"userName: $userName not found")
      }
    }

    case GetEmailSenders =>
      sender() ! initEmailSenders
  }
}

object EmailMaster {
  def props(initEmailSender: Seq[EmailSender]) = Props(new EmailMaster(initEmailSender))
}
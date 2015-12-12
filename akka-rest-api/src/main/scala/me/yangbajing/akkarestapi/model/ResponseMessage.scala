package me.yangbajing.akkarestapi.model

/**
 * HTTP 响应消息
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
case class ResponseMessage(errcode: Int = 0, errmsg: String = "") extends RuntimeException(errmsg)

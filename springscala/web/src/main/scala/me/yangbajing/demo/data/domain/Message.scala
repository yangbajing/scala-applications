package me.yangbajing.demo.data.domain

import me.yangbajing.demo.common.Utils

import scala.beans.BeanProperty

case class Message(name: String, age: Int) {
  @BeanProperty
  val sign: String = Utils.randomString(16)
}


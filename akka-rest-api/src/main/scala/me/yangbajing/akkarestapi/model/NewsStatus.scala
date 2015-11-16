package me.yangbajing.akkarestapi.model

import java.time.LocalDateTime

/**
 * 新闻服务状态
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
case class NewsStatus(now: LocalDateTime, status: String)

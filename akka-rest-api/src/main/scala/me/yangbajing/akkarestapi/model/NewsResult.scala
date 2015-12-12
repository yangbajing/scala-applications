package me.yangbajing.akkarestapi.model

import java.time.LocalDateTime

/**
 * News Result
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
case class NewsResult(key: String,
                      method: String,
                      count: Int,
                      items: Seq[NewsItem])

case class NewsItem(key: String,
                    time: LocalDateTime,
                    title: String,
                    author: String,
                    summary: String,
                    content: String)

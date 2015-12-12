package me.yangbajing.akkarestapi.service

import me.yangbajing.akkarestapi.model.{NewsItem, NewsResult, NewsStatus, ResponseMessage}
import me.yangbajing.akkarestapi.repo.NewsRepo
import me.yangbajing.akkarestapi.utils.Utils

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

/**
 * News Service
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
class NewsService private() {
  val newsRepo = new NewsRepo()

  def persistNewsItem(item: NewsItem)(implicit ec: ExecutionContext): Future[ResponseMessage] = {
    newsRepo.insert(item)
  }

  /**
   * 获取新闻
   * @param key 搜索关键词
   * @param method 搜索方式：S: 摘要，F: 全文
   * @return
   */
  def findNews(key: String,
               method: String)(implicit ec: ExecutionContext): Future[NewsResult] = Future {
    NewsResult(key, method, Random.nextInt(), Nil)
  }

  /**
   * 获取当前新闻服务状态
   * @return
   */
  def getStatus()(implicit ec: ExecutionContext): Future[NewsStatus] = Future {
    NewsStatus(Utils.now, "ok")
  }

}

object NewsService {
  def apply() = new NewsService()
}

# Email服务器

Email发送服务器

## 配置

推荐在启动时使用 `-Dapplication.file=/usr/app/etc/emailserver/application.conf` 指定配置文件


## 编译与运行

```
# 编译
./sbt assembly

# 运行
java -Dapplication.file=/usr/app/etc/emailserver/application.conf -jar target/scala-2.11/email-server.jar
```


## 测试REST服务

```
# 查询存在的邮箱发送者：
curl http://localhost:9999/email/users

# 发送测试邮件
curl -v -XPOST -H "Content-Type: application/json" \
  -d '{"userName":"Info@socialcredits.cn", "subject":"测试邮件","to":["yangbajing@gmail.com", "jing.yang@socialcredits.cn"],"content":"测试邮件内容咯~"}' \
  http://localhost:9999/email/send

```


## 使用JMS发送邮件

**安装`activemq`**

Downloads：[http://mirrors.hust.edu.cn/apache/activemq/5.11.1/apache-activemq-5.11.1-bin.tar.gz](http://mirrors.hust.edu.cn/apache/activemq/5.11.1/apache-activemq-5.11.1-bin.tar.gz)

```
tar zxf ~/Downloads/apache-activemq-5.11.1-bin.tar.gz
cd apache-activemq-5.11.1/
./bin/activemq-admin start
```

`activemq`管理控制台地址：[http://localhost:8161/admin/](http://localhost:8161/admin/)，账号：`admin`，密码：`admin`。

JMS TCP地址：`tcp://localhost:61616`

**生产邮件发送请求**

修改[EmailProducers.scala](https://github.com/yangbajing/scala-applications/blob/master/email-server/src/main/scala/me/yangbajing/emailserver/demo/EmailProducers.scala)的`activeMqUrl`及`mapMessage`参数，执行`EmailProducers`生产一个邮件发送请求。


## changelog

**ver: 0.0.2 2015-08-11**

实现消费`activemq jms`邮件发送消息

**ver: 0.0.1 2015-08-11**

实现基于`REST API`的邮件发送功能。

# Email服务器

Email发送服务器

推荐在启动时使用 `-Dapplication.file=/path/application.conf` 推定配置文件

```
./sbt assembly
java -Dapplication.file=/usr/app/etc/emailserver/application.conf -jar target/scala-2.11/email-server.jar
```

测试服务

```
# 查询存在的邮箱发送者：
curl http://localhost:9999/email/users

# 发送测试邮件
curl -v -XPOST -H "Content-Type: application/json" \
  -d '{"subject":"测试邮件","to":["yangbajing@gmail.com", "jing.yang@socialcredits.cn"],"content":"测试邮件内容咯~"}' \
  http://localhost:9999/email/send
```

## changelog

**ver: 0.0.1 2015-08-11**

实现基于`REST API`的邮件发送功能。

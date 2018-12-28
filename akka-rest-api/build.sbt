description := "Akka REST API"

version := "0.0.2"

homepage := Some(
  new URL(
    "https://github.com/yangbajing/scala-applications/tree/master/akka-rest-api"
  )
)

organization := "me.yangbajing"

organizationHomepage := Some(
  new URL("https://github.com/yangbajing/scala-applications")
)

startYear := Some(2015)

scalaVersion := "2.11.12"

scalacOptions ++= Seq(
  "-encoding",
  "utf8",
  "-unchecked",
  "-feature",
  "-deprecation"
)

javacOptions ++= Seq(
  "-encoding",
  "utf8",
  "-Xlint:unchecked",
  "-Xlint:deprecation"
)

javaOptions += "-Dproject.base=" + baseDirectory.value

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases",
  "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"
)

assemblyJarName in assembly := "akka-rest-api.jar"

mainClass in assembly := Some("me.yangbajing.akkarestapi.Main")

libraryDependencies ++= Seq(
  _mongoScala,
  _json4sJackson,
  _redisclient,
  _akkaHttpCore,
  _akkaActor,
  _akkaSlf4j,
  _logback,
  _typesafeConfig,
  _scalaLogging,
  _scalatest
)

lazy val _scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.0.4"
lazy val _scalatest = "org.scalatest" %% "scalatest" % "2.2.5" % "test"
lazy val _typesafeConfig = "com.typesafe" % "config" % "1.3.0"
lazy val _scalaLogging = ("com.typesafe.scala-logging" %% "scala-logging" % "3.1.0")
  .exclude("org.scala-lang", "scala-library")
  .exclude("org.scala-lang", "scala-reflect")

lazy val verAkka = "2.5.19"

lazy val _akkaHttpCore = "com.typesafe.akka" %% "akka-http" % "10.1.6"

lazy val _akkaActor = "com.typesafe.akka" %% "akka-actor" % verAkka
lazy val _akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % verAkka

lazy val _redisclient = "net.debasishg" %% "redisclient" % "3.0"

lazy val _json4sJackson = "org.json4s" %% "json4s-jackson" % "3.3.0"

lazy val _mongoScala = "org.mongodb.scala" %% "mongo-scala-driver" % "1.0.0"

lazy val _logback = "ch.qos.logback" % "logback-classic" % "1.1.3"
lazy val _commonsEmail = "org.apache.commons" % "commons-email" % "1.4"
lazy val _guava = "com.google.guava" % "guava" % "18.0"

name := "file-upload"

description := "File Upload"

version := "0.0.1"

homepage := Some(
  new URL(
    "https://github.com/yangbajing/scala-applications/tree/master/file-upload"
  )
)

organization := "me.yangbajing"

organizationHomepage := Some(
  new URL("https://github.com/yangbajing/scala-applications")
)

startYear := Some(2018)

scalaVersion := "2.12.8"

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

assemblyJarName in assembly := "file-upload.jar"

mainClass in assembly := Some("me.yangbajing.fileupload.Main")

libraryDependencies ++= Seq(
  _akkaActor,
  _akkaSlf4j,
  _akkaHttp,
  _logback,
  _typesafeConfig,
  _scalaLogging,
  _scalatest
)

val verAkka = "2.5.18"
lazy val _scalatest = "org.scalatest" %% "scalatest" % "3.0.5" % "test"
lazy val _typesafeConfig = "com.typesafe" % "config" % "1.3.3"
lazy val _scalaLogging = ("com.typesafe.scala-logging" %% "scala-logging" % "3.9.2")
  .exclude("org.scala-lang", "scala-library")
  .exclude("org.scala-lang", "scala-reflect")
lazy val _akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.1.6"
lazy val _akkaActor = "com.typesafe.akka" %% "akka-actor" % verAkka
lazy val _akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % verAkka
lazy val _logback = "ch.qos.logback" % "logback-classic" % "1.2.3"


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

scalafmtOnCompile in ThisBuild := true

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases",
  "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"
)

assemblyJarName in assembly := "file-upload.jar"

mainClass in assembly := Some("me.yangbajing.fileupload.Main")

resolvers ++= Seq(
  "ihongka.cn sbt".at("https://artifactory.hongkazhijia.com/artifactory/sbt-release"),
  "ihongka.cn maven".at("https://artifactory.hongkazhijia.com/artifactory/libs-release"))

libraryDependencies ++= Seq(
  "com.helloscala.fusion"      %% "helloscala-common"   % "1.0.0-alpha14",
  _typesafeConfig,
  _logback,
  _scalaLogging,
  _scalatest
) ++ _akkas ++ _akkaHttps ++ _jsons

lazy val _scalatest = "org.scalatest" %% "scalatest" % "3.0.7" % Test
lazy val _typesafeConfig = "com.typesafe" % "config" % "1.3.3"
lazy val _scalaLogging = ("com.typesafe.scala-logging" %% "scala-logging" % "3.9.2")
  .exclude("org.scala-lang", "scala-library")
  .exclude("org.scala-lang", "scala-reflect")

lazy val _akkaHttps = Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.8",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.8" % Test
)
lazy val _logback = "ch.qos.logback" % "logback-classic" % "1.2.3"

val verAkka = "2.5.22"
lazy val _akkas = Seq(
  "com.typesafe.akka" %% "akka-actor" % verAkka,
  "com.typesafe.akka" %% "akka-stream" % verAkka,
  "com.typesafe.akka" %% "akka-slf4j" % verAkka,
  "com.typesafe.akka" %% "akka-stream-testkit" % verAkka % Test
)

val versionJackson = "2.9.6"
lazy val _jsons = Seq(
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % versionJackson,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % versionJackson,
  "com.fasterxml.jackson.module" % "jackson-module-parameter-names" % versionJackson,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % versionJackson
)

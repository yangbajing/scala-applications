import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys

object Build extends Build {

  override lazy val settings = super.settings :+ {
    shellPrompt := (s => Project.extract(s).currentProject.id + " > ")
  }

  lazy val root = Project("email-server", file("."))
    .settings(
      description := "Email Server",
      version := "1.0",
      homepage := Some(new URL("http://github.com/yangbajing/scala-applications")),
      organization := "me.yangbajing",
      organizationHomepage := Some(new URL("https://github.com/yangbajing/scala-applications")),
      startYear := Some(2014),
      scalaVersion := "2.11.7",
      scalacOptions ++= Seq(
        "-encoding", "utf8",
        "-unchecked",
        "-feature",
        "-deprecation"
      ),
      javacOptions ++= Seq(
        "-encoding", "utf8",
        "-Xlint:unchecked",
        "-Xlint:deprecation"
      ),
      javaOptions += "-Dproject.base=" + baseDirectory.value,
      publish :=(),
      publishLocal :=(),
      publishTo := None,
      offline := true,
      resolvers ++= Seq(
        "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
        "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases",
        "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
        "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"),
      AssemblyKeys.assemblyJarName in AssemblyKeys.assembly := "email-server.jar",
      mainClass in AssemblyKeys.assembly := Some("me.yangbajing.emailserver.app.Main"),
      libraryDependencies ++= Seq(
        _commonsEmail,
        _akkaHttp,
        _akkaHttpPlayJson,
        _akkaActor,
        _akkaSlf4j,
        _logback,
        _activemqClient,
        _typesafeConfig,
        _scalatest))

  val _scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.0.4"
  val _scalatest = "org.scalatest" %% "scalatest" % "2.2.5" % "test"
  val _typesafeConfig = "com.typesafe" % "config" % "1.3.0"

  val verAkkaHttp = "1.0"
  val _akkaStream = "com.typesafe.akka" %% "akka-stream-experimental" % verAkkaHttp
  val _akkaHttpCore = "com.typesafe.akka" %% "akka-http-core-experimental" % verAkkaHttp
  val _akkaHttp = "com.typesafe.akka" %% "akka-http-experimental" % verAkkaHttp
  val _akkaHttpPlayJson = "de.heikoseeberger" %% "akka-http-play-json" % "1.0.0"

  val verAkka = "2.3.12"
  val _akkaActor = "com.typesafe.akka" %% "akka-actor" % verAkka
  val _akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % verAkka

  val _logback = "ch.qos.logback" % "logback-classic" % "1.1.3"
  val _commonsEmail = "org.apache.commons" % "commons-email" % "1.4"
  val _reactivemongo = "org.reactivemongo" %% "reactivemongo" % "0.11.5"
  val _guava = "com.google.guava" % "guava" % "18.0"
  val _activemqClient = "org.apache.activemq" % "activemq-client" % "5.11.1"

}


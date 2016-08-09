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
      version := "0.0.2",
      homepage := Some(new URL("http://github.com/yangbajing/scala-applications")),
      organization := "me.yangbajing",
      organizationHomepage := Some(new URL("https://github.com/yangbajing/scala-applications")),
      startYear := Some(2015),
      scalaVersion := "2.11.8",
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
      mainClass in AssemblyKeys.assembly := Some("me.yangbajing.emailserver.Main"),
      libraryDependencies ++= Seq(
        _scalaReflect,
        _guice,
        _redisclient,
        _ssc,
        _commonsEmail,
        _akkaHttp,
        _akkaActor,
        _akkaSlf4j,
        _logback,
        _activemqClient,
        _typesafeConfig,
        _scalaLogging,
        _scalatest))

  val _scalaReflect = "org.scala-lang" %  "scala-reflect"  % "2.11.8"
  val _scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.0.+"
  val _scalatest = "org.scalatest" %% "scalatest" % "2.2.+" % "test"
  val _typesafeConfig = "com.typesafe" % "config" % "1.3.+"
  val _scalaLogging = ("com.typesafe.scala-logging" %% "scala-logging" % "3.4.0").exclude("org.scala-lang", "scala-library").exclude("org.scala-lang", "scala-reflect")

//  val _akkaHttpPlayJson = "de.heikoseeberger" %% "akka-http-play-json" % "1.0.0"
  val verAkka = "2.4.8"

  val _akkaActor = "com.typesafe.akka" %% "akka-actor" % verAkka
  val _akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % verAkka
  val _akkaStream = "com.typesafe.akka" %% "akka-stream" % verAkka
  val _akkaHttp = "com.typesafe.akka" %% "akka-http-experimental" % verAkka

  val _ssc = "com.elderresearch" %% "ssc" % "0.2.0"

  val _guice = "com.google.inject" % "guice" % "4.1.0"

  val _redisclient = "net.debasishg" %% "redisclient" % "3.0"

  val _logback = "ch.qos.logback" % "logback-classic" % "1.1.+"
  val _commonsEmail = "org.apache.commons" % "commons-email" % "1.4"
  val _reactivemongo = "org.reactivemongo" %% "reactivemongo" % "0.11.+"
  val _guava = "com.google.guava" % "guava" % "19.+"
  val _activemqClient = "org.apache.activemq" % "activemq-client" % "5.13.+"

}


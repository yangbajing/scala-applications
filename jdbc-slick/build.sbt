name := "jdbc-slick"

organization := "me.yangbajing"

organizationName := "Yangbajing's Garden"

organizationHomepage := Some(url("https://www.yangbajing.me"))

homepage := Some(url("https://github.com/yangbajing/scala-applications/tree/master/jdbc-slick"))

startYear := Some(2018)

licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))

scalaVersion := "2.12.6"

shellPrompt := { s =>
  Project.extract(s).currentProject.id + " > "
}

scalacOptions ++= Seq(
  "-encoding", "utf8",
  "-unchecked",
  "-feature",
  "-deprecation"
)

libraryDependencies ++= Seq(
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % "2.9.6",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "2.9.6",
  "com.fasterxml.jackson.module" % "jackson-module-parameter-names" % "2.9.6",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.6",
  "org.postgresql" % "postgresql" % "42.2.2",
  "com.zaxxer" % "HikariCP" % "3.2.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "com.github.tminglei" %% "slick-pg" % "0.16.2",
  "com.github.tminglei" %% "slick-pg_json4s" % "0.16.2",
  "com.typesafe.akka" %% "akka-stream" % "2.5.14",
  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.3" % Test,
  "com.typesafe.slick" %% "slick-testkit" % "3.2.3" % Test,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

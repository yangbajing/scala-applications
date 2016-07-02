scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-encoding", "utf8",
  "-unchecked",
  "-feature",
  "-deprecation"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.7"
)


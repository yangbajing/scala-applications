scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-encoding", "utf8",
  "-unchecked",
  "-feature",
  "-deprecation"
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.5.4",
  "com.typesafe.akka" %% "akka-actor" % "2.4.7"
)


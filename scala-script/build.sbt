scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-encoding", "utf8",
  "-unchecked",
  "-feature",
  "-deprecation"
)
      
libraryDependencies += "com.typesafe.play" %% "play-ws" % "2.5.3"


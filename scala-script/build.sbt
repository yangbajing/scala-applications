scalaVersion := "2.11.7"

scalacOptions ++= Seq(
  "-encoding", "utf8",
  "-unchecked",
  "-feature",
  "-deprecation"
)
      
libraryDependencies += "com.typesafe.play" %% "play-ws" % "2.4.6"


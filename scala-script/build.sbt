scalaVersion := "2.11.7"

scalacOptions ++= Seq(
  "-encoding", "utf8",
  "-unchecked",
  "-feature",
  "-deprecation"
)
      
javacOptions ++= Seq(
  "-encoding", "utf8",
  "-Xlint:unchecked",
  "-Xlint:deprecation"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies += "com.wacai" %% "config-annotation" % "0.3.4"

libraryDependencies += "com.typesafe.play" %% "play-ws" % "2.4.4"


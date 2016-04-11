scalaVersion := "2.11.7"

scalacOptions ++= Seq(
  "-encoding", "utf8",
  "-unchecked",
  "-feature",
  "-deprecation"
)

assemblyJarName in assembly := "spark-startup.jar"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

test in assembly := {}


val verSpark = "1.5.2"
val verHadoop = "2.6.2"
      
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.5.2" % "provided,test",
  "org.apache.spark" %% "spark-sql" % "1.5.2" % "provided,test",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)


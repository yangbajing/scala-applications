name := "scalatest"

organization := "me.yangbajing"

organizationName := "Yangbajing's Garden"

organizationHomepage := Some(url("https://www.yangbajing.me"))

homepage := Some(url("https://github.com/yangbajing/scala-applications/tree/master/scalatest"))

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
  "org.scalamock" %% "scalamock" % "4.1.0" % Test,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)


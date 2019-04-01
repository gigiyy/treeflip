name := "treeflip"

version := "0.1"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "27.0.1-jre",
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

mainClass in Compile := Some("tree.App")

name := "typesanegame"

version := "3.14"

scalaVersion := "2.11.8"

lazy val root = (project in file("."))
  .aggregate(graph, game)

lazy val graph = project in file("graph")

lazy val game = (project in file("game"))
  //.settings(mainClass in assembly := Some("com.typesane.game.Game")) - Only needed when assembly plugin is included.
  .dependsOn(graph)

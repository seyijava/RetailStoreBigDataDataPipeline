name := "akka-quickstart-scala"

version := "1.0"

scalaVersion := "2.12.10"
val akkaVersion = "2.5.26"
val akkaHttpVersion = "10.1.11"
val circeVersion = "0.11.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "joda-time" % "joda-time" % "2.9.4",
  "de.heikoseeberger" %% "akka-http-circe" % "1.15.0",
  "org.scalacheck" %% "scalacheck" % "1.14.2"
)


name := "dataIngestAPI"

version := "1.0"

scalaVersion := "2.12.10"


val AkkaVersion = "2.6.13"
val AlpakkaVersion = "2.0.2"
val circeVersion = "0.11.2"
val AkkaHttpVersion = "10.0.5"
libraryDependencies ++=
  Seq(
    "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
    "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
    "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-stream-kafka" % "2.0.6",
    "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
    "com.google.code.gson" % "gson" % "1.7.1",
    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion,
    "joda-time" % "joda-time" % "2.9.4",
    "de.heikoseeberger" %% "akka-http-circe" % "1.15.0",
      "com.typesafe.akka" %% "akka-slf4j" % "2.6.0",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
  )

version in Docker := "latest"
dockerExposedPorts in Docker := Seq(9090)
dockerRepository := Some("akka-retail-store-Ingest-api")
dockerBaseImage := "java"
enablePlugins(JavaAppPackaging)



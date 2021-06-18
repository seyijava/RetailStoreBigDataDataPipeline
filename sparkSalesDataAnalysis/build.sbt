name := "sparkBatchJob"

version := "1.0"

scalaVersion := "2.12.10"

lazy val akkaVersion = "2.6.14"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.1.1",
  "org.apache.spark" %% "spark-sql" % "3.1.1",
  "org.apache.spark" %% "spark-avro" % "3.1.1",
  "org.apache.spark" %% "spark-hive" % "3.1.1",
  "com.datastax.spark" %% "spark-cassandra-connector" % "3.0.0",
"org.scalatest" %% "scalatest" % "3.1.0" % Test
)

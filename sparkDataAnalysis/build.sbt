name := "akka-quickstart-scala"

version := "1.0"

scalaVersion := "2.12.10"


lazy val sparkVersion = "3.0.1"
lazy val cassandraSparkVersion = "3.0.1"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" %  sparkVersion,
  "org.apache.spark" %% "spark-avro" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "com.datastax.spark" %% "spark-cassandra-connector" % cassandraSparkVersion,
   "com.typesafe" % "config" % "1.4.1"
)

package com.bigdataconcept.spark.retail.sales.analytics.data.pipeline.util

import com.typesafe.config.ConfigFactory

trait AppConfig {

  val config = ConfigFactory.load()
  private val cassandraConfig = config.getConfig("alpakka.cassandra")
  private val kafkaConfig = config.getConfig("kafka")
  val keyspace: String = cassandraConfig.getString("keySpace")
  val table: String = cassandraConfig.getString("table")
  val kafkaTopic: String = kafkaConfig.getString("dataIngestTopic")
  val brokerUrl: String = kafkaConfig.getString("brokerUrl")
  val consumerGroupId: String = kafkaConfig.getString("consumerGroupId")
  val maxPartitions: Int =  kafkaConfig.getString("partition-size").toInt
  val kafkaClientConfig = config.getConfig("akka.kafka.consumer")
}

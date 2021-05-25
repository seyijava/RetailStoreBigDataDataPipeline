package com.bigdataconcept.retail.sales.analytics.data.ingest.api.util

import com.typesafe.config.ConfigFactory

trait AppConfig {


  val config = ConfigFactory.load()

  val kafkaProducerConfig = config.getConfig("akka.kafka.producer")
  val httpConfig = config.getConfig("rest")
  val kafkaConfig = config.getConfig("kafka")
  val httpHost = httpConfig.getString("host")
  val httpPort = httpConfig.getInt("port")

  val kafkaTopic = kafkaConfig.getString("dataIngestTopic")
  val kafkaTopicPartitionSize = kafkaConfig.getInt("partition-size")

}

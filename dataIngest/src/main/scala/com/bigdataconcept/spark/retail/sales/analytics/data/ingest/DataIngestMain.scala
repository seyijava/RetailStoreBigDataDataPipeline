package com.bigdataconcept.spark.retail.sales.analytics.data.ingest



import akka.actor.ActorSystem
import akka.kafka.{CommitterSettings, ConsumerSettings}
import akka.stream.ActorMaterializer
import akka.stream.alpakka.cassandra.{CassandraSessionSettings}
import akka.stream.alpakka.cassandra.scaladsl.{CassandraSessionRegistry}
import com.bigdataconcept.spark.retail.sales.analytics.data.ingest.pipeline.DailySalesDataPipeLine
import com.bigdataconcept.spark.retail.sales.analytics.data.ingest.util.AppConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer}

import scala.concurrent.ExecutionContext
import akka.stream.{ActorMaterializerSettings, Supervision}

object DataIngestMain extends App  with AppConfig {

  implicit val system = ActorSystem("RetailStoreDataIngest")

  val decider: Supervision.Decider = { e =>
    system.log.error("Unhandled exception in stream", e.printStackTrace())
    Supervision.stop
  }
  val materializerSettings = ActorMaterializerSettings(system).withSupervisionStrategy(decider)

  implicit val mat = ActorMaterializer(materializerSettings)

  implicit val ec: ExecutionContext = system.dispatcher

  implicit val sessionSettings = CassandraSessionSettings()

  implicit  val session   = CassandraSessionRegistry.get(system).sessionFor(sessionSettings)

  implicit val committerSettings: CommitterSettings = CommitterSettings(system)

  implicit val consumerSettings =
    ConsumerSettings(kafkaClientConfig, new StringDeserializer, new ByteArrayDeserializer)
      .withBootstrapServers(brokerUrl)
      .withGroupId(consumerGroupId)
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val dailySalesDataPipeLine =  DailySalesDataPipeLine(keyspace,table,kafkaTopic,maxPartitions)

  dailySalesDataPipeLine.pipeline.run()
}
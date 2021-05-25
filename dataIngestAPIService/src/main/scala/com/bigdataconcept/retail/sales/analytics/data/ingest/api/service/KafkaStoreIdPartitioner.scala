package com.bigdataconcept.retail.sales.analytics.data.ingest.api.service
import org.apache.kafka.clients.producer.Partitioner
import org.apache.kafka.common.{Cluster, InvalidRecordException}
import java.util

class KafkaStoreIdPartitioner extends Partitioner{

  var storePartitionData: Map[String,Int] = Map.empty
  val defaultPartition = 0
  override def partition(topic: String, key: Any, keyBytes: Array[Byte], value: Any, valueBytes: Array[Byte], cluster: Cluster): Int = {

    if ((keyBytes == null) || (!key.isInstanceOf[String]))
      throw new InvalidRecordException("All messages must have store code name as key")
    val storeKey = key.asInstanceOf[String]
     storePartitionData.getOrElse(storeKey,defaultPartition)
  }

  override def close(): Unit = {}

  override def configure(configs: util.Map[String, _]): Unit = {
    val storePartitionIds = configs.get("storePartition")
    val storeIdPartitionerPair = storePartitionIds.asInstanceOf[String]
    if(storeIdPartitionerPair.isBlank || storeIdPartitionerPair.isEmpty)
      throw new IllegalArgumentException("Store partition configuration not set or found")
    storePartitionData = storeIdPartitionerPair.split("&").map(_.split("=")).map(arr => arr(0) -> arr(1).toInt).toMap
  }
}


object KafkaStoreIdPartitioner{
  def apply() = new KafkaStoreIdPartitioner()
}
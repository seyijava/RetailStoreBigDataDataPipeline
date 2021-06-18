package com.bigdataconcept.retail.sales.analytics.data.ingest.api.service

import akka.Done
import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.stream.Materializer
import akka.stream.scaladsl.{Keep, Source}
import com.bigdataconcept.retail.sales.analytics.data.ingest.api.domain.Domain.DailySaleRecord
import org.apache.kafka.clients.producer.ProducerRecord
import io.circe._
import io.circe.syntax._
import akka.kafka.scaladsl.Producer

import scala.concurrent.Future
import com.bigdataconcept.retail.sales.analytics.data.ingest.api.domain.DomainCodecs.dailySaleRecord


class KafkaProducerService(topicName: String)(implicit mat: Materializer, system: ActorSystem, implicit val producerSettings: ProducerSettings[String,String]) {

  def sendDailySalesRecord(saleRecord: DailySaleRecord) : Future[Done]={
    system.log.info(s"Publishing Sale Record to Kafka $saleRecord")
    import dailySaleRecord._
    val done = Source.single(saleRecord)
      .map(s =>  new ProducerRecord[String, String](topicName,saleRecord.store.code,s.asJson.noSpaces))
      .runWith(Producer.plainSink(producerSettings))
     done
  }
}

object KafkaProducerService{

   def apply(topicName:String)(implicit  mat: Materializer, system: ActorSystem, producerSettings: ProducerSettings[String,String])  = new KafkaProducerService(topicName)
}

package com.bigdataconcept.retail.sales.analytics.data.ingest.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.kafka.ProducerSettings
import akka.stream.ActorMaterializer
import com.bigdataconcept.retail.sales.analytics.data.ingest.api.domain.Domain.{DailySaleRecord, ItemSale, Location, Store}
import com.bigdataconcept.retail.sales.analytics.data.ingest.api.route.DataIngestAPIRoute
import com.bigdataconcept.retail.sales.analytics.data.ingest.api.service.KafkaProducerService
import com.bigdataconcept.retail.sales.analytics.data.ingest.api.util.AppConfig
import org.apache.kafka.common.serialization.StringSerializer

import java.time.Instant
import scala.concurrent.ExecutionContext


object DataIngestMain extends App with AppConfig{

  implicit val system = ActorSystem("DataIngestService",config)

  implicit val mat: ActorMaterializer = ActorMaterializer()

  implicit val ec: ExecutionContext = system.dispatcher

  implicit val producerSetting: ProducerSettings[String, String] =
    ProducerSettings(kafkaProducerConfig, new StringSerializer, new StringSerializer)
      .withBootstrapServers(kafkaConfig.getString("brokerUrl"))
      .withProperty("partitioner.class", "com.bigdataconcept.retail.sales.analytics.data.ingest.api.service.KafkaStoreIdPartitioner")
      .withProperty("storePartition",kafkaConfig.getString("partition"))

  val kafkaProducerService =  KafkaProducerService(kafkaTopic)


   val storeNumber = (1 to 100)

    val storeCodes = storeNumber.map(n => {
       if(n <= 9){
          "S-00" + String.valueOf(n)
       }
       else if(n > 9 && n <= 99){
        "S-0" + String.valueOf(n)
       }
      else{
         "S-" + String.valueOf(n)
       }
     })



  for{
     storeCode <- storeCodes
  }yield{
  val location = Location("2334","monctor","lagos","US")
  val store = Store(location=location,code=storeCode)
  val saleRecord = DailySaleRecord(store=store,ItemSale("12233",2,45),Instant.now)
  val done = kafkaProducerService.sendDailySalesRecord(saleRecord)}

  //val apiRoute = new DataIngestAPIRoute(kafkaProducerService)

   //Http().bindAndHandle(apiRoute.route, httpHost, httpPort)
}

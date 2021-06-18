package com.bigdataconcept.retail.sales.simulator

import akka.actor.{Actor, ActorLogging, ActorSystem, Cancellable, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.POST
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpRequest, HttpResponse}
import akka.http.scaladsl.settings.ConnectionPoolSettings
import akka.stream.ActorMaterializer
import akka.stream.javadsl.Sink
import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.bigdataconcept.retail.sales.simulator.Domain.{DailySaleRecord, ItemSale, Store}
import io.circe._
import io.circe.syntax._

import scala.concurrent.duration._
import java.time.{Instant, LocalDateTime}
import scala.concurrent.{ExecutionContext}
import com.bigdataconcept.retail.sales.simulator.DomainCodecs.dailySaleRecord._

import scala.util.{Failure, Success}

object POSTerminal{
  case object SendSaleRecord
  def props(store: Store, terminalId: String,ingestServerUrl: String)(implicit actorSystem: ActorSystem,mat: ActorMaterializer) : Props = Props(new POSTerminal(store,terminalId,ingestServerUrl))
}

class POSTerminal(store: Store, terminalId: String, ingestServerUrl: String)(implicit system: ActorSystem,mat: ActorMaterializer) extends Actor with ActorLogging{

  import POSTerminal._

  implicit val ex: ExecutionContext = system.dispatcher

  val saleRequestSchedule: Cancellable = system.scheduler.schedule(5 seconds,120 seconds){
    self ! SendSaleRecord
  }

  override def receive: Receive = {
    case SendSaleRecord => sendSaleRecordRequest()
  }
    def sendSaleRecordRequest() : Unit = {
       sendRequest(createHttpRequest())
    }

     def createHttpRequest() : HttpRequest={
       val sale = SaleDataGenerator.generatorSaleItem
       val saleRecord = DailySaleRecord(store = store, itemSale = sale, Instant.now())
       log.info(saleRecord.asJson.noSpaces)
        HttpRequest(method = POST,  uri = "http://127.0.0.1:9000/dataIngest",entity = HttpEntity(ContentTypes.`application/json`, ByteString(saleRecord.asJson.noSpaces)))
     }


  def sendRequest(req: HttpRequest): Unit = {
    val pool = Http().cachedHostConnectionPool[Int]("127.0.0.1", 9000, ConnectionPoolSettings(system))
    Source.single(req → 1)
     .via(pool)
      .runWith(Sink.foreach {
        case (Success(a), i) => println(s"[${LocalDateTime.now}] $i ${self.path} succeeded $a")
        case (Failure(e), i) => println(s"[${LocalDateTime.now}] $i ${self.path} failed: $e ")
      })

  }
}

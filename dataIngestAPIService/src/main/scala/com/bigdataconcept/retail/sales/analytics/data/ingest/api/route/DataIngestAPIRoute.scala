package com.bigdataconcept.retail.sales.analytics.data.ingest.api.route

import akka.http.scaladsl.model.StatusCodes
import com.bigdataconcept.retail.sales.analytics.data.ingest.api.service.KafkaProducerService
import akka.http.scaladsl.server.{Directives, Route}
import com.bigdataconcept.retail.sales.analytics.data.ingest.api.domain.Domain.DailySaleRecord
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class DataIngestAPIRoute(kafkaProducerService: KafkaProducerService) (implicit executionContext: ExecutionContext)   extends Directives with FailFastCirceSupport{
  val route: Route = pathPrefix("dataIngest") {
    pathEndOrSingleSlash {
      post {
        entity(as[DailySaleRecord]) { dailySaleRecord =>
           println(dailySaleRecord)
          onComplete(kafkaProducerService.sendDailySalesRecord(dailySaleRecord)) { done =>
            done match
            {
              case Success(_) => complete(StatusCodes.Created)
              case Failure(ex) => complete(StatusCodes.InternalServerError, ex.toString)
            }
          }
        }
      }
    }
  }
}
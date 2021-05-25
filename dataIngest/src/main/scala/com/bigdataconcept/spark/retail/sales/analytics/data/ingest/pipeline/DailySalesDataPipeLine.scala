package com.bigdataconcept.spark.retail.sales.analytics.data.ingest.pipeline

import akka.NotUsed
import akka.actor.ActorSystem
import akka.kafka.scaladsl.Consumer
import akka.kafka.{CommitterSettings, ConsumerMessage, ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import akka.stream.alpakka.cassandra.CassandraWriteSettings
import akka.stream.alpakka.cassandra.scaladsl.{CassandraFlow, CassandraSession}
import akka.stream.scaladsl.{Flow, Sink}
import com.bigdataconcept.spark.retail.sales.analytics.data.ingest.domain.Domain.{DailySale, DailySaleRecord}
import com.bigdataconcept.spark.retail.sales.analytics.data.ingest.domain.DomainCodecs.dailySale
import com.datastax.oss.driver.api.core.cql.{BoundStatement, PreparedStatement}
import io.circe.parser


class DailySalesDataPipeLine(cassandraKeySpace: String, cassandraTable: String,kafkaTopic: String,maxPartitions: Int)
                            ( implicit val mat: ActorMaterializer,system: ActorSystem, committerSettings: CommitterSettings,session: CassandraSession
                            ,consumerSettings: ConsumerSettings[String,Array[Byte]]){


  val statementBinder: (DailySaleRecord, PreparedStatement) => BoundStatement =
    (dailySales, preparedStatement) => preparedStatement.bind(dailySales.store.code,dailySales.store.location.city
      ,dailySales.store.location.postalCode,dailySales.store.location.state,dailySales.itemSale.productCode,
      Int.box(dailySales.itemSale.quantity),Double.box(dailySales.itemSale.unitAmount.toDouble),Double.box(dailySales.itemSale.totalAmount.toDouble),
    dailySales.transactionDate,Int.box(dailySales.month),Int.box(dailySales.year))
  val cassandraInsertStatement = s"INSERT INTO $cassandraKeySpace.$cassandraTable(storeCode,city,postalCode,state,productCode,quantity,unitAmount,totalAmount," +
    s"transactionDate,salemonth,saleyear) " +
    s"VALUES (?,?,?,?,?,?,?,?,?,?,?)"


  val cassandraWriteFlow: Flow[DailySaleRecord, DailySaleRecord, NotUsed] = CassandraFlow.create(CassandraWriteSettings.defaults, cassandraInsertStatement, statementBinder)


  val convertFromKafkaMessageToDailySalesFlow: Flow[ConsumerMessage.CommittableMessage[String, Array[Byte]], DailySaleRecord, NotUsed] =
    Flow[ConsumerMessage.CommittableMessage[String, Array[Byte]]].map { message =>
      import dailySale._
      val payload = new String(message.record.value())
      val key = message.record.key()
      val partition = message.committableOffset.partitionOffset._1.partition
      system.log.info(s"store data payload ${payload}  Key: ${key}  partition ${partition}")
      val saleRecord = parser.decode[DailySale](payload).toOption.get
      saleRecord.convertToDailySaleRecord
    }

  //def businessFlow[T]: Flow[T, T, NotUsed] = Flow[T]

  def pipeline=
    Consumer
      .committablePartitionedSource(consumerSettings, Subscriptions.topics(kafkaTopic))
      .mapAsyncUnordered(maxPartitions) { case (_, source) => {
        source
          .via(convertFromKafkaMessageToDailySalesFlow)
          .via(cassandraWriteFlow)
          .runWith(Sink.ignore)
      }
      }
}


object DailySalesDataPipeLine{

   def apply(cassandraKeySpace: String, cassandraTable: String,kafkaTopic: String,maxPartitions: Int)
            ( implicit  mat: ActorMaterializer,s: ActorSystem, committerSettings: CommitterSettings,session: CassandraSession
  ,consumerSettings: ConsumerSettings[String,Array[Byte]])
   = new DailySalesDataPipeLine(cassandraKeySpace,cassandraTable,kafkaTopic,maxPartitions)

}



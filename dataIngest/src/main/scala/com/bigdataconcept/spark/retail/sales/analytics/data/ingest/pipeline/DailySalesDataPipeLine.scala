package com.bigdataconcept.spark.retail.sales.analytics.data.ingest.pipeline

import akka.NotUsed
import akka.actor.ActorSystem
import akka.kafka.ConsumerMessage.CommittableOffset
import akka.kafka.scaladsl.Consumer.DrainingControl
import akka.kafka.scaladsl.{Committer, Consumer}
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


  val statementBinder: ((DailySaleRecord,ConsumerMessage.Committable), PreparedStatement) => BoundStatement =
    (dailySales, preparedStatement) => preparedStatement.bind(dailySales._1.store.code,dailySales._1.store.location.city
      ,dailySales._1.store.location.state,dailySales._1.itemSale.productCode,
      Int.box(dailySales._1.itemSale.quantity),Double.box(dailySales._1.itemSale.unitAmount.toDouble),Double.box(dailySales._1.itemSale.totalAmount.toDouble),
    dailySales._1.transactionDate,Int.box(dailySales._1.month),Int.box(dailySales._1.year),dailySales._1.itemSale.category,dailySales._1.store.location.countryCode)
  val cassandraInsertStatement = s"INSERT INTO $cassandraKeySpace.$cassandraTable(storeCode,city,state,productCode,quantity,unitAmount,totalAmount," +
    s"transactionDate,salemonth,saleyear,category,country) " +
    s"VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"


  val cassandraWriteFlow: Flow[(DailySaleRecord,ConsumerMessage.Committable), (DailySaleRecord,ConsumerMessage.Committable), NotUsed] = CassandraFlow.create(CassandraWriteSettings.defaults, cassandraInsertStatement, statementBinder)


  val convertFromKafkaMessageToDailySalesFlow: Flow[ConsumerMessage.CommittableMessage[String, Array[Byte]], (DailySaleRecord,ConsumerMessage.Committable), NotUsed] =
    Flow[ConsumerMessage.CommittableMessage[String, Array[Byte]]].map { message =>
      import dailySale._
      val payload = new String(message.record.value())
      val key = message.record.key()
      val partition = message.committableOffset.partitionOffset._1.partition
      system.log.info(s"store data payload ${payload}  Key: ${key}  partition ${partition}")
      val saleRecord = parser.decode[DailySale](payload).toOption.get
      (saleRecord.convertToDailySaleRecord,message.committableOffset)
    }

  def businessFlow[T]: Flow[T, T, NotUsed] = Flow[T]

  def pipeline=
    Consumer
      .committablePartitionedSource(consumerSettings, Subscriptions.topics(kafkaTopic))
      .mapAsyncUnordered(maxPartitions) { case (topicPartitions, source) => {
        system.log.info(s"partition-[${topicPartitions.partition}] topic-[${topicPartitions.topic}\n\n\n")
        source
          .via(convertFromKafkaMessageToDailySalesFlow)
          .via(cassandraWriteFlow)
          .map(msgCommitter => msgCommitter._2)
          .runWith(Committer.sink(committerSettings))
      }
      }.toMat(Sink.ignore)(DrainingControl.apply)
}


object DailySalesDataPipeLine{

   def apply(cassandraKeySpace: String, cassandraTable: String,kafkaTopic: String,maxPartitions: Int)
            ( implicit  mat: ActorMaterializer,s: ActorSystem, committerSettings: CommitterSettings,session: CassandraSession
  ,consumerSettings: ConsumerSettings[String,Array[Byte]])
   = new DailySalesDataPipeLine(cassandraKeySpace,cassandraTable,kafkaTopic,maxPartitions)

}



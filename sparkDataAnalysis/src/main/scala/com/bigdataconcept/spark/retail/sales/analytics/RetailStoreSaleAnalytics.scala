package com.bigdataconcept.spark.retail.sales.analytics

import com.bigdataconcept.spark.retail.sales.analytics.config.AppConfig
import com.bigdataconcept.spark.retail.sales.analytics.transformation.StoreSaleAggregateAnalysis
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.cassandra._
import org.apache.spark.sql.functions.col



object RetailStoreSaleAnalytics extends Serializable with AppConfig{

     def main(arg: Array[String]) : Unit ={

       implicit val spark = SparkSession.builder
         .appName("RetailStoreSaleAnalytics")
         .master(masterUrl)
         .config("spark.cassandra.connection.host", cassandraHost)
         .config("spark.cassandra.connection.port", cassandraPort)
         .config("spark.cassandra.auth.username",cassandraUserName)
         .config("spark.cassandra.auth.password",cassandraPassword)
         .getOrCreate
       import spark.implicits._


       val dailySaleRecordDF = spark.read.cassandraFormat("dailysales", cassandraKeySpace).load()
        .select("storeCode", "salemonth","transactionDate","city","productcode","saleyear" , "country",
          "category","quantity","state","totalAmount","unitAmount").where("saleMonth == 6  and saleYear == 2021") //.as[DailySaleRecord]


       val storeSaleAggregateAnalysis = StoreSaleAggregateAnalysis(cassandraKeySpace)

       println(dailySaleRecordDF.rdd.glom().collect().toArray.combinations(3).foreach(x => println(x.)
     //  dailySaleRecordDF.foreachPartition(iterator => println(iterator.toList.mkString(", ")))
      // storeSaleAggregateAnalysis.aggregateDailySalesPerCategory(dailySaleRecordDF)

      // storeSaleAggregateAnalysis.aggregateDailySalesPerStore(dailySaleRecordDF)

      // storeSaleAggregateAnalysis.topPerformingCategoryDailySales(dailySaleRecordDF)

     }
}

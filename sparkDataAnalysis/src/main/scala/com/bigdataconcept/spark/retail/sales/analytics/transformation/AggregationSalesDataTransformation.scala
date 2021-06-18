package com.bigdataconcept.spark.retail.sales.analytics.transformation

import org.apache.spark.sql.{DataFrame, SparkSession}
import com.datastax.spark.connector._
import org.apache.spark.sql.cassandra._
import org.apache.spark.sql.functions._
import com.datastax.spark.connector.writer._

object StoreSaleAggregateAnalysis{

     def apply(keyspace: String) (implicit  sparkSession: SparkSession) = new StoreSaleAggregateAnalysis(keyspace)
}

class StoreSaleAggregateAnalysis(keySpaceDB: String) (implicit  spark: SparkSession){

  def aggregateDailySalesPerCategory(df: DataFrame, destinationTable: String = "dailysalespercategorytbl") : Unit ={
   val dailySalePerCategory =  df.groupBy(date_trunc("Day",col("TransactionDate")).as("transactionDate"),
        col("StoreCode").as("storeCode"),col("Category").as("category"),col("City").as("city"),col("State").as("state"),col("Country").as("country"))
        .agg(sum(col("TotalAmount")).as("totalSales"), count(col("quantity")).as("totalPerCategory")).orderBy(col("StoreCode").asc)
          dailySalePerCategory.show
           dailySalePerCategory.write.cassandraFormat(destinationTable,keySpaceDB)
            .mode("Append")
            .save
  }

  def topPerformingCategoryDailySales(df: DataFrame, destinationTable: String = "topperformingcategorydailysales") : Unit ={
    val topPerformingCatgoryDailySales =  df.groupBy(date_trunc("Day",col("TransactionDate")).as("transactionDate"),
      col("StoreCode").as("storeCode"),col("Category").as("category"),col("City").as("city"),col("State").as("state"),col("Country").as("country"))
      .agg(sum(col("TotalAmount")).as("totalRevenue")).orderBy(col("category"),col("StoreCode").asc)
    topPerformingCatgoryDailySales.show
   topPerformingCatgoryDailySales.write.
     cassandraFormat(destinationTable,keySpaceDB)
     .mode("Append")
     .save
  }

  def aggregateDailySalesPerStore(df: DataFrame, destinationTable: String = "dailysalesperstoretbl") : Unit ={
    val groupByColumns = Seq(date_trunc("Day",col("TransactionDate")).as("transactionDate"),
      col("StoreCode").as("storeCode"),col("City").as("city"),col("State").as("state"),
      col("Country").as("country"))
    val totalDailySaleDF = df.groupBy(groupByColumns: _*)
      .agg(sum(col("TotalAmount")).as("totalSales"), count(col("quantity")).as("totalPerStore")).orderBy(col("StoreCode").asc)
    totalDailySaleDF.show
      totalDailySaleDF.write.cassandraFormat(destinationTable,keySpaceDB)
        .mode("Append")
        .save
  }


/*
  def aggregateWeeklySalesPerCategory(df: DataFrame, destinationTable: String = "weeklySalePerCategoryTbl") : Unit ={

     val groupByColumns  = Seq(col("Week_Start_Day"),col("StoreCode"),col("Category"),col("City"),col("State"),
     col("Country"))
    val weeklySalesDF = df.withColumn("Week_Start_Day",date_sub(next_day(col("TransactionDate"),"Sunday"),7))
    val weeklySaleAggDF =  weeklySalesDF.groupBy(groupByColumns: _*).agg(sum(col("TotalAmount")).as("TotalSales"), sum(col("Quantity")).as("TotalQuantity"))
      .orderBy(col("StoreCode").asc)
    weeklySaleAggDF.show

    //weeklySalePerCategory.show
  }


  def aggregateWeeklySalesPerStore(df: DataFrame, destinationTable: String = "aggregateWeeklySalesPerStoreTbl") : Unit ={

    val groupByColumns  = Seq(col("Week_Start_Day"),col("StoreCode"),col("City"),col("State"),
      col("Country"))
    val weeklySalesDF = df.withColumn("Week_Start_Day",date_sub(next_day(col("TransactionDate"),"Sunday"),7))
    val weeklySaleAggDF =  weeklySalesDF.groupBy(groupByColumns: _*).agg(sum(col("TotalAmount")).as("TotalSales"), sum(col("Quantity")).as("TotalQuantity"))
      .orderBy(col("StoreCode").asc)
    weeklySaleAggDF.show

  }


   def topCategorySaleOfTheDayPerStore(df: DataFrame, destinationTable: String = "topCategorySalePerDayTbl") : Unit={
        val groupByColumns = Seq(date_trunc("Day",col("TransactionDate")).as("TransactionDate"),col("Category"),col("StoreCode"),col("City"),col("State"),col("Country"))
        val topCategorySaleDF =  df.groupBy(groupByColumns: _*).agg(sum(col("Quantity")).alias("SaleCounts"))
          .orderBy(col("SaleCounts").desc)
          .limit(10)
        //topCategorySaleDF.show(60)

       df.select(groupByColumns: _*).show(5000)

   }*/


}

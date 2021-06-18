package com.bigdataconcept.retail.sales.analytics.spark.batch.job.analysis

import org.apache.spark.sql.{DataFrame, SparkSession}

class SaleDataAnalysis {


          def aggregateWeeklyTransactionByStore(spark: SparkSession, cassandraTable: String, dataFrame: DataFrame) : Unit={
            ???
          }

         def aggregateWeeklyTransactionByStoreAndProduct(spark: SparkSession, cassandraTable: String, dataFrame: DataFrame) : Unit={
                ???
          }

}

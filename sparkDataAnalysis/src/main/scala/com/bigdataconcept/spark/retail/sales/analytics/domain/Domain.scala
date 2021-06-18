package com.bigdataconcept.spark.retail.sales.analytics.domain

import java.time.Instant

object Domain {

     case class DailySaleRecord(storeCode: String, saleMonth: Int, saleYear: Int, transactionDate: Instant, city: String,
                               productCode: String, quantity: Int, totalAmount: Double, unitAmount: Double,category: String)
}

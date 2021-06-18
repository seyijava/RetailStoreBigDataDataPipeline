package com.bigdataconcept.spark.retail.sales.analytics.data.ingest.domain

import java.time.{Instant, ZoneId}

object Domain {
  case class Store(code: String, location: Location)

  case class Location( city: String, state: String,countryCode: String)

  case class ItemSale(productCode:String, quantity: Int, unitAmount: BigDecimal, totalAmount: BigDecimal = 0, category: String)

  object ItemSale{
    def apply(productCode: String ,quantity: Int, unitAmount: BigDecimal, category: String) = new ItemSale(productCode,quantity,unitAmount, totalAmount = unitAmount * quantity,category)

  }

  case class DailySale(store: Store, itemSale: ItemSale, transactionDate: Instant) {

        def convertToDailySaleRecord : DailySaleRecord={
          val timeZone =  transactionDate.atZone(ZoneId.systemDefault())
          val month = timeZone.getMonthValue
          val year = timeZone.getYear
             DailySaleRecord(store=store,itemSale=itemSale,transactionDate=transactionDate,month=month,year=year)
        }
  }

  case class DailySaleRecord(store: Store, itemSale: ItemSale, transactionDate: Instant, month: Int =0, year: Int = 0)
}

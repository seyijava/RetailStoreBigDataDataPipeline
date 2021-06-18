package com.bigdataconcept.retail.sales.analytics.data.ingest.api.domain

import java.time.{Instant, LocalDateTime}

object Domain {

  case class Store(code: String, location: Location)

  case class Location(city: String, state: String,countryCode: String)

  case class ItemSale(productCode:String, quantity: Int, unitAmount: BigDecimal, totalAmount: BigDecimal = 0, category: String)

  object ItemSale{
    def apply(productCode: String ,quantity: Int, unitAmount: BigDecimal,category: String) = new ItemSale(productCode,quantity,unitAmount, totalAmount = unitAmount * quantity, category)

  }
  case class DailySaleRecord(store: Store, itemSale: ItemSale, transactionDate: Instant)
}
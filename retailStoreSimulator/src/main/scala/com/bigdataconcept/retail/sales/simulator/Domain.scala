package com.bigdataconcept.retail.sales.simulator

import java.time.Instant

object Domain {
  case class Store(code: String, location: Location)

  case class Location(city: String, state: String,countryCode: String)

  case class ItemSale(productCode:String, itemDescription: String, quantity: Int, unitAmount: BigDecimal, category: String, totalAmount: BigDecimal = 0)

  object ItemSale{
    def apply(productCode: String ,itemDescription: String,quantity: Int, unitAmount: BigDecimal,category:String,totalAmount: Double) =
      new ItemSale(productCode,itemDescription,quantity,unitAmount,category,unitAmount * quantity)

  }
  case class DailySaleRecord(store: Store, itemSale: ItemSale, transactionDate: Instant)
}

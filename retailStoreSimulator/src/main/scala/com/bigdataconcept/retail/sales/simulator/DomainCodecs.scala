package com.bigdataconcept.retail.sales.simulator

import Domain.{DailySaleRecord, ItemSale, Location, Store}
import io.circe.{Decoder, Encoder}



object DomainCodecs {

  object location{
    implicit val locationDecoder: Decoder[Location] = {
      Decoder.forProduct3( "city", "state","countryCode")(Location.apply)
    }

    implicit val locationEncoder: Encoder[Location] = {
      Encoder.forProduct3("city","state","countryCode"){e =>
        (e.city,e.state,e.countryCode)
      }
    }
  }

  object store{
    import location._
    implicit val storeDecoder: Decoder[Store] ={
      Decoder.forProduct2("code","location")(Store.apply)
    }

    implicit val storeEncoder: Encoder[Store] ={
      Encoder.forProduct2("code","location"){ s => (s.code,s.location)}
    }
  }

  object itemSale{
    implicit val itemSaleDecoder: Decoder[ItemSale] ={
      Decoder.forProduct6("productCode", "itemDescription","quantity","unitAmount","category", "totalAmount")(ItemSale.apply)
    }

    implicit val itemSaleEncoder: Encoder[ItemSale] ={
      Encoder.forProduct6("productCode"," itemDescription","quantity","unitAmount","category","totalAmount"){ i =>
        (i.productCode,i.itemDescription,i.quantity,i.unitAmount,i.category,i.totalAmount)
      }
    }
  }

  object dailySaleRecord {

    import itemSale._
    import store._

    implicit val dailySaleRecordDecoder: Decoder[DailySaleRecord] = {
      Decoder.forProduct3("store", "itemSale", "transactionDate")(DailySaleRecord.apply)
    }

    implicit val dailySaleRecordDEncoder: Encoder[DailySaleRecord] = {
      Encoder.forProduct3("store", "itemSale", "transactionDate") { d =>
        (d.store, d.itemSale, d.transactionDate)
      }
    }
  }

}

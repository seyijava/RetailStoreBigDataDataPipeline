package com.bigdataconcept.spark.retail.sales.analytics.data.ingest.domain


import com.bigdataconcept.spark.retail.sales.analytics.data.ingest.domain.Domain.{DailySale, DailySaleRecord, ItemSale, Location, Store}
import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}

import java.time.Instant


object DomainCodecs {



  object location{
    implicit val locationDecoder: Decoder[Location] = {
      Decoder.forProduct4("postalCode", "city", "state","countryCode")(Location.apply)
    }

    implicit val locationEncoder: Encoder[Location] = {
      Encoder.forProduct4("postalCode","city","state","countryCode"){e =>
        (e.city,e.postalCode,e.state,e.countryCode)
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
      Decoder.forProduct3("productCode","quantity","unitAmount")(ItemSale.apply)
    }

    implicit val itemSaleEncoder: Encoder[ItemSale] ={
      Encoder.forProduct3("productCode","quantity","unitAmount"){ i =>
        (i.productCode,i.quantity,i.totalAmount)
      }
    }
  }

  object dailySale {

    import itemSale._
    import store._

    implicit val dailySaleDecoder: Decoder[DailySale] = {
      Decoder.forProduct3("store", "itemSale", "transactionDate")(DailySale.apply)
    }

    implicit val dailySaleEncoder: Encoder[DailySale] = {
      Encoder.forProduct3("store", "itemSale", "transactionDate") { d =>
        (d.store, d.itemSale, d.transactionDate)
      }
    }
  }

}

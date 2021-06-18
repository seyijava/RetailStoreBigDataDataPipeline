package com.bigdataconcept.retail.sales.simulator

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.bigdataconcept.retail.sales.simulator.Domain.{Location, Store}
import com.bigdataconcept.retail.sales.simulator.RetailStoreActor.OpenStore

object RetailsStoreSimulator extends App{

   implicit val system = ActorSystem("RetailStoreSimulator")

   implicit val mat = ActorMaterializer()

   implicit val ingestUrl = "http://localhost:9090/ingest"

    val retailsStores: Seq[Store] = Seq(Store("S-01",location = Location("Queens","NY","US")),
                                          Store("S-02",location = Location("New York City","NY","US")),
                                           Store("S-03",location = Location("Los Angles","CA","US")),
                                            Store("S-04",location = Location("Hollywood","CA","US")),
                                              Store("S-05",location = Location("Santa Monitca","CA","US")),
                                              Store("S-06",location = Location("Jersy","NJ","US")),
                                              Store("S-07",location = Location("Dallas","TX","US")),
                                              Store("S-08",location = Location("Houston","TX","US")),
                                              Store("S-09",location = Location("Miami","FL","US")),
                                              Store("S-10",location = Location("Orlando","FL","US")),
                                              Store("S-11",location = Location("Tampa","FL","US")),
                                              Store("S-12",location = Location("Miami Beach","FL","US")),
                                               Store("S-13",location = Location("Newark","NJ","US")),
                                               Store("S-14",location = Location("Atlantic City","NJ","US")),
                                              Store("S-15",location = Location("East Orange","NJ","US")),
                                              Store("S-16",location = Location("Niagara Fall","NY","US")),
                                              Store("S-17",location = Location("Albany","NY","US")),
                                               Store("S-18",location = Location("Atlanta","GA","US")),
                                              Store("S-19",location = Location("Austin","TX","US")),
                                              Store("S-20",location = Location("Forth Worth","TX","US")),
                                              Store("S-21",location = Location("Denver","CL","US")),
                                               Store("S-22",location = Location("Detriot","BO","US")),
                                              Store("S-23",location = Location("Toronto","ON","CA")),
                                              Store("S-24",location = Location("Ottawa","ON","CA")),
                                              Store("S-25",location = Location("Montreal","QC","CA")),
                                             Store("S-26",location = Location("Halifax","NS","CA")),
                                            Store("S-27",location = Location("Detriot","BO","US")),
                                          Store("S-28",location = Location("Vancouver","BC","CA")),
                                          Store("S-29",location = Location("Calgary","AB","CA")),
                                          Store("S-30",location = Location("Moncton","NB","CA")),
                                         Store("S-31",location = Location("Mexico City","MC","MX")),
                                        Store("S-32",location = Location("Cancún","MC","MX"))


    )

  retailsStores.foreach( retailStore => {
        val actorRefStore =  system.actorOf(RetailStoreActor.props(store = retailStore,posTerminals = 5),retailStore.code)
        actorRefStore ! OpenStore
     })

  //(1 to 1000).foreach(_ => println(SaleDataGenerator.generatorSaleItem))

}

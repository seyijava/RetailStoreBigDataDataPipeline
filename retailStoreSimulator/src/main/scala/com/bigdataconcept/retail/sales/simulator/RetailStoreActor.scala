package com.bigdataconcept.retail.sales.simulator

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, OneForOneStrategy, Props}
import akka.stream.ActorMaterializer
import com.bigdataconcept.retail.sales.simulator.Domain.Store
import com.bigdataconcept.retail.sales.simulator.RetailStoreActor.{CloseStore, OpenStore}


object RetailStoreActor{

     case object OpenStore

     case object  CloseStore

    def props(store: Store, posTerminals: Int)(implicit actorSystem: ActorSystem,mat: ActorMaterializer,ingestServerUrl: String) : Props = Props(new RetailStoreActor(store,posTerminals))

}


class RetailStoreActor(store: Store,  posTerminals: Int = 10)(implicit actorSystem: ActorSystem, mat: ActorMaterializer,ingestServerUrl: String) extends Actor with ActorLogging{

      override val supervisorStrategy = OneForOneStrategy(){
        case _ : NullPointerException => Restart
      }

     var posTerminalMap: Map[Int, ActorRef] = Map.empty[Int,ActorRef]

  override def receive: Receive = {
    case OpenStore => openStoreAndStartPosTerminals()
    case CloseStore => closeStoreAndStopPOSTerminal()
  }


   def openStoreAndStartPosTerminals(): Unit ={

     log.info(s"Opening Store Store Code${store.code}")

     (1 to posTerminals).foreach(terminalId => startPOSTerminal(terminalId))

     def startPOSTerminal(terminalId: Int) : Unit={
       log.info(s"Starting POS Terminal Actor Id $terminalId")
       val actorRef =  context.actorOf(POSTerminal.props(store = store, terminalId = terminalId.toString,ingestServerUrl = ingestServerUrl),s"POSTerminal-$terminalId")
       posTerminalMap += (terminalId -> actorRef)
     }
   }

  def closeStoreAndStopPOSTerminal(): Unit = {
    posTerminalMap.values.foreach( posTerminal => context.stop(posTerminal))
    context.stop(self)
  }

}

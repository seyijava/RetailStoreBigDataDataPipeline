//#full-example
package com.example


import akka.actor.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.typesafe.config.ConfigFactory



//#main-class
object AkkaQuickstart extends App {
  //#actor-system
  val config = ConfigFactory.load()

  val restConfig = config.getConfig("rest")

  println(restConfig.entrySet())
 // println(config.entrySet())
  val greeterMain  = ActorSystem()
  //#actor-system

  //#main-send-messages

}
//#main-class
//#full-example

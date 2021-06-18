package com.bigdataconcept.spark.retail.sales.analytics.config
import com.typesafe.config.ConfigFactory



trait AppConfig {

      val config = ConfigFactory.load()
      val sparkConfig = config.getConfig("spark")
      val cassandraConfig = config.getConfig("cassandra")

      val masterUrl = sparkConfig.getString("spark-master")

      val cassandraUserName = cassandraConfig.getString("username")
      val cassandraPassword = cassandraConfig.getString("password")

      val cassandraHost = cassandraConfig.getString("contact-point")

     val cassandraPort = cassandraConfig.getInt("port")

     val cassandraKeySpace = cassandraConfig.getString("key-space")
}

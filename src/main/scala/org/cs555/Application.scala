package org.cs555

import com.mongodb.spark.MongoSpark
import com.mongodb.spark._
import org.apache.spark.sql.{DataFrame, SparkSession}

object Application {

  def printArgs(args: Array[String]): Unit = {
    for (i <- args.indices) {
      val arg: String = args(i)
      printf("args[%d]: %s\n", i, arg)
    }
  }

  def printUsage(): Unit = {
    println("USAGE")
    println("\tBuild:\n\t\tsbt package")
    println("\tSubmit as JAR to Spark cluster:\n\t\t$SPARK_HOME/bin/spark-submit <submit_options> \\")
    println("\t\ttarget/scala-2.12/climate-analytics_2.12-0.1.jar")
    println()
  }

  def main(args: Array[String]): Unit = {
    val sparkMaster = "spark://lattice-100:8079"

    val sparkSession: SparkSession = SparkSession.builder
      .appName("Climate Analysis")
      .master(sparkMaster)
      .config("spark.mongodb.input.uri", "mongodb://lattice-100:27018/sustaindb.climate_cypress_hill_sk")
      .getOrCreate()

    val climateProcessing: ClimateProcessing = new ClimateProcessing(sparkSession)
    val mpbProcessing: MountainPineBeetleProcessing = new MountainPineBeetleProcessing(sparkSession)
    val noaaProcessing: NoaaProcessing = new NoaaProcessing(sparkSession)

    climateProcessing.processClimateData()
    mpbProcessing.processMpbData()
    // noaaProcessing.processNoaaData()

    sparkSession.close()
  }
}

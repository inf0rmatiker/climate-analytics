package org.cs555.mongo

import com.mongodb.spark._
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

class SparkConnector {

  def loadDataset(): Unit = {

    /* Create the SparkSession.
     * If config arguments are passed from the command line using --conf,
     * parse args for the values to set.
     */
    val spark: SparkSession = SparkSession.builder()
      .master("spark://char:30633")
      .appName("ClimateAnalytics")
      .config("spark.mongodb.input.uri", "mongodb://lattice-100/sustaindb.climate_cypress_hill_sk")
      .getOrCreate()

    val df: DataFrame = MongoSpark.load(spark)
    println(df.count)
    println(df.first().json)

    spark.close()
  }
}

package org.cs555

import org.apache.spark.sql.{DataFrame, SparkSession}
import com.mongodb.spark.config._
import com.mongodb.spark.MongoSpark
import org.apache.spark.sql.functions.col

class MountainPineBeetleProcessing(sparkSession: SparkSession) {

  def processMpbData(): Unit = {
    val readConfig: ReadConfig = ReadConfig(Map("collection" -> "mpb_cypress_hill_sk_100m"), Some(ReadConfig(sparkSession)))
    val mpbDf: DataFrame = MongoSpark.load(sparkSession, readConfig)

    val mpbPerYearDf: DataFrame = mpbDf.groupBy("YEAR").sum("MPB")

    mpbPerYearDf.show(10)
  }
}

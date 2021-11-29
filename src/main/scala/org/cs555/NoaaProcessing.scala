package org.cs555

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import org.apache.spark.sql.{DataFrame, SparkSession}

class NoaaProcessing(sparkSession: SparkSession) {
  def processNoaaData(): Unit = {
    val readConfig: ReadConfig = ReadConfig(Map("collection"-> "noaa_nam"),
      Some(ReadConfig(sparkSession)))
    val noaaDf: DataFrame = MongoSpark.load(sparkSession, readConfig)
      .select(
        "GISJOIN",
        "YYYYMMDDHHHH",
        "SNOW_COVER_AT_SURFACE_PERCENT",
        "DEWPOINT_TEMPERATURE_2_METERS_ABOVE_SURFACE_KELVIN",
        "TEMPERATURE_AT_SURFACE_KELVIN"
      )

    noaaDf.show(5)

    val saver: DataFrameSaver = new DataFrameSaver(".")

  }
}

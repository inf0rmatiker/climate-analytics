package org.cs555

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import org.apache.spark.sql.{DataFrame, SparkSession}

class NoaaProcessing(sparkSession: SparkSession) {
  def processNoaaData(): Unit = {
    processNoaaData("G0800510")
  }

  def processNoaaData(gisJoin: String): Unit = {
    import sparkSession.sqlContext.implicits._
    println(s"Processing GISJoin: $gisJoin")
    val readConfig: ReadConfig = ReadConfig(Map("collection"-> "noaa_nam"),
      Some(ReadConfig(sparkSession)))
    val noaaDf: DataFrame = MongoSpark.load(sparkSession, readConfig)
      .select(
        "GISJOIN",
        "YYYYMMDDHHHH",
        "SNOW_COVER_AT_SURFACE_PERCENT",
        "DEWPOINT_TEMPERATURE_2_METERS_ABOVE_SURFACE_KELVIN",
        "TEMPERATURE_AT_SURFACE_KELVIN",
        "RELATIVE_HUMIDITY_2_METERS_ABOVE_SURFACE_PERCENT"
      )
      .filter($"GISJOIN".contains(gisJoin))
      .withColumn("year_month_day", $"YYYYMMDDHHHH".substr(0, 8))
      .groupBy("year_month_day")
      .mean(
        "SNOW_COVER_AT_SURFACE_PERCENT",
        "DEWPOINT_TEMPERATURE_2_METERS_ABOVE_SURFACE_KELVIN",
        "TEMPERATURE_AT_SURFACE_KELVIN",
        "RELATIVE_HUMIDITY_2_METERS_ABOVE_SURFACE_PERCENT"
    )

    //noaaDf.show(5)
    //println(s"#rows = ${noaaDf.count()}")

    // val saver: DataFrameSaver =
    //   new DataFrameSaver("/s/parsons/b/others/sustain/menukaw/climate-analytics/spark_output/colorado")

    val saver: DataFrameSaver =
      new DataFrameSaver("hdfs://lattice-126:30000/user/menukaw/cs555")

    println(s"Writing output: $gisJoin")
    saver.saveAsCsv(s"$gisJoin.csv", noaaDf)

  }
}

package org.cs555

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.text.SimpleDateFormat
import java.util.Date

class ClimateProcessing(sparkSession: SparkSession) {

  def processClimateData(): Unit = {
    val readConfig: ReadConfig = ReadConfig(Map("collection" -> "climate_cypress_hill_sk"), Some(ReadConfig(sparkSession)))
    val climateDf: DataFrame = MongoSpark.load(sparkSession, readConfig)

    import sparkSession.implicits._
    val filteredDf: DataFrame = climateDf.select("YEAR_MONTH_DAY", "TOTAL_SNOW_CM")
    val mappedDatesDf = filteredDf.map(row => {
      val dateFormat: String = "yyyy-MM-dd"
      val simpleDateFormat: SimpleDateFormat = new SimpleDateFormat(dateFormat)
      val parsedDate: Date = simpleDateFormat.parse(row.getString(0))
      val timestamp: Long = parsedDate.getTime
      (timestamp, row.getInt(1))
    }).toDF("timestamp", "total_snow_cm").sort("timestamp")
    mappedDatesDf.show(100)
  }
}
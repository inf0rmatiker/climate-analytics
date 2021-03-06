package org.cs555

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.Date

class ClimateProcessing(sparkSession: SparkSession) {

  def processClimateData(): Unit = {
    val readConfig: ReadConfig = ReadConfig(Map("collection" -> "climate_cypress_hill_sk"), Some(ReadConfig(sparkSession)))
    val climateDf: DataFrame = MongoSpark.load(sparkSession, readConfig)

    import sparkSession.implicits._
    val filteredDf: DataFrame = climateDf.select("YEAR_MONTH_DAY", "TOTAL_SNOW_CM")
    filteredDf.printSchema()
    val mappedDatesDf = filteredDf.map(row => {
      val dateFormat: String = "yyyy-MM-dd"
      val simpleDateFormat: SimpleDateFormat = new SimpleDateFormat(dateFormat)
      val parsedDate: Date = simpleDateFormat.parse(row.getString(0))
      val timestamp: Long = parsedDate.getTime

      val totalSnowStr: String = row.getString(1)
      var totalSnowDbl: Double = 0.0
      try {
        totalSnowDbl = totalSnowStr.toDouble
      } catch {
        case e: NumberFormatException => println(s"Failed to parse '$totalSnowStr' to a Double, using 0.0 as default")
      }

      (timestamp, totalSnowDbl)
    }).toDF("timestamp", "total_snow_cm")

    val saver: DataFrameSaver = new DataFrameSaver("/s/chopin/b/grad/cacaleb/Jetbrains/IntelliJ/climate-analytics/output_dir")
    saver.saveAsSortedCsv("cypress_hill_sk_snow.csv", mappedDatesDf, "timestamp", isAscending = true)
  }
}
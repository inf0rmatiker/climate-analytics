package org.cs555

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col

class DataFrameSaver(val outputDirectory: String) {

  def saveAsCsv(filename: String, df: DataFrame): Unit = {
    df.coalesce(1)
      .write
      .option("header","true")
      .option("sep",",")
      .mode("overwrite")
      .csv(s"$outputDirectory/$filename")
  }

  def saveAsSortedCsv(filename: String, df: DataFrame, sortedField: String, isAscending: Boolean): Unit = {
    if (isAscending) {
      df.coalesce(1)
        .sort(col(sortedField).asc)
        .write
        .option("header","true")
        .option("sep",",")
        .mode("overwrite")
        .csv(s"$outputDirectory/$filename")
    } else {
      df.coalesce(1)
        .sort(col(sortedField).desc)
        .write
        .option("header","true")
        .option("sep",",")
        .mode("overwrite")
        .csv(s"$outputDirectory/$filename")
    }
  }

}

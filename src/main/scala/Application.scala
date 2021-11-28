import com.mongodb.spark.MongoSpark
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
    val spark = SparkSession.builder
      .appName("Simple Application")
      .master("spark://lattice-100:8079")
      .config("spark.mongodb.input.uri", "mongodb://lattice-100:27018/sustaindb.climate_cypress_hill_sk")
      .getOrCreate()

    println("GOT HERE SUCCESSFULLY")

    val df: DataFrame = MongoSpark.load(spark)
    println(df.count)
    println(df.first().json)

    spark.close()
  }
}

import mongo.SparkConnector

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
    println("GOT HEEEEEEEEREREREREE")
    printArgs(args)
//    if(args.length != 2) {
//      printUsage()
//      System.exit(1)
//    }

    val sparkConnector: SparkConnector = new SparkConnector()
    sparkConnector.loadDataset()
  }
}

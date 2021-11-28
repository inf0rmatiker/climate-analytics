name := "climate-analytics"

version := "0.1"

scalaVersion := "2.12.10"

// set the main class for 'sbt run'
Compile / run / mainClass := Some("Application")

val sparkVersion = "3.1.2"
val mongoSparkConnectorVersion = "3.0.1"

libraryDependencies ++= Seq(
  "org.mongodb.spark" %% "org.cs555.mongo-spark-connector" % mongoSparkConnectorVersion,
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
)
name := "climate-analytics"

version := "0.1"

scalaVersion := "2.12.12"

// set the main class for 'sbt run'
Compile / run / mainClass := Some("org.cs555.Application")

val sparkVersion = "3.1.2"
val mongoSparkConnectorVersion = "3.0.1"

libraryDependencies ++= Seq(
  "org.mongodb.spark" %% "mongo-spark-connector" % mongoSparkConnectorVersion,
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
)
#!/bin/bash

function print_usage {
  echo -e "USAGE\n\tsubmit.sh" 
  echo -e "\nEXAMPLE\n\tsubmit.sh"
}

if [[ $# -eq 0 ]]; then
  echo -e "Submitting Spark Job...\n"
  ${SPARK_HOME}/bin/spark-submit \
    --class org.cs555.Application \
    --master "local[8]" \
    --packages org.mongodb.spark:mongo-spark-connector_2.12:3.0.1 \
    target/scala-2.12/climate-analytics_2.12-0.1.jar
else
  print_usage
fi

#!/bin/bash

function print_usage {
  echo -e "USAGE\n\trun.sh <spark_master>"
  echo -e "\nEXAMPLE\n\trun.sh spark://char:30633"
}

if [[ $# -eq 1 ]]; then
  echo -e "Submitting Spark Job...\n"
  "${SPARK_HOME}"/bin/spark-submit \
    --class Application \
    --master "$1" \
    --deploy-mode cluster \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    target/scala-2.12/climate-analytics_2.12-0.1.jar
else
  print_usage
fi
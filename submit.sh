#!/bin/bash

function print_usage {
  echo -e "USAGE\n\tsubmit.sh" 
  echo -e "\nEXAMPLE\n\tsubmit.sh"
}

if [[ $# -eq 0 ]]; then
  echo -e "Submitting Spark Job...\n"
  ${SPARK_HOME}/bin/spark-submit \
    --class Application \
    --master spark://lattice-100:8079 \
    --deploy-mode cluster \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    target/scala-2.12/climate-analytics_2.12-0.1.jar
else
  print_usage
fi

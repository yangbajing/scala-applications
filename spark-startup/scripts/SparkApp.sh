#!/usr/bin/env bash

$SPARK_HOME/bin/spark-submit \
    --class sample.SparkApp \
    --master spark://sc-data-server-1:7077 \
    --name "sample.SparkApp" \
    --executor-memory 10G \
    --driver-memory 2G \
    --total-executor-cores 4 \
    --conf spark.serializer=org.apache.spark.serializer.KryoSerializer \
    ../target/scala-2.11/spark-startup.jar &


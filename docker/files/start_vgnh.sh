#!/bin/bash

# subindo cassandra
cassandra -f &
# criando modelo
CMD="cqlsh -f /tmp/model.cql"
$CMD
while [ $? -eq 1 ]; do
    sleep 3
    echo "Waiting for cassandra to go up..."
    $CMD 2> /dev/null
done

# iniciando report-api
(cd /opt/report-api; nodejs app.js &)

# iniciando consolidator
(cd /opt/consolidator/; spark/bin/spark-submit --master "local[10]" --class br.com.vgnh.consolidator.ConsolidatorApp consolidator-*-jar-with-dependencies.jar /tmp/collector/data 5 &)

# iniciando collector
(cd /opt/collector; java -jar collector-*.jar)

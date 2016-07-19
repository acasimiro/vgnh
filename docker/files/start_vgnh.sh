#!/bin/bash

# criando modelo no cassandra
# cassandra -f &

# CMD="cqlsh -f /tmp/model.cql"

# $CMD
# while [ $? -eq 1 ]; do
#     sleep 3
#     $CMD > /dev/null
# done

# iniciando collector
java -jar /opt/collector/collector-*.jar &

# iniciando report-api
(cd /opt/report-api; nodejs app.js)

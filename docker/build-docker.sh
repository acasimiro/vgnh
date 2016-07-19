#!/bin/bash

if [ ! -f files/spark-1.6.0-bin-hadoop2.6.tgz ]; then
    (cd files; wget http://archive.apache.org/dist/spark/spark-1.6.0/spark-1.6.0-bin-hadoop2.6.tgz)
fi

if [ ! -f files/collector-*.jar ]; then
    (cd ../collector; mvn clean package)
    cp ../collector/target/collector-*.jar files
fi

if [ ! -f files/consolidator-*.jar ]; then
    (cd ../consolidator; mvn clean package)
    cp ../consolidator/target/consolidator-*-jar-with-dependencies.jar files
fi

if [ ! -f files/app.js ]; then
    cp ../report-api/app.js files
    cp ../report-api/package.json files
fi

sudo docker build -t vgnh .

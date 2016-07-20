#!/bin/bash

if [ "`id -u 2>/dev/null`" != "0" ] ; then
    echo "You must be root."
    exit 1
fi

mkdir -p app_files

if [ ! -f app_files/spark-1.6.0-bin-hadoop2.6.tgz ]; then
    (cd app_files; wget http://archive.apache.org/dist/spark/spark-1.6.0/spark-1.6.0-bin-hadoop2.6.tgz)
fi

if [ ! -f app_files/collector-*.jar ]; then
    (cd ../collector; mvn clean package)
    cp ../collector/target/collector-*.jar app_files
fi

if [ ! -f app_files/consolidator-*.jar ]; then
    (cd ../consolidator; mvn clean package)
    cp ../consolidator/target/consolidator-*-jar-with-dependencies.jar app_files
fi

if [ ! -f app_files/app.js ]; then
    cp ../report-api/app.js app_files
    cp ../report-api/package.json app_files
fi

sudo docker build -t acasimiro/vgnh .

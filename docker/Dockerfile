FROM cassandra:3.0

RUN mkdir /opt/collector /opt/consolidator /opt/report-api

# adding collector
ADD files/collector-*.jar /opt/collector

# adding consolidator
ADD files/consolidator-*-jar-with-dependencies.jar /opt/consolidator
ADD files/spark-1.6.0-bin-hadoop2.6.tgz /opt/consolidator
RUN ln -s /opt/consolidator/spark-1.6.0-bin-hadoop2.6.tgz /opt/consolidator/spark

# adding report-api
ADD files/app.js /opt/report-api
ADD files/package.json /opt/report-api
ADD setup_npm4.sh /tmp
RUN /tmp/setup_npm4.sh

# create cassandra schema
ADD model.cql /tmp
#RUN cqlsh -f /tmp/model.cql

# starts report-api
#RUN (cd /opt/report-api; node app.js &)

# starts consolidator

# starts collector
/opt/consolidator/spark/bin/spark-submit --master "local[10]" --class br.com.vgnh.consolidator.ConsolidatorApp /opt/consolidator/consolidator-*-jar-with-dependencies.jar;


# define default command.
CMD ["bash"]

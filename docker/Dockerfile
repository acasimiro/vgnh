FROM cassandra:3.0

RUN mkdir /opt/collector /opt/consolidator /opt/report-api

# adding collector
RUN mkdir /var/log/collector
RUN mkdir -p /tmp/collector/data
ADD app_files/collector-*.jar /opt/collector

# adding consolidator
ADD app_files/consolidator-*-jar-with-dependencies.jar /opt/consolidator
ADD app_files/spark-1.6.0-bin-hadoop2.6.tgz /opt/consolidator
RUN ln -s /opt/consolidator/spark-1.6.0-bin-hadoop2.6.tgz /opt/consolidator/spark

# adding report-api
ADD app_files/app.js /opt/report-api
ADD app_files/package.json /opt/report-api
ADD files/setup_npm4.sh /tmp
RUN /tmp/setup_npm4.sh
RUN apt-get install -y nodejs
RUN (cd /opt/report-api; npm install)

# create cassandra schema
ADD files/model.cql /tmp

# starts report-api
#RUN (cd /opt/report-api; node app.js &)

# starts consolidator
#/opt/consolidator/spark/bin/spark-submit --master "local[10]" --class br.com.vgnh.consolidator.ConsolidatorApp /opt/consolidator/consolidator-*-jar-with-dependencies.jar;


RUN update-ca-certificates -f 

# adding start script
COPY files/start_vgnh.sh /
RUN chmod u+x start_vgnh.sh
ENTRYPOINT ["/start_vgnh.sh"]

EXPOSE 8080

# define default command.
# CMD ["bash"]

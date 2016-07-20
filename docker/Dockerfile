FROM cassandra:3.0

### DEPENDENCIES ###
RUN mkdir /opt/collector /opt/consolidator /opt/report-api
# collector
RUN mkdir /var/log/collector
RUN mkdir -p /tmp/collector/data
RUN update-ca-certificates -f
# consolidator
ADD app_files/spark-1.6.0-bin-hadoop2.6.tgz /opt/consolidator
RUN ln -s /opt/consolidator/spark-1.6.0-bin-hadoop2.6 /opt/consolidator/spark
# report-api
ADD files/setup_npm4.sh /tmp
RUN /tmp/setup_npm4.sh
RUN apt-get install -y nodejs

### APPLICATION ###
# collector
ADD app_files/collector-*.jar /opt/collector
# consolidator
ADD app_files/consolidator-*-jar-with-dependencies.jar /opt/consolidator
# report-api
ADD app_files/app.js /opt/report-api
ADD app_files/package.json /opt/report-api
RUN (cd /opt/report-api; npm install)
# cassandra schema
ADD files/model.cql /tmp

### STARTUP ###
COPY files/start_vgnh.sh /
RUN chmod u+x start_vgnh.sh
ENTRYPOINT ["/start_vgnh.sh"]
CMD ["bash"]

### PORTS ###
EXPOSE 8080 3000

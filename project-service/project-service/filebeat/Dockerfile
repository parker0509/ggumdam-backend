FROM docker.elastic.co/beats/filebeat:8.13.0

COPY filebeat.yml /usr/share/filebeat/filebeat.yml
USER root
RUN chown root:filebeat /usr/share/filebeat/filebeat.yml && chmod 600 /usr/share/filebeat/filebeat.yml

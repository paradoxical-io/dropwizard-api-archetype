FROM java:8

RUN apt-get update && apt-get install -y dos2unix

ADD data /data

RUN chmod +x /data/bin/service; dos2unix /data/bin/service

# set the service to run
ENTRYPOINT ["/data/bin/service"]
FROM postgres:17

RUN apt-get update && \
    apt-get install -y wget && \
    wget https://jdbc.postgresql.org/download/postgresql-42.7.4.jar -P /usr/local/share/java/ && \
    apt-get clean

ENV CLASSPATH /usr/local/share/java/postgresql-42.7.4.jar

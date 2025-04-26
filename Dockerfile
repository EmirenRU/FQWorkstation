FROM maven:3.9.9-eclipse-temurin-24
LABEL org.opencontainers.image.authors="Emil Yusupov <1032211216@pfur.ru>"
WORKDIR /builder
COPY . .
RUN mvn clean package -DskipTests -T 4C

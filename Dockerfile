FROM maven:3.9.9-eclipse-temurin-24
WORKDIR /builder
COPY . .
RUN mvn clean package -DskipTests -T 4C

FROM openjdk:21-jdk
WORKDIR /app
COPY src/main/resources/ /app/resources/
COPY target/fqw-1.0.0.jar /app/app.jar
EXPOSE 13131
ENTRYPOINT ["java", "-jar", "app.jar"]
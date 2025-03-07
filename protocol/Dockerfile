FROM openjdk:21-jdk
WORKDIR /app
COPY src/main/resources/ /app/resources/
COPY target/protocol-0.9.0.jar /app/app.jar
EXPOSE 13134
ENTRYPOINT ["java", "-jar", "app.jar"]
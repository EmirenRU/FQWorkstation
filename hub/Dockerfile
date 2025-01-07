FROM openjdk:21-jdk
WORKDIR /app
COPY target/hub-0.8.0.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
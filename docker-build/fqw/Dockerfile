FROM openjdk:21-jdk
WORKDIR /app
COPY /target/*.jar /app/app.jar
EXPOSE 13131
ENTRYPOINT ["java", "-jar", "app.jar"]
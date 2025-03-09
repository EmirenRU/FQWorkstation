FROM openjdk:21-jdk
WORKDIR /app
COPY /target/*.jar /app/app.jar
EXPOSE 13134
ENTRYPOINT ["java", "-jar", "app.jar"]
FROM openjdk:21-jdk
WORKDIR /app
COPY src/main/resources/ /app/resources/
COPY target/email-0.5.0.jar /app/app.jar
EXPOSE 13132
ENTRYPOINT ["java", "-jar", "app.jar"]
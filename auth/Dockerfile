FROM openjdk:21-jdk
WORKDIR /app
COPY src/main/resources/ /app/resources/
COPY target/auth.jar /app/app.jar
EXPOSE 13135
ENTRYPOINT ["java", "-jar", "app.jar"]
FROM openjdk:21-jdk
WORKDIR /app
COPY src/main/resources/ /app/resources/
COPY src/main/resources/template.docx /app/template.docx
COPY target/fqw-1.0.4.jar /app/app.jar
EXPOSE 13130
ENTRYPOINT ["java", "-jar", "app.jar"]
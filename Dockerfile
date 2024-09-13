FROM openjdk:21-jdk
WORKDIR /app
COPY src/main/resources/ /app/resources/
COPY src/main/resources/template.docx /app/template.docx
COPY target/app-1.0.0.jar /app/app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar"]
FROM openjdk:21-jdk
WORKDIR /app
COPY target/info-system-department-0.0.1-SNAPSHOT.jar /app/app.jar
COPY src/main/resources/template.docx /app/template.docx
EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar"]
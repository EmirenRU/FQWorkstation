FROM openjdk:21-jdk
WORKDIR /app
COPY src/main/resources/ /app/resources/
COPY src/main/resources/template.docx /app/template.docx
COPY target/info-system-department-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar"]
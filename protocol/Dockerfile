FROM maven:3.9.9-eclipse-temurin-24 as builder
WORKDIR /builder
COPY . .
RUN mvn clean package -DskipTests -T 4C

FROM eclipse-temurin:24-jre AS runtime
WORKDIR /app
COPY --from=fqworkstation-root:latest /builder/protocol/target/*.jar /app/app.jar
EXPOSE 13134
ENTRYPOINT ["java", "-Xms1g", "-Xmx8g", "-jar", "app.jar"]
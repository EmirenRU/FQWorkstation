FROM maven:3.9.6-eclipse-temurin-21 as builder
WORKDIR /builder
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=builder /builder/target/*.jar /app/app.jar
EXPOSE 13133
ENTRYPOINT ["java", "-jar", "app.jar"]
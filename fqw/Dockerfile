FROM emiren-co/fqworkstation-root:latest AS builder
WORKDIR /app
COPY . .

FROM openjdk:24 AS runtime
WORKDIR /app
COPY --from=builder /builder/fqw/target/*.jar /app/app.jar
EXPOSE 13134
ENTRYPOINT ["java", "-Xms1g", "-Xmx8g", "-jar", "app.jar"]
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/payment-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 4242
ENTRYPOINT ["java", "-jar", "app.jar"]
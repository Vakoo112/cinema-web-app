FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/notification-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 4040
ENTRYPOINT ["java", "-jar", "app.jar"]
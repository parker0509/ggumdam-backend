FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/user-service-0.0.1-SNAPSHOT.jar user-service.jar
COPY src/main/resources/application.yml /app/config/application.yml

ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]
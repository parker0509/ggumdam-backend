FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/EurekaServer-0.0.1-SNAPSHOT.jar /app/EurekaServer.jar
COPY src/main/resources/application.yml /app/config/application.yml

ENTRYPOINT ["java", "-jar", "/app/EurekaServer.jar", "--spring.config.location=file:/app/config/application.yml"]

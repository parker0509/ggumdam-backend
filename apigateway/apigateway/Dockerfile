FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/apigateway-0.0.1-SNAPSHOT.jar /app/apigateway.jar
COPY src/main/resources/application.yml /app/config/application.yml

ENTRYPOINT ["java", "-jar", "/app/apigateway.jar", "--spring.config.location=file:/app/config/application.yml"]

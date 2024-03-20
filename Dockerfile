FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/randomNumbers-1.0.jar /app

CMD ["java","-jar","randomNumbers-1.0.jar"]
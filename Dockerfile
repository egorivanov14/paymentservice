FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/*SNAPSHOT.jar app.j
RUN mkdir extracted && cd extracted && jar -xf ../app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-cp", "extracted/BOOT-INF/classes:extracted/BOOT-INF/lib/*", "com.innowise.paymentservice.PaymentserviceApplication"]
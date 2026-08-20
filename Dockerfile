FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/*SNAPSHOT.jar app.jar
RUN mkdir extracted && cd extracted && jar -xf ../app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-cp", "extracted/BOOT-INF/classes:extracted/BOOT-INF/lib/*", "com.innowise.paymentservice.PaymentserviceApplication"]
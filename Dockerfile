FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app
COPY /target/BankTestTask-0.0.1-SNAPSHOT.jar /app/bank.jar
WORKDIR /app
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "bank.jar"]
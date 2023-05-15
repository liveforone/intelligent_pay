FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/bankbook-service-1.0.jar
COPY ${JAR_FILE} BankbookService.jar
ENTRYPOINT ["java","-jar","BankbookService.jar"]
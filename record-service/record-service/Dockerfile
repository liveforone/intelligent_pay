FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/record-service-1.0.jar
COPY ${JAR_FILE} RecordService.jar
ENTRYPOINT ["java","-jar","RecordService.jar"]
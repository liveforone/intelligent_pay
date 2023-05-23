FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/remit-service-1.0.jar
COPY ${JAR_FILE} RemitService.jar
ENTRYPOINT ["java","-jar","RemitService.jar"]
FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/pay-service-1.0.jar
COPY ${JAR_FILE} PayService.jar
ENTRYPOINT ["java","-jar","PayService.jar"]
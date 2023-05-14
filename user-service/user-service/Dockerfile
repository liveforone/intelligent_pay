FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/user-service-1.0.jar
COPY ${JAR_FILE} UserService.jar
ENTRYPOINT ["java","-jar","UserService.jar"]
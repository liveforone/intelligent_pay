FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/discovery-service-1.0.jar
COPY ${JAR_FILE} DiscoveryService.jar
ENTRYPOINT ["java","-jar","DiscoveryService.jar"]
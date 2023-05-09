FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/gateway-service-1.0.jar
COPY ${JAR_FILE} GatewayService.jar
ENTRYPOINT ["java","-jar","GatewayService.jar"]
# 실행을 위한 명령어

## 카프카
```
cd C:\kafka\kafka_2.13-3.4.0

bin\windows\zookeeper-server-start.bat config\zookeeper.properties
bin\windows\kafka-server-start.bat config\server.properties
```

## 마이크로서비스
```
cd C:\Users\KYC\study\intelligent_pay\discovery-service\discovery-service\build\libs
java -jar discovery-service-1.0.jar

cd C:\Users\KYC\study\intelligent_pay\gateway-service\gateway-service\build\libs
java -jar gateway-service-1.0.jar

cd C:\Users\KYC\study\intelligent_pay\user-service\user-service\build\libs
java -jar user-service-1.0.jar

cd C:\Users\KYC\study\intelligent_pay\bankbook-service\bankbook-service\build\libs
java -jar bankbook-service-1.0.jar

cd C:\Users\KYC\study\intelligent_pay\record-service\record-service\build\libs
java -jar record-service-1.0.jar

cd C:\Users\KYC\study\intelligent_pay\remit-service\remit-service\build\libs
java -jar remit-service-1.0.jar

cd C:\Users\KYC\study\intelligent_pay\pay-service\pay-service\build\libs
java -jar pay-service-1.0.jar
```
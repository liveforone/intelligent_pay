# Intelligent Pay
* 간편 결제+송금 서비스 입니다.
* 확장에 열려있고, 유지보수가 쉬운 시스템을 만들기위해 노력하였습니다.
* Intelligent라는 가상의 회사에 여러 사업들이 이루어진다는 컨셉을 가지고 프로젝트가 진행되었습니다.
* 대부분의 서비스는 결제작업이 필요합니다. 그러나 모든 서비스에 결제를 진행하는 부분을 넣기에는 부적절하다고 판단하였고,
* 간편송금과 Intelligent의 서비스(사업)들에서 결제가 필요할때 Intelligent Pay를 사용해 간펼결제를 처리할 수 있도록도록 하였습니다.
* 즉 Intelligent의 모든 서비스에서 결제가 필요한 경우 Intelligent Pay 서비스를 이용해 결제를 처리합니다.
* 이 프로젝트는 Intelligent 회사의 첫 서비스(사업)이자 모든 서비스의 결제를 담당하는 중요한 프로젝트입니다.
* **"확장성"**, **"유지보수 간편성"**, **"자사 내 타 서비스와 연결성"**, **객체지향적인 설계/구현**
* 위의 4가지 키워드를 집중적으로 다룬 프로젝트입니다.

# 0. 목차
1. [프로젝트 소개](#1-프로젝트-소개)
2. [프로젝트 고민](#2-프로젝트-고민)
3. [서비스별 문서](#3-서비스별-문서)
4. [프로젝트 설계 문서](#4-프로젝트-설계-문서)
5. [새롭게 알게된 점](#5-새롭게-알게된-점)
6. [Intelligent Pay를 지원하는 서비스](#6-intelligent-pay를-지원하는-서비스)

# 1. 프로젝트 소개
* [Intelligent 시리즈 소개](https://github.com/liveforone/intelligent_pay/blob/master/Documents/INTELLIGENT_COMPANY.md)
* [프로젝트 소개](https://github.com/liveforone/intelligent_pay/blob/master/Documents/INTRODUCTION.md)

# 2. 프로젝트 고민점
* [validator 계층 나누기](https://github.com/liveforone/intelligent_pay/blob/master/Documents/VALIDATOR_LAYER.md)
* [CQS 패턴과 osiv](https://github.com/liveforone/intelligent_pay/blob/master/Documents/CQS_PATTERN.md)
* [결제와 결제 취소시에 주고받을 데이터](https://github.com/liveforone/intelligent_pay/blob/master/Documents/CANCEL_PROBLEM.md)
* [이벤트에 종속적인 validator의 의존도 낮추기](https://github.com/liveforone/intelligent_pay/blob/master/Documents/VALIDATOR_DEPENDENCY.md)

# 3. 서비스별 문서
* 서비스별 **요구사항**과 **설계**, **api 스펙**이 모두 기술되어 있습니다.
* [회원(user) 서비스](https://github.com/liveforone/intelligent_pay/blob/master/Documents/README_USER.md)
* [계좌(bankbook) 서비스](https://github.com/liveforone/intelligent_pay/blob/master/Documents/README_BANKBOOK.md)
* [거래내역(record) 서비스](https://github.com/liveforone/intelligent_pay/blob/master/Documents/README_RECORD.md)
* [송금(remit) 서비스](https://github.com/liveforone/intelligent_pay/blob/master/Documents/README_REMIT.md)
* [결제(pay) 서비스](https://github.com/liveforone/intelligent_pay/blob/master/Documents/README_PAY.md)

# 4. 프로젝트 설계 문서
* [아키텍처와 전반설계](https://github.com/liveforone/intelligent_pay/blob/master/Documents/DESIGN.md)
* [DB 설계와 모델링](https://github.com/liveforone/intelligent_pay/blob/master/Documents/DATABASE_DESIGN.md)
* [마이크로 서비스 통합 설계](https://github.com/liveforone/intelligent_pay/blob/master/Documents/MICROSERVICE_DESIGN.md)
* [화면 설계](https://github.com/liveforone/intelligent_pay/blob/master/Documents/INTERFACE_DESIGN.md)

# 5. 새롭게 알게된 점
* [페인 클라이언트 http 메소드별 파라미터](https://github.com/liveforone/intelligent_pay/blob/master/Documents/FEIGN_HTTPMETHOD.md)
* [DTO 프로젝션 in 쿼리 DSL](https://github.com/liveforone/intelligent_pay/blob/master/Documents/DSL_DTO_PROJECTION.md)

# 6. Intelligent Pay를 지원하는 서비스
* [Intelligent Store]() : 예정(제작 미완료)
* [Intelligent Booking]() : 예정(제작 미완료)

## 동시성
* 출금할때 보통 발생하는데, 이 프로젝트에서는 걱정하지 않아도된다.
* 이유는 자동이체가 지원되지 않는다. 즉 외부에서 출금 request를 하지 않는다.
* 출금을 하는 경우는 딱 한번 결제시에 존재하며, 결제를 다양하게 하지 않기때문에 동시성문제에서 자유롭다.

## 주요 포인트
api + DB + 함수 + 마이크로서비스 : 의존성 제거하기 + 모듈화 + 응집도 높이기 + 확장/유지보수에 유연하게 대처가능

## 결제 서비스
결제 시에는 반드시 입출금 금액이 잔액을 초과하는지 확인
더티체킹한다.
페인서비스로 계좌 서비스와 통신한다.
카프카 프로듀서 필요
결제 취소시 입금통장번호, 출금통장번호 필요.
### 결제시 고민점
결제로직에서는 표준화된 body 규격이 필요함.
즉 rest-api를 호출하여 결제를 요청하는 경우 통일된 값을 받아야함.
이를 통해 유지보수가 쉬워짐.
rest-api도 난잡하게 이것저것 막 만드는것이 아니라
극도로 모듈화시켜서 최대한 깔끔하게 rest-api를 만들어내야한다.
즉 쇼핑몰에서 결제를 요청하건, 예약서비스에서 요청하건,
외부에서 요청하건 결제 api는 하나여야하고, 따라서 규격은 모두동일해야한다.

## api, db, 함수 의존성/결합도 최소화 - 고민점
api를 종속적으로 만들지 말아라. db도 나누고, 도메인도 나누고(마이크로서비스), api도 마치 함수처럼 나눈다. 
마이페이지를 위한 함수를 만들지말고 여러군데에서 사용할 수있도록 하기위해 함수를 만드는 것 처럼 api도 그러하다. 
db를 설계하고, 전체 아키텍처를 설계할때도 마찬가지이지만 성능은 맨 마지막에 고려해야한다. 
왜냐하면 성능을 튜닝하는 법은 너무 많기 때문이다. 성능에 목매면 설계가 박살날수있다.
설계할때 프론트 단까지 모든 api를 설계하고, 백엔드는 프론트에 전달해줄 api만 만들면된다. 
백엔드에서 모든 api를 다 처리하려고하니깐 문제가 생겼던 것이다. 
프론트와 백은 분리가되어있으니 백은 오로지 데이터 전달을 위한 통신용 api설계에만 집중하면 된다.
-> 마치 아래에서 소개된 상품을 조회할때 여러 서비스에서 필요한 데이터들을 가져와 최종적으로 조합하듯 말이다.
-> 데이터를 아주 섬세하게 저장하고 섬세하게 가져오자!!
-> 외부 api로 데이터를 가져오는것을 너무 두려워 해선 안될것 같음.
-> 화면에 종속되지 않는 api는 파라미터에 데이터를 끼워보내기 보단, json 으로(dto로) 보내는것이 더욱 좋은 방법이다. 화면에 무슨 값이 있으니깐 이값을 어떻게 보내고 이런식의 생각은 좋지 않다.

## 게이트웨이 분리 시 서비스간 통신
* 페인서비스는 게이트웨이가 분리되어도 충분히 페인클라이언트를 통해 연결가능하게 해준다.
* 실제 도메인(url)을 부여받으면 아무 상관이없다. 기업에서 api 제공하듯 제공하면 끝나는 문제인데, 나는 테스트를 해야하니...연결을 시켜야한다.

## 할일
* 입금, 송금, 결제를 하여서 실제 거래내역 생성 + 잔액 변화 테스트

## 프로젝트에서 반드시 할것
* validator는 dto를 이용해 소통하고, 특히나 데이터를 조회하여 검증하는 함수의 경우 이벤트에 종속되게 설계한다.
* 커맨드 쿼리 분리했다면 osiv 끄기
* username 으로 주인 검증하기(외부 제공 api는 금지)
* dto 이름 requestDto로 하기
* 검증 쿼리 따로 만들기
* 계층별 validator 분리하기

## 향후
* 리팩토링 문서만들기
* 리팩토링 문서에는 예정된 리팩토링(리팩토링 할것)과
* 완료한 리팩토링에 대해 정리한다.
* 게이트웨이 설정 코드로 하기
* 거래내역을 api가 아니라 카프카로 한다. 사실상 거래내역은 실패이유가 단 한가지다. 그것은 서버 장애로 거래내역 서비스가 죽었을때이다. 되돌릴 수도 없는 api보단 카프카를 이용해서 서버가 다시 살아나면 거래내역이 생성되도록 하는것이 더 좋다.

## intelligent store
* 반드시 상점 서비스에서 상점의 계좌번호를 가지고 있어야한다.
* 이것이 상품에 있어서는 안된다.(상점에 있으면 중복될 일도 없음)
* 또한 결제나 결제 취소 모두 사용자로 부터 계좌를 입력받는다.
* 상점 서비스에 계좌 DB도 만들어서 계좌번호 여러개 저장하게 하기
* 왜냐하면 페이서비스로만 결제할 것이 아니니깐
* 일반적으로 가능한 은행의 계좌를 상점의 경우에는 다 저장하도록 해야함
* 주문시 주문내역은 하나를 저장하고, 계좌번호 네이밍을 구매자 계좌, 판매자 계좌로 네이밍하여 저장

## 명령어 -> detach 실행 편리함을 위한
```
bin\windows\zookeeper-server-start.bat config\zookeeper.properties

bin\windows\kafka-server-start.bat config\server.properties

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
```
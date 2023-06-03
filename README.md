# Intelligent Pay
* 간편 결제+송금 서비스 입니다.
* 확장에 열려있고, 유지보수가 쉬운 시스템을 만들기위해 노력하였습니다.
* Intelligent라는 가상의 회사에 여러 사업들이 이루어진다는 컨셉을 가지고 프로젝트가 진행되었습니다.
* 대부분의 서비스는 결제작업이 필요합니다. 그러나 모든 서비스에 결제를 진행하는 부분을 넣기에는 부적절하다고 판단하였고,
* 간편송금과 Intelligent의 서비스(사업)들에서 결제가 필요할때 Intelligent Pay를 사용해 간펼결제를 처리할 수 있도록도록 하였습니다.
* 즉 Intelligent의 모든 서비스에서 결제가 필요한 경우 Intelligent Pay 서비스를 이용해 결제를 처리합니다.
* 이 프로젝트는 Intelligent 회사의 첫 서비스(사업)이자 모든 서비스의 결제를 담당하는 중요한 프로젝트입니다.
* **"확장성"**, **"유지보수 간편성"**, **"자사 내 타 서비스와 연결성"**, **"객체지향적인 설계/구현"**, **"도메인에 맞는 분산 트랜잭션 롤백처리"**
* 위의 5가지 키워드를 집중적으로 다룬 프로젝트입니다.

# 0. 목차
1. [프로젝트 소개](#1-프로젝트-소개)
2. [프로젝트 고민점](#2-프로젝트-고민점)
3. [서비스별 문서](#3-서비스별-문서)
4. [프로젝트 설계 문서](#4-프로젝트-설계-문서)
5. [새롭게 알게된 점](#5-새롭게-알게된-점)
6. [Intelligent Pay를 지원하는 서비스](#6-intelligent-pay를-지원하는-서비스)
7. [리팩토링 문서](#7-리팩토링-문서)

# 1. 프로젝트 소개
### 프로젝트 소개 문서
* [Intelligent 시리즈 소개](https://github.com/liveforone/intelligent_pay/blob/master/Documents/INTELLIGENT_COMPANY.md)
* [프로젝트 소개](https://github.com/liveforone/intelligent_pay/blob/master/Documents/INTRODUCTION.md)
### 기술스택
* Framework : Spring Boot 3.1.0 & Spring Cloud(2022.0.2)
* Lang : Java17
* Data : Spring Data Jpa & Query Dsl & MySql
* Security : Spring Security & Jwt
* Service Communication : Apache Kafka(Async), Open Feign Client(Sync)
* Container : Docker & Docker-compose
* Test : Junit5
* Util : Apache Commons Lang3, LomBok

# 2. 프로젝트 고민점
* [validator 계층 나누기](https://github.com/liveforone/intelligent_pay/blob/master/Documents/VALIDATOR_LAYER.md)
* [CQS 패턴과 osiv](https://github.com/liveforone/intelligent_pay/blob/master/Documents/CQS_PATTERN.md)
* [결제와 결제 취소시에 주고받을 데이터](https://github.com/liveforone/intelligent_pay/blob/master/Documents/CANCEL_PROBLEM.md)
* [이벤트에 종속적인 validator의 의존도 낮추기](https://github.com/liveforone/intelligent_pay/blob/master/Documents/VALIDATOR_DEPENDENCY.md)
* [api 결합도 최소화](https://github.com/liveforone/intelligent_pay/blob/master/Documents/API_COUPLING.md)
* [분산 트랜잭션 환경에서 롤백](https://github.com/liveforone/intelligent_pay/blob/master/Documents/DISTRIBUTION_ROLLBACK_PROBLEM.md)

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
* [Intelligent Taxi](https://github.com/liveforone/intelligent_taxi) : 위치기반 택시 승차 o2o 서비스
* [Intelligent Store](https://github.com/liveforone/intelligent_store) : 이커머스 서비스, 예정(미완료)
* [Intelligent Booking]() : 예정(미완료)

# 7. 리팩토링 문서
* [리팩토링 문서](https://github.com/liveforone/intelligent_pay/blob/master/Documents/REFACTORING.md)
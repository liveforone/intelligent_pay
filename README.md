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

# 3. 서비스별 문서
* 서비스별 **요구사항**과 **설계**, **api 스펙**이 모두 기술되어 있습니다.
* [회원(user) 서비스](https://github.com/liveforone/intelligent_pay/blob/master/Documents/README_USER.md)
* [계좌(bankbook) 서비스](https://github.com/liveforone/intelligent_pay/blob/master/Documents/README_BANKBOOK.md)
* [거래내역(record) 서비스](https://github.com/liveforone/intelligent_pay/blob/master/Documents/README_RECORD.md)
* [입출금(statement) 서비스]()
* [결제(pay) 서비스]()

# 4. 프로젝트 설계 문서
* [아키텍처와 전반설계](https://github.com/liveforone/intelligent_pay/blob/master/Documents/DESIGN.md)
* [DB 설계와 모델링](https://github.com/liveforone/intelligent_pay/blob/master/Documents/DATABASE_DESIGN.md)
* [마이크로 서비스 통합 설계](https://github.com/liveforone/intelligent_pay/blob/master/Documents/MICROSERVICE_DESIGN.md)
* [화면 설계](https://github.com/liveforone/intelligent_pay/blob/master/Documents/INTERFACE_DESIGN.md)
* [수익모델]()

# 5. 새롭게 알게된 점

# 6. Intelligent Pay를 지원하는 서비스
* [Intelligent Store]() : 예정(제작 미완료)
* [Intelligent Booking]() : 예정(제작 미완료)

## 동시성
* 출금할때 보통 발생하는데, 이 프로젝트에서는 걱정하지 않아도된다.
* 이유는 자동이체가 지원되지 않는다. 즉 외부에서 출금 request를 하지 않는다.
* 출금을 하는 경우는 딱 한번 결제시에 존재하며, 결제를 다양하게 하지 않기때문에 동시성문제에서 자유롭다.

## 락
낙관적락은 update쿼리에 쓸수 없고, select 쿼리에만 쓸수 있다.
다만 update쿼리에는 비관적 락은 사용가능하다.
문제는 update에 낙관적 락을 걸 수 없으므로, 오로지 더티체킹으로만 변경할때 낙관적락이 유용한데, 업데이트시 조건을 넣는등 임의의 코드를 날릴때에는 사용이 불가능하다.
접근이 많은경우, 일례로 좋아요 수나, 상품의 수량같은 것에는 낙관적락이 좋으나,
접근이 별로없는 계좌나(동시성 발생확률이 낮음), 예약시스템(가게당 예약이 몇개 없음)의 경우에는 비관적 락이 더 좋다.
jpa에서 @Version 어노테이션을 붙인 버전 필드를 관리한다.
따라서 업데이트 쿼리가 나가면 자동으로 version 필드를 + 1한다.
그러나 벌크연산에서는 jpa를 무시하므로, 임의로 version의 값을 +1 해주어야한다.
쿼리 dsl에서는 .setLockMode(LockModeType.OPTIMISTIC)로 락을 건다.
예외 : OptimisticLockingFailureException
임의의 쿼리, 즉 직접 작성한 update쿼리에서도 version이 자동으로 잘 업데이트 되는지 확인

## 비밀번호 -> 설계
회원 비밀번호랑 계좌 비밀번호를 다르게 해야함.

## 벌크연산
여러값을 바꾸는 벌크연산 후에는 em.clear로 영속성 컨텍스트를 비워주어야 데이터 불일치가 해결된다.
벌크연산과 일반 update를 고려해서 쿼리를 짜라.
동시성 제어시 피해보지 않도록 설계해야한다.

## 주요 포인트
api + DB + 함수 + 마이크로서비스 : 의존성 제거하기 + 모듈화 + 응집도 높이기 + 확장/유지보수에 유연하게 대처가능

## 통신
내부에서의 통신은 카프카의 사용이 가능하지만,
외부 통장에서 접근하는 즉 pg의 경우에는 rest-api로 통신하므로
카프카를 활용할 수 없다.

## 입출금 서비스
입금, 타 페이계좌에 송금, 타행 계좌에 송금
입금 => 외부 은행계좌에서 pay 계좌로 송금처리하여 pay의 포인트로 전환
외부계좌로 출금시 1% 수수료 
항상 먼저 잔액을 체크하도록 계좌 서비스에 command 요청을 하여 bool 값을 리턴받고 진행한다.
외부로부터 입금을 받을때에는 상관없다. 그것은 외부 타행 계좌 사정이기 때문이다. 출금이나 결제 시에는 반드시 계좌를 체크한다.
입출금 시에는 반드시 입출금 금액이 잔액을 초과하는지 확인
더티체킹한다.
페인서비스로 계좌 서비스와 통신한다.
카프카 프로듀서 필요
타행 계좌에 송금하는 경우 pg사 api를 이용하기 때문에 생략한다.

## 결제 서비스
결제 시에는 반드시 입출금 금액이 잔액을 초과하는지 확인
더티체킹한다.
페인서비스로 계좌 서비스와 통신한다.
카프카 프로듀서 필요

## 결제시 고민점
결제로직에서는 표준화된 body 규격이 필요함.
즉 rest-api를 호출하여 결제를 요청하는 경우 통일된 값을 받아야함.
이를 통해 유지보수가 쉬워짐.
rest-api도 난잡하게 이것저것 막 만드는것이 아니라
극도로 모듈화시켜서 최대한 깔끔하게 rest-api를 만들어내야한다.
즉 쇼핑몰에서 결제를 요청하건, 예약서비스에서 요청하건,
외부에서 요청하건 결제 api는 하나여야하고, 따라서 규격은 모두동일해야한다.

## 타 마이크로서비스 제공 api 예외처리 - 고민점
* respones entity나 일반적인 형태의 json을 리턴하는 api가 아닌
* 타 마이크로서비스에서 요청하면 이벤트를 처리하고, 요청한 서비스에서 리턴값을 판별해서 알맞는 처리를 하는 api가 있다.
* 이 api의 경우에는 리턴값으로 boolean을 사용하는데,
* 다른것은 문제되지 않지만 validation을 하고나서 예외를 터뜨리는것이 문제다.
* 이 경우 custom 예외를 boolean값을 전달하도록 하는 예외로 다시 만들고,
* 예외가 터졌을때 false를 리턴하도록 하면된다.
* 코드 첨부

## 기능
카카오페이처럼 자체 머니 방식으로 하기.
타은행 계좌에 이체와 현 계좌로의 이체가 모두가능함
이커머스 서비스에서 계좌 잔액 확인할 수 있도록 api제작
송금은 구현가능하나, 결제의 경우에는 api를 통해 atm입출금 같은 느낌으로 처리하면된다.
조건은 카드를 긁거나, qr을 찍었을때, 또는 온라인으로 결제하여 위의 api로 들어왔다고 가정하면된다.
pg처럼 외부에 제공하는 api도 있어야함.

## api, db, 함수 의존성 최소화 - 고민점
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

## cqs 패턴 - 고민점 or 설계
[카프카]
카프카를 사용시에도 cq를 구분하라.
즉 command 디렉터리에 producer를 넣고
query 디렉터리에 consumer를 넣어서 처리하라.
[cqs]
이 메서드를 호출 했을 때, 내부에서 변경(사이드 이펙트)가 일어나는 메서드인지, 아니면 내부에서 변경이 전혀 일어나지 않는 메서드인지 명확히 분리하는 것이지요.
그렇게 되면 데이터 변경 관련 이슈가 발생했을 때, 변경이 일어나는 메서드만 찾아보면 됩니다^^
정말 크리티컬한 이슈들은 대부분 데이터를 변경하는 곳에서 발생하지요.
변경 메서드도 변경에만 집중하면 되기 때문에 유지보수가 더 좋아집니다.
제가 권장하는 방법은 insert는 id만 반환하고(아무것도 없으면 조회가 안되니), update는 아무것도 반환하지 않고, 조회는 내부의 변경이 없는 메서드로 설계하면 좋습니다^^

커맨드와 쿼리를 분리할때 그것이
db단까지 분리한 cqrs라 할지라도
변경이 아닌 경우
즉 insert에 한해서는 리턴값을 두도록한다.
리턴이 존재해야 테스트를 작성하능것더 가능하고
클라이언트에게 id를 리턴하능것도 가능하기 때문이다.
커맨드 쿼리 분리 후 osiv 끄기.
혹은 도메인 분리
핵심은 클라이언트에게 전달하는 최종값을 모두 가지고 쿼리에서 처리하고 컨트롤러에 넘기는것이다. 
osiv가 트랜잭션 범위내(서비스, 리파지)에만 머물기 때문에.
그 안에서 매퍼를 이용해 변환하고, 등등 모든 일을 처리해서 컨트롤러에는 결과값만 띡 가져다준다.
사실 마이크로서비스 별로 어떤 서비스는 조회가많고
어떤 서비스는 명령이 많다.
이에 따라 많은 것들에 집중할 수 있게 시작부터 분리하는것은 아주 좋다.
이런 경우가 아니라도 일반적인 상황에서는 조회하는 것이 더 많기때문에 시작부터 분리하고 가면 좋다.
XxCommandService
XxQueryService
https://wonit.tistory.com/629
https://velog.io/@sangmin7648/%EC%84%9C%EB%B9%84%EC%8A%A4-%EA%B3%84%EC%B8%B5-CQS%EB%A5%BC-%EC%83%9D%ED%99%9C%ED%99%94-%ED%95%98%EC%9E%90-814vmzx1
https://wonit.tistory.com/628
[cqrs]
Write, Read가 분리되어 있는 데이터베이스로 가정하고 설명드릴게요.
이렇게 분리가 되어 있는 경우 등록과 조회는 각각의 데이터베이스 용도에 맞도록 분리해서 사용하면 됩니다.
다만 질문하신 것 처럼 변경하자마자 그 결과를 바로 받아서 사용해야 하는 특별한 경우가 있습니다. 
그런데 Wrtie에서 아직 Read에 싱크가 안되었기 때문에 Write를 하자마자 바로 Read에서 읽어야 하면 데이터가 누락될 수 있습니다.
이런 특별한 경우에 한정해서는 Write 데이터베이스에서 쓰고 Write 데이터베이스에서 바로 읽어오는 것이 
데이터 동기화 이슈가 없는 가장 안전한 방법입니다.

## 페인클라이언트 - 새롭게 알게된점
일반적인 api도 마찬가지 이지만
request body는 command의 경우에만 가지고 있다.
get 매핑의 한해서는 pathvariable이나 request param처럼 url에 정보를 넣어서 전달한다.

## dto projection - 새롭게 알게된점
생성자를 사용하는 경우 : @AllArgsConstructor 필요, setter가 필요 없다.
Projections.fields 필드 직접접근
필드에 값을 딱 꽂아주기 때문에 setter와 기본생성자가 필요없습니다.
Projections.bean 프라퍼티 접근
setter(bean)를 통해 데이터를 인젝션 해주며 기본 생성자가 무조건 필요하다.

## 쿠팡 분산 시스템
https://medium.com/coupang-engineering/%EB%8C%80%EC%9A%A9%EB%9F%89-%ED%8A%B8%EB%9E%98%ED%94%BD-%EC%B2%98%EB%A6%AC%EB%A5%BC-%EC%9C%84%ED%95%9C-%EC%BF%A0%ED%8C%A1%EC%9D%98-%EB%B0%B1%EC%97%94%EB%93%9C-%EC%A0%84%EB%9E%B5-184f7fdb1367

## 연쇄 삭제 금지
* 유저가 탈퇴하면 a서비스에 삭제 메세지보내고, a는 b에보내고.. 너무 의존적이고 연쇄적이다.
* 각각 유저는 a, 유저는 b이렇게 메세지를 보내는것이 옳다.

## 계층별 유효성 검증 나누기 - 고민점
* 사실 상 모든 계층에서 검사를 해야한다.
* 그런데 이전에 필자의 방식은 컨트롤러에서 모든 검증을 마치는 것이 었다.
* 이렇게 하기 보다는 계층별로 내려가면서 유효성을 검증하는데,
* 점점 깊게 검증하는 것이다.
* 컨트롤러에서는 입력값이 자릿수라던지, 즉 바인딩에 관한 유효성을 검증하여 다음 계층으로 보내고,
* 최종적으로 도메인 로직이 돌아가는 엔티티에서는 입력값이 도메인 로직의 입장에서 유효한지 검증한다.
* 즉 컨트롤러 부터 얕게 시작하여 엔티티로 가면 갈 수록 유효성의 범위가 깊어지고,
* 또한 자신이 하는 일에 대해서만 validation을 하는 것이 적절하다.
### 형태
* 가장 일반적인 형태는 컨트롤러에서는 입력받은 값(dto)를 그대로 전달하고, 그 dto에서 값을 추출해 validation 한다.
* validator를 호출하는 함수는 위의 형태도 하지만, 
* validator의 경우 파라미터를 dto로 하면 해당 이벤트 종속적으로 변하기에, dto를 파라미터로 받지 않는다.

## 계층간 분리 - 고민점(대표적 : 유저서비스)
* 계층간 의존성을 떨어뜨리기 제일 쉬운 방법이 바로 dto이다.
* dto를 주고 받는것은 하나의 값을 주고 받는것보다 훨씬 의존성이 떨어진다. 따라서 계층간에 철저한 분리는 dto를 표준화하여 서로 통일하고
* 변경이 생기면 dto만 변경하도록 한다.
* 이러면 서로를 호출하는 함수사이에서 파라미터가 변경되거나 모호하여 논리적 오류를 발생하는 문제에서 쉽게 벗어날 수 있다.
* validator의 경우 파라미터를 dto로 하면 해당 이벤트 종속적으로 변하는 반면,
* 일반적인 계층에서 파라미터를 dto로 하면 그런 문제는 생기지 않고 오히려 장점이 늘어난다.

## 할일
* 계층별 validator 분리하고(서비스 계층의 경우 async 걷어내야한다. 앞으로 서비스계층에 validator가 들어가는 경우 async를 넣지 말자), 
* 계좌에서 타 서비스에서 호출함 api에서 도메인 로직이 동작할때 예외 발생하면 예외던지기
* controller advice에서 false를 넘기기 때문에 상관없음
* 여튼 어떠한 경우던 컨트롤러만 validator를 두지말고,
* 도메인 로직이 작동하는 경우에도 예외가 충분히 발생하므로(컨트롤러 validator에서 잡지못하는 경우에) 이 경우에는 도메인 로직에 validator를 만들어 처리하라.
* 송금 + 결제를 할때 내 계좌 출금을 먼저하고
* 타 계좌에 입금을하는데, 입금 함수를 그대로 쓸경우 함수끼리의 의존성이 발생한다. 유의

## 명령어 -> detach 실행 편리함을 위한
```
cd C:\Users\KYC\study\intelligent_pay\discovery-service\discovery-service\build\libs

java -jar discovery-service-1.0.jar

cd C:\Users\KYC\study\intelligent_pay\gateway-service\gateway-service\build\libs

java -jar gateway-service-1.0.jar

cd C:\Users\KYC\study\intelligent_pay\user-service\user-service\build\libs

java -jar user-service-1.0.jar

cd C:\Users\KYC\study\intelligent_pay\bankbook-service\bankbook-service\build\libs

java -jar bankbook-service-1.0.jar
```
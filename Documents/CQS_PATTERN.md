# CQS 패턴과 osiv

## 고민점
* 이번 프로젝트의 핵심 목표인 높은 확장성, 쉬운 유지보수성을 얻기위해서 필요한
* api, 함수 등의 결합도를 낮추고 어떻게 하나의 일만 할 수 있을지 고민하였다.
* api를 통해 힌트를 얻었는데, 그것은 바로 post, put, delete, get 등의 이벤트 방식으로 나누어서 api를 일반적으로 호출한다는것이다.
* 그렇다면 이것을 함수에도 적용할 수 있지 않을까? 라는 생각이 들었다.

## CQS
* command와 query로 DB와 이벤트 핸들링 등을 나누는 cqrs라는 msa의 한 부분에 대해 공부한적이 있다.
* 이와 비슷하게 함수를 처리하면되지 않을까? 라는 생각에 검색을 하였더니
* cqs라는 패턴이 존재하였다. cqrs와 다른점은 db를 나눈다는 것이었고, 이외에 나머지는 거의 동일했다.

## 적용
* 이를 프로젝트에 아래와 같이 적용했다.
* command를 처리하는 service인 commandService와
* query를 처리하는 service인 queryService로 나누었다.
* commandService의 함수들은 리턴값이 존재하지 않는다.
* 예외의 경우가 있는데, 생성(create == insert)을 처리하는 함수의 경우 예외적으로 id나 식별값을 리턴할 수 있게 하였다.
* queryService의 함수들은 절대로 command를 처리하지 않는다.
* 오로지 조회만 처리한다.
* 또한 카프카의 경우 producer건 consumer건 명령을 처리하므로 command에 둔다.

## 장점
* 일반적으로 service 계층에는 command 함수보다는 query 를 처리하는 함수가 더 많다.
* 유틸클래스 들도 쿼리를 위한 것들이 거의 주를 이루는데
* 이를 한번에 분리를 하니깐 관리하기가 수월해졌다.
* 또한 함수를 찾기도 쉬워졌다.
* 더 나아가 함수들이 오로지 하나의 이벤트만 처리하기에 사이드 이펙트도 생기지 않았다.
* 그리고 문제가 발생하는 부분들은 주로 변경하는 부분들인데,
* 문제가 발생하면 변경하는 부분만 딱 꼬집어서 살펴볼 수 있어서 에러 해결시간이 더욱 빨라졌다.

## 리턴 값
* 생성을 하는 경우에만 예외적으로 식별자를 리턴한다고 하였다.
* 이외의 부분들에서는 절대로 명령을 처리할때 값을 리턴해선 안된다.
* 생성시 값을 리턴하는 이유는 테스트 작성시 식별자가 필요하기도하고,
* 생성을 하고 로그를 남길때도 식별자를 기준으로 로그를 남기기 때문이다.
* CQRS로 DB가 분리된 경우에도 마찬가지로 insert 작업의 경우에
* 쓰기 DB에서 값을 바로 읽어서 전달하면 된다.
* 그래야 싱크 문제가 발생하지 않기 때문이다.

## OSIV
* 커맨드와 쿼리를 분리하면 서비스에서 mapper를 이용해 전달할 값을 다 분리하는 경우에 osiv를 끄는것이 가능하다.
* osiv는 컨트롤러가 클라이언트에 값을 전달할때 까지 영속성 컨텍스트를 물고있다.
* 따라서 osiv를 on하는 경우 성능문제가 발생해 장애가 발생하는 경우가 종종생긴다.
* osiv를 끄는것이 베스트인데, 그렇기 위해서는 쿼리 서비스에서 전달할 값을 모두 만들어 완료하고 전달하면 문제없이 사용이 가능하다.
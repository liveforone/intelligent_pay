# 쿼리 dsl에서 dto projection

## dto projection 방법
* 쿼리 dsl에서 dto 프로젝션을 사용하는 방법은 여러종류가 있다.
* projection.constructor -> 생성자
* Projections.fields -> 필드 직접 접근
* Projections.bean -> 프라퍼티 접근

## 주의점
* 위의 방법에 따라 필요한 것들이 조금씩 다르다.
* 아래의 필요한 요소를 하나라도 지키지 않으면 실패하여 에러를 발생시킨다.

## 생성자를 사용하는 경우
* @AllArgsConstructor 필요
* setter 필요 없음

## 필드 직접 접근
* 필드에 값을 딱 꽂아주기 때문에 
* setter와 기본생성자가 필요없음.

## 프라퍼티 접근
* setter(bean)필요
* 기본 생성자(no args)가 무조건 필요하다.
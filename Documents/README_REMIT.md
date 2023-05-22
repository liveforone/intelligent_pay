# 송금 서비스
* 타행 계좌로부터의 입금과 송금을 담당하는 서비스입니다.

## 요구사항
* DB는 존재하지 않으며 command를 처리하는 마이크로 서비스입니다.
* 입금은 외부에서 요청하는 이벤트이고
* 송금은 사용자가 내부에서 요청하는 이벤트입니다.
* 계좌에 돈을 채우기위해서는 타행계좌에서 페이 서비스에 입금 요청을 해야합니다.
* 사용자는 송금이 본인의 타행계좌, 타인의 타행계좌, 타인의 페이계좌로 송금이 가능합니다.

## API 설계
```
[POST] /deposit : 입금
[POST] /remit : 송금
```

## Json body 예시

## 서비스 간 통신

open feign 

거래내역 서비스
계좌서비스의 api 사용함

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
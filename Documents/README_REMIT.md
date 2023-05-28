# 송금 서비스
* 타행 계좌로부터의 입금과 
* 다양한 은행으로 송금을 담당하는 서비스입니다.

## 요구사항
* DB는 존재하지 않으며 command를 처리하는 마이크로 서비스입니다.
* 입, 송금시 표준화 json body 규격을 지켜서 입력받습니다.
* 입금은 외부에서 요청하는 이벤트이고
* 송금은 사용자가 내부에서 요청하는 이벤트입니다.
* 계좌에 돈을 채우기위해서는 타행계좌에서 페이 서비스에 입금 요청을 해야합니다.
* 사용자는 송금이 본인의 타행계좌, 타인의 타행계좌, 타인의 페이계좌로 송금이 가능합니다.
* 타행계좌에 송금시 1%의 수수료가 붙습니다.
* 거래내역 서비스, 계좌 서비스와 통신합니다.

## API 설계
* 모든 api는 외부에 제공함
```
[POST] /deposit : 입금, 외부제공
[POST] /remit : 송금
```

## Json body 예시
```
[입금]
{
    "bankbookNum" : "1234567891234",
    "money" : 40000,
    "otherBankbookNum" : "9876543219876"
}

[송금]
{
    "bankbookNum" : "1234567891234",
    "money" : 40000,
    "password" : "12345678",
    "otherBankbookNum" : "9876543219876"
}
```

## 서비스 간 통신
### 입금 요청
* 계좌서비스에 요청
* bool 리턴받음
```
[POST] /provide/add/balance
```
### 출금 요청
* 계좌서비스에 요청
* bool 리턴받음
```
[POST] /provide/subtract/balance
```
### 입금 거래내역 생성 요청
* 거래내역 서비스에 요청
```
deposit-record
```
### 출금 거래내역 생성 요청
* 거래내역 서비스에 요청
```
withdraw-record
```

## 표준화 body 규격
* 표준화 규격은 서비스간 통신을 할때 반드시 지켜야하는 json body 규격을 의미합니다.
```
[입금 - 계좌서비스]
{
    "bankbookNum",
    "money"
}

[출금 - 계좌 서비스]
{
    "bankbookNum",
    "money",
    "password"
}

[거래내역 서비스]
{
  "title": "홍길동 입금",
  "bankBookNum": "1234567894321",
  "money": 40000
}
{
  "title": "홍길동 출금",
  "bankBookNum": "1234567894321",
  "money": 40000
}
```

## 송금 실패시 롤백
* 송금시에는 송금 요청 계좌 출금 -> 송금 받는 계좌 입금 순으로 진행한다.
* 송금 요청 계좌 출금이 실패하면 그대로 종료하면되지만, 송금 받는 계좌의 입금이 실패하면 이전에 발행했던 이벤트를 롤백해야한다.
* 따라서 이 경우 입금 실패가 발생하면, 송금 요청 계좌에 출금한 금액만큼 다시 입금 이벤트를 발행하여
* 잔액을 롤백하여주었다.
* 아래는 코드이다.
```
//송금 함수
public void remit(RemitRequest requestDto) {
        SubtractBalanceRequest subtractRequest = SubtractBalanceRequest.builder()
                .bankbookNum(requestDto.getBankbookNum())
                .money(requestDto.getMoney())
                .password(requestDto.getPassword())
                .build();
        //출금 이벤트 발행
        boolean subtractBalanceResult = bankbookClientWrapper.subtractBalance(subtractRequest);
        //출금 이벤트 결과 확인
        serviceValidator.validateSubtractBalance(subtractBalanceResult);

        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(requestDto.getOtherBankbookNum())
                .money(requestDto.getMoney())
                .build();
        //입금 이벤트 발행
        boolean addBalanceResult = bankbookClientWrapper.addBalance(addRequest);

        //만약 입금을 실패했다면
        if (!addBalanceResult) {
            //롤백
            remitRollback(subtractRequest);
        }

        //출금 거래내역 이벤트 발행
        RecordRequest withdrawRequest = RecordRequest.builder()
                .title(requestDto.getOtherBankbookNum() + "출금")
                .bankBookNum(requestDto.getBankbookNum())
                .money(requestDto.getMoney())
                .build();
        recordProducer.withdrawRecord(withdrawRequest);
        
        //입금 거래내역 이벤트 발행
        RecordRequest depositRequest = RecordRequest.builder()
                .title(requestDto.getBankbookNum() + "입금")
                .bankBookNum(requestDto.getOtherBankbookNum())
                .money(requestDto.getMoney())
                .build();
        recordProducer.depositRecord(depositRequest);
}

//롤백 함수
private void remitRollback(SubtractBalanceRequest subtractRequest) {
        //출금 요청 dto를 받아서 입금 요청 dto로 변환
        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(subtractRequest.getBankbookNum())
                .money(subtractRequest.getMoney())
                .build();
        //입금 이벤트 발행
        bankbookClientWrapper.addBalance(addRequest);

        //입금 실패했기 때문에 controller advice로 송금 실패 exception 전달
        //-> 최종적으로 사용자에게 송금 실패 결과창 전달됨
        throw new RemitCustomException(ResponseMessage.REMIT_FAIL);
}
```

## 가능한 타행계좌
* 타행계좌는 여러종류가 있으나, 프로젝트의 주된 목적에 집중하기 위해서 가능한 타행계좌는 간단하게 하였습니다.
* OtherBank 라는 enum에 넣어서 클라이언트의 입력에 따라 처리하도록 하엿습니다.
* 타행계좌의 pg에서 지원 하는 open api를 가져오진 않았고, 그저 정책상으로만 타행계좌에 송금하는 것을 가능하게 하였습니다.
* 실제 구현은 하지 않았습니다.
```
국민
농협
신한
```

open feign 
OtherBank enum 만들어처리
타행계좌 송금시 1%수수료
계좌서비스 요청 -> 거래내역 요청 -> 모든 bool 판별 후 return
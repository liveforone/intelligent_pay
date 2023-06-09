# 결제 서비스
* 타 서비스(해당 프로젝트 이외)에서 결제를 처리할 수 있도록 하는 페이 서비스

## 요구사항
* 결제/결제 취소 시에는 계좌번호를 입력받습니다.
* 계좌번호가 식별하는 key입니다.
* 결제/결제 취소 시에는 표준화 json body 규격을 지켜서 입력받습니다.
* DB는 존재하지 않고, 오로지 결제관련 이벤트만 처리합니다.
* 거래내역 서비스, 계좌 서비스와 통신합니다.
* 결제 취소시에는 비밀번호를 입력받지 않습니다.

## API 설계
* 모든 api는 외부에 제공함
```
[POST] /pay : 결제
[POST] /cancel/pay : 결제 취소
```

## Json body 예시
```
[결제]
{
    "buyerBankbookNum" : "1234567891234",
    "money" : 40000,
    "password" : "12345678",
    "sellerBankbookNum" : "9876543219876",
    "payTitle" : "맑은 물"
}

[결제취소]
{
    "buyerBankbookNum" : "1234567891234",
    "money" : 40000,
    "sellerBankbookNum" : "9876543219876",
    "payTitle" : "맑은 물"
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
### 결제 취소시 출금 요청
* 계좌서비스에 요청
* bool 리턴받음
```
[POST] /provide/subtract/balance/cancel
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
  "title": "맑은 물 결제",
  "bankBookNum": "1234567894321",
  "money": 40000
}
```

## 결제/결제 취소시 롤백
* 결제시에는 결제 요청 계좌 출금 -> 상품 주인 계좌 입금 순으로 진행한다.
* 결제 요청 계좌 출금이 실패하면 그대로 종료하면되지만, 상줌 주인 계좌의 입금이 실패하면 이전에 발행했던 이벤트를 롤백해야한다.
* 따라서 이 경우 입금 실패가 발생하면, 결제 요청 계좌에 출금한 금액만큼 다시 입금 이벤트를 발행하여
* 잔액을 롤백하여주었다.
* 아래는 코드이다.
```
//결제 함수(결제 취소도 동일함)
public void pay(PayRequest requestDto) {
        SubtractBalanceRequest subtractRequest = SubtractBalanceRequest.builder()
                .bankbookNum(requestDto.getBuyerBankbookNum())
                .money(requestDto.getMoney())
                .password(requestDto.getPassword())
                .build();
        //결제 요청 계좌 출금
        boolean subtractBalanceResult = bankbookClientWrapper.subtractBalance(subtractRequest);
        //출금 처리 확인
        serviceValidator.validatePay(subtractBalanceResult);

        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(requestDto.getSellerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        //상품 주인 계좌 입금
        boolean addBalanceResult = bankbookClientWrapper.addBalance(addRequest);

        //입금 실패시
        if (!addBalanceResult) {
            //결제 요청계좌 출금 롤백
            payRollback(subtractRequest);
        }

        RecordRequest withdrawRequest = RecordRequest.builder()
                .title(requestDto.getPayTitle() + "결제")
                .bankBookNum(requestDto.getBuyerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        //출금 계좌 거래내역 생성
        recordProducer.withdrawRecord(withdrawRequest);

        RecordRequest depositRequest = RecordRequest.builder()
                .title(requestDto.getPayTitle() + "결제" + requestDto.getBuyerBankbookNum() + "입금")
                .bankBookNum(requestDto.getSellerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        //입금 계좌 거래내역 생성
        recordProducer.depositRecord(depositRequest);
}

//롤백 함수
private void payRollback(SubtractBalanceRequest subtractRequest) {
        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(subtractRequest.getBankbookNum())
                .money(subtractRequest.getMoney())
                .build();
        //출금 dto를 입금 dto로 변환하여 입금 처리
        bankbookClientWrapper.addBalance(addRequest);

        //입금 실패했기 때문에 controller advice로 결제 실패 exception 전달
        //-> 최종적으로 사용자에게 결제 실패 결과창 전달됨
        throw new PayCustomException(ResponseMessage.PAY_FAIL);
}
```

## 결제시 dto
* 스프링은 dto를 변수명을 기준으로 매핑한다.
* 따라서 타 서비스로 dto를 전달때 표준화 규격을 반드시 지켜서 전달한다.(변수명을 맞추어서)

## 거래내역 취소 매커니즘
* 거래내역 취소시 거래내역은 취소되지 않고,
* 입금과 출금이 서로 바뀌어 새로운 거래내역이 생성된다.
### 거래자
* 거래자는 두 종류가 있다.
* 상품을 제공하는 공급자와
* 상품을 구매하는 소비자가 있다.
### 매커니즘
* 타 서비스로부터 입금된 계좌와 출금된 계좌를 입력받는다.
* 결제서비스는 계좌서비스로 입출금을 서로 바꾸어 api call을 한다.
* 거래내역 서비스로는 계좌서비스 api call과 마찬가지로 입금과 출금을 서로바꾸어 거래내역으로 call한다.
### 간단 정리
* 입금된 계좌, 출금된 계좌 입력받음
* 결제서비스가 계좌서비스로 입금 출금 서로 exchange해서 api call
* 거래내역 서비스로는 입금 출금 서로 exchange해서 api call한다.
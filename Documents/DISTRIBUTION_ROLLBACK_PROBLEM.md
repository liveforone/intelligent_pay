# 송금과 결제시 롤백

## 분산 트랜잭션 환경 롤백
* 분산 트랜잭션 환경에서 롤백을 처리하는 데에는 다양한 방법이 있다.
* 모놀리틱 방식에 비해 롤백처리가 까다롭다.
* 분산 환경에서 롤백을 처리하는데에는 다양한 패턴과 방법이 존재하나,
* 필자는 해당 프로젝트의 성격(비즈니스 도메인)에 맞추어서 해결방법을 찾았고, 그 방법에 대해 기술하는 점을 유의하라.

## 송금과 결제의 공통점
* 송금과 결제는 공통점이 있다.
* 그것은 입금과 출금이 동시에 발생한다는 것이다.
* 입금은 입금 처리만 한다.
* 그러나 송금은 송금 요청 계좌에 출금을 처리하고
* 송금받는 계좌에 입금을 처리한다.
* 결제는 결제 요청 계좌에 출금을 처리하고
* 결제한 상품의 주인에게 입금을 처리한다.

## 문제점
* 하나의 요청만 발생하는 경우 롤백이 상당히 간단하다.
* 그러나 타서비스와 통신할때 하나의 이벤트에서 두가지 요청이 필요한경우에는 롤백이 복잡해진다.

## 해결책
### 송금 실패시 롤백
* 송금시에는 송금 요청 계좌 출금 -> 송금 받는 계좌 입금 순으로 진행한다.
* 송금 요청 계좌 출금이 실패하면 그대로 종료하면되지만, 송금 받는 계좌의 입금이 실패하면 이전에 발행했던 이벤트를 롤백해야한다.
* 따라서 이 경우 입금 실패가 발생하면, 송금 요청 계좌에 출금한 금액만큼 다시 입금 이벤트를 발행하여
* 잔액을 롤백하여주었다.
* 코드는 송금 밖에 제시하지 않았지만, 결제/결제 취소 또한 동일한 방식으로 해결하였다.
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
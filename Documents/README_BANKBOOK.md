# 계좌 서비스

## 통장 서비스 소개
* bankbook 엔티티가 통장서비스에 속해있습니다.
* 입/송금/결제 등을 할때에는 통장서비스에 요청이 옵니다.
* 위의 이벤트를 처리하는 서비스와는 분리되어 있지만 이벤트가 발생 할 경우 통장서비스의 리턴결과가 타 서비스에 영향을 미칩니다.

## 상세 요구사항
* rest-api는 각 이벤트에 맞는 rest-api를 쓰지 아니하고,
* 유지보수와 api들의 간결성을 극대화 하기위해 표준화된 규격의 body를 이용해 이벤트들을 하나의 rest-api로 통일합니다.
* 즉 입금시 필요한 api와 송금(내 계좌, 타인계좌) + 결제에 필요한 api 총 2개만 존재합니다.
* 계좌는 더이상 사용하지 않을경우 정지시키는것이 가능합니다.
* 정지된 통장의 경우 어떠한 행위도 불가능합니다.
* 계좌번호는 13자리의 숫자로 구성됩니다.

## 표준화 규격
* rest-api는 돈을 넣는 행위와 돈을 빼는 행위 총 두가지의 api 형태만 제공합니다.
* 따라서 입금과 송금 + 결제로 이벤트 분리가 가능합니다.
### plus
* 입금
```
{
    "bankbookNum",
    "money"
}
```
### minus
* 송금(나의 타행 계좌, 타인 계좌) + 결제
```
{
    "bankbookNum",
    "money",
    "password",
    "otherBankbookNum"
}
```

## API 설계
```
[GET] /balance/{username} : 잔액 리턴
[POST] /create : 통장 개설
[POST] /add/balance : 잔액 plus 행위
[POST] /subtract/balance : 잔액 minus 행위
[PUT] /update/password
[PUT] /suspend : 통장 정지
[PUT] /cancel/suspend : 통장 정지 해제
```
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
* 입금하는 api는 타 은행에서 사용하는 api입니다.
* pay의 목적에 맞게 다른 마이크로서비스와 통신하는 api가 잔액을 마이너스 하는 api입니다.
* 따라서 입금과 송금 + 결제를 하는 api는 목적이 완전히 다릅니다.

## API 설계
```
[GET] /basic/info/{username} : 잔액 리턴
[GET] /info : 통장 정보 리턴
[POST] /create : 통장 개설
[POST] /add/balance : 잔액 plus 행위, bool 리턴
[POST] /subtract/balance : 잔액 minus 행위, bool 리턴
[PUT] /update/password
[PUT] /suspend : 통장 정지
[PUT] /cancel/suspend : 통장 정지 해제
```

## 표준화 body 규격
* rest-api는 돈을 넣는 행위와 돈을 빼는 행위 총 두가지의 api 형태만 제공합니다.
* 따라서 입금과 송금 + 결제로 이벤트 분리가 가능합니다.
### plus
* 입금
* 타 은행에서 사용하고, 타 은행에 제공하는 api로 
* intelligent pay의 다른 마이크로 서비스에게 제공하는 api는 아닙니다.
```
{
    "bankbookNum",
    "money"
}
```
### minus
* 송금(나의 타행 계좌, 타인 계좌) + 결제
* intelligent pay의 다른 마이크로서비스에게 제공하는 api입니다.
```
{
    "bankbookNum",
    "money",
    "password",
    "otherBankbookNum"
}
```

## validator 매개변수 다를때 통일
* 메소드 오버로딩은 매개변수의 타입이나, 갯수가 다를때 가능하다.
* 그러나 null을 체크하는 validator가 두가지 파라미터를 입력받을 수 있는데,
* 하나는 username이고 하나는 bankbookNum이다.
* 필자는 둘중 어느것을 받던, 둘에게 적합한 find query method를 호출하고
* null을 체크하고 싶었다.
* username은 36자리의 uuid와 4자리의 랜덤 문자열로 이루어져있다.
* 따라서 username은 총 40자리이다.
* bankbookNum은 총 13자리의 문자열로 되어있다.
* 이것을 이용하면 identifier이 계좌번호인지 username인지 알 수 있다.
* 코드로 표현하면 아래와 같다.
```
public void validateBankbookNull(String identifier) {
    final int SIZE_OF_BANKBOOK_NUM = 13;

    Bankbook foundBankbook;
    if (identifier.length() == SIZE_OF_BANKBOOK_NUM) {
        foundBankbook = bankbookRepository.findOneByBankbookNum(identifier);
    } else {
        foundBankbook = bankbookRepository.findOneByUsername(identifier);
    }

    if (CommonUtils.isNull(foundBankbook)) {
        throw new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL);
    }
}
```
* 위처럼 길이가 정해져 있는 경우에는 길이를 이용해 파라미터로 넘어온 식별자가 무엇인지 판단하고
* 이에 맞는 함수를 호출하면된다.
* 그렇다면 타입이 같은데, 길이도 같으면 어떻게 해야하는가?
* ByXx를 이용해서 입력받는 파라미터를 구분하여 함수를 하나 더 만들면된다.
* 그러나 결국 하는일이 같을때 하나의 함수로 처리할 수 있다면 매개변수를 판단하는 로직을 넣어서 하나의 함수로 같은 타입의 다양한 파라미터를 받아보도록 할 수 있다.
package intelligent_pay.bankbookservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    CREATE_BANKBOOK_SUCCESS(201, "계좌를 성공적으로 생성하였습니다.");

    private final int status;
    private final String value;
}

package intelligent_pay.bankbookservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    BANKBOOK_IS_NULL(404, "계좌가 존재하지 않습니다."),
    DUPLICATE_BANKBOOK(400, "이미 계좌가 존재합니다."),
    CREATE_BANKBOOK_SUCCESS(201, "계좌를 성공적으로 생성하였습니다.");

    private final int status;
    private final String value;
}

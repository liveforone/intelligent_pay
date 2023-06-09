package intelligent_pay.bankbookservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    //==success==//
    CREATE_BANKBOOK_SUCCESS(201, "계좌를 성공적으로 생성하였습니다."),
    UPDATE_PASSWORD_SUCCESS(200, "비밀번호 변경에 성공하였습니다."),
    SUSPEND_SUCCESS(200, "계좌 정지에 성공하엿습니다."),
    CANCEL_SUSPEND_SUCCESS(200, "계좌 정지 해제에 성공하엿습니다."),

    //==fail==//
    DUPLICATE_ENTITY_VALUE(400, "중복되는 데이터가 존재합니다."),
    BANKBOOK_IS_NULL(404, "계좌가 존재하지 않습니다."),
    SUSPEND_BANKBOOK(400, "정지된 계좌입니다."),
    PASSWORD_NOT_MATCH(400, "비밀번호가 일치하지 않습니다."),
    USERNAME_NOT_MATCH(401, "계좌의 주인이 아닙니다.");

    private final int status;
    private final String value;
}

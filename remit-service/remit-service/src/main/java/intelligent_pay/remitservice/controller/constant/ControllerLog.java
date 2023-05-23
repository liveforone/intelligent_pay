package intelligent_pay.remitservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    DEPOSIT_SUCCESS("입금 성공"),
    WITHDRAW_SUCCESS("출금 성공");

    private final String value;
}

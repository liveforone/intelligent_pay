package intelligent_pay.payservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    PAY_SUCCESS("결제 성공"),
    CANCEL_PAY_SUCCESS("결제 취소 성공");

    private final String value;
}

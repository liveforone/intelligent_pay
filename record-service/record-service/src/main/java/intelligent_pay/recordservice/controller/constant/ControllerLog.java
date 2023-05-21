package intelligent_pay.recordservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    CREATE_DEPOSIT_RECORD("입금 거래내역을 생성하였습니다. | ID : "),
    CREATE_WITHDRAW_RECORD("출금 거래내역을 생성하였습니다. | ID : "),
    CANCEL_RECORD_SUCCESS("거래내역을 취소하였습니다.");

    private final String value;
}

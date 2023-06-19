package intelligent_pay.payservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    //==success==//
    PAY_SUCCESS(201, "결제를 완료하였습니다."),
    CANCEL_PAY_SUCCESS(201, "결제취소를 완료하였습니다."),

    //==fail==//
    PAY_FAIL(400, "결제에 실패하였습니다."),
    PAY_RECORD_FAIL(400, "결제 거래내역 생성에 실패하였습니다."),
    CANCEL_PAY_FAIL(400, "결제 취소에 실패하였습니다."),
    CANCEL_PAY_RECORD_FAIL(400, "결제 취소 거래내역 생성에 실패하였습니다.");

    private final int status;
    private final String value;
}

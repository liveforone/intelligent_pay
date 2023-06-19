package intelligent_pay.remitservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    //==success==//
    DEPOSIT_SUCCESS(201, "입금을 완료하였습니다."),
    REMIT_SUCCESS(201, "송금을 완료하였습니다."),

    //==fail==//
    DEPOSIT_FAIL(400, "입금에 실패하였습니다."),
    DEPOSIT_RECORD_FAIL(400, "입금 거래내역 생성에 실패하였습니다."),
    REMIT_FAIL(400, "송금에 실패하였습니다."),
    REMIT_RECORD_FAIL(400, "송금 거래내역 생성에 실패하였습니다.");

    private final int status;
    private final String value;
}

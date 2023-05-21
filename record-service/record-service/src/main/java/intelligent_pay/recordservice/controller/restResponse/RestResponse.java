package intelligent_pay.recordservice.controller.restResponse;

import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> createDepositRecordSuccess() {
        return ResponseEntity
                .status(ResponseMessage.CREATE_DEPOSIT_SUCCESS.getStatus())
                .body(ResponseMessage.CREATE_DEPOSIT_SUCCESS.getValue());
    }

    public static ResponseEntity<?> createWithdrawRecordSuccess() {
        return ResponseEntity
                .status(ResponseMessage.CREATE_WITHDRAW_SUCCESS.getStatus())
                .body(ResponseMessage.CREATE_WITHDRAW_SUCCESS.getValue());
    }
}

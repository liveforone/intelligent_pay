package intelligent_pay.bankbookservice.controller.restResponse;

import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> createBankbookSuccess() {
        return ResponseEntity
                .status(ResponseMessage.CREATE_BANKBOOK_SUCCESS.getStatus())
                .body(ResponseMessage.CREATE_BANKBOOK_SUCCESS.getValue());
    }

    public static ResponseEntity<?> updatePasswordSuccess() {
        return ResponseEntity.ok(ResponseMessage.UPDATE_PASSWORD_SUCCESS.getValue());
    }

    public static ResponseEntity<?> suspendSuccess() {
        return ResponseEntity.ok(ResponseMessage.SUSPEND_SUCCESS.getValue());
    }
}

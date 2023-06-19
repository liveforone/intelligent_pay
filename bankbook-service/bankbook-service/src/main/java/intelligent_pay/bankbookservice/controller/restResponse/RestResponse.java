package intelligent_pay.bankbookservice.controller.restResponse;

import org.springframework.http.ResponseEntity;

public class RestResponse {

    //==fail==//
    public static ResponseEntity<?> duplicateEntityValue() {
        return ResponseEntity
                .status(ResponseMessage.DUPLICATE_ENTITY_VALUE.getStatus())
                .body(ResponseMessage.DUPLICATE_ENTITY_VALUE.getValue());
    }

    //==success==//
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

    public static ResponseEntity<?> cancelSuspendSuccess() {
        return ResponseEntity.ok(ResponseMessage.CANCEL_SUSPEND_SUCCESS.getValue());
    }
}

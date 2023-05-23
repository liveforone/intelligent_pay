package intelligent_pay.remitservice.controller.restResponse;

import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> depositSuccess() {
        return ResponseEntity
                .status(ResponseMessage.DEPOSIT_SUCCESS.getStatus())
                .body(ResponseMessage.DEPOSIT_SUCCESS.getValue());
    }

    public static ResponseEntity<?> remitSuccess() {
        return ResponseEntity
                .status(ResponseMessage.REMIT_SUCCESS.getStatus())
                .body(ResponseMessage.REMIT_SUCCESS.getValue());
    }
}

package intelligent_pay.remitservice.controller.restResponse;

import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> depositSuccess() {
        return ResponseEntity
                .status(ResponseMessage.DEPOSIT_SUCCESS.getStatus())
                .body(ResponseMessage.DEPOSIT_SUCCESS.getValue());
    }
}

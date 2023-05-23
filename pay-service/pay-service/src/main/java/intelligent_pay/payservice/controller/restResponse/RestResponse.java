package intelligent_pay.payservice.controller.restResponse;

import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> paySuccess() {
        return ResponseEntity
                .status(ResponseMessage.PAY_SUCCESS.getStatus())
                .body(ResponseMessage.PAY_SUCCESS.getValue());
    }

    public static ResponseEntity<?> cancelPaySuccess() {
        return ResponseEntity
                .status(ResponseMessage.CANCEL_PAY_SUCCESS.getStatus())
                .body(ResponseMessage.CANCEL_PAY_SUCCESS.getValue());
    }
}

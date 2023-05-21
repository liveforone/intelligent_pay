package intelligent_pay.recordservice.controller.restResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> createDepositRecordSuccess(Long depositId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(depositId);
    }
}

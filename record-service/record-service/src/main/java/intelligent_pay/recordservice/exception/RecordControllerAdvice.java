package intelligent_pay.recordservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RecordControllerAdvice {

    @ExceptionHandler(RecordCustomException.class)
    protected ResponseEntity<?> shopCustomHandle(RecordCustomException customException) {
        return ResponseEntity
                .status(customException.getResponseMessage().getStatus())
                .body(customException.getResponseMessage().getValue());
    }

    @ExceptionHandler(BindingCustomException.class)
    protected ResponseEntity<?> bindingErrorHandle(BindingCustomException customException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(customException.getMessage());
    }
}

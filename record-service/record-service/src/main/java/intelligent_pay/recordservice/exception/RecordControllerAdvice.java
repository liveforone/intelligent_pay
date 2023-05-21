package intelligent_pay.recordservice.exception;

import intelligent_pay.recordservice.exception.returnBool.BindingCustomBoolException;
import intelligent_pay.recordservice.exception.returnBool.RecordCustomBoolException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RecordControllerAdvice {

    @ExceptionHandler(BindingCustomException.class)
    protected ResponseEntity<?> bindingErrorHandle(BindingCustomException customException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(customException.getMessage());
    }

    @ExceptionHandler(BindingCustomBoolException.class)
    protected boolean bindingBoolErrorHandle(BindingCustomBoolException bindingCustomBoolException) {
        return false;
    }

    @ExceptionHandler(RecordCustomBoolException.class)
    protected boolean recordCustomBoolHandle(RecordCustomBoolException recordCustomBoolException) {
        return false;
    }
}

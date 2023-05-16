package intelligent_pay.bankbookservice.exception;

import intelligent_pay.bankbookservice.exception.returnBool.BindingCustomBoolException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BankbookControllerAdvice {

    @ExceptionHandler(BankbookCustomException.class)
    protected ResponseEntity<?> shopCustomHandle(BankbookCustomException customException) {
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

    @ExceptionHandler(BindingCustomBoolException.class)
    protected boolean bindingBoolErrorHandle(BindingCustomBoolException bindingCustomBoolException) {
        return false;
    }
}

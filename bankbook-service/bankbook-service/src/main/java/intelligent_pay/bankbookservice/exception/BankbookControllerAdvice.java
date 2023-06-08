package intelligent_pay.bankbookservice.exception;

import intelligent_pay.bankbookservice.controller.restResponse.RestResponse;
import intelligent_pay.bankbookservice.exception.returnBool.BankbookCustomBoolException;
import intelligent_pay.bankbookservice.exception.returnBool.BindingCustomBoolException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BankbookControllerAdvice {

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<?> duplicateEntityValue() {
        return RestResponse.duplicateEntityValue();
    }

    @ExceptionHandler(BankbookCustomException.class)
    protected ResponseEntity<?> bankbookCustomHandle(BankbookCustomException customException) {
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

    @ExceptionHandler(BankbookCustomBoolException.class)
    protected boolean bankbookCustomBoolHandle(BankbookCustomBoolException bankbookCustomBoolException) {
        return false;
    }
}

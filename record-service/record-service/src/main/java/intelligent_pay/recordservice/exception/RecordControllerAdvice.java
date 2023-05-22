package intelligent_pay.recordservice.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RecordControllerAdvice {

    @ExceptionHandler(BindingCustomExceptionBool.class)
    protected boolean bindingErrorHandleBool(BindingCustomExceptionBool customException) {
        return false;
    }
}

package intelligent_pay.recordservice.validator;

import intelligent_pay.recordservice.exception.BindingCustomException;
import intelligent_pay.recordservice.exception.returnBool.BindingCustomBoolException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Component
public class ControllerValidator {

    public void validateBinding(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects
                    .requireNonNull(bindingResult.getFieldError())
                    .getDefaultMessage();
            throw new BindingCustomException(errorMessage);
        }
    }

    public void validateBindingThrowBool(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BindingCustomBoolException();
        }
    }
}

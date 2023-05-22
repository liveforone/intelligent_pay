package intelligent_pay.recordservice.validator;

import intelligent_pay.recordservice.exception.BindingCustomExceptionBool;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ControllerValidator {

    public void validateBindingThrowBool(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BindingCustomExceptionBool();
        }
    }
}

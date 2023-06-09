package intelligent_pay.userservice.validator;

import intelligent_pay.userservice.controller.constant.ControllerLog;
import intelligent_pay.userservice.controller.restResponse.ResponseMessage;
import intelligent_pay.userservice.domain.Role;
import intelligent_pay.userservice.exception.BindingCustomException;
import intelligent_pay.userservice.exception.MemberCustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class ControllerValidator {

    public void validateBinding(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects
                    .requireNonNull(bindingResult.getFieldError())
                    .getDefaultMessage();
            throw new BindingCustomException(errorMessage);
        }
    }

    public void validateAdmin(String auth) {
        if (!auth.equals(Role.ADMIN.getAuth())) {
            log.error(ControllerLog.ADMIN_FAIL.getValue());
            throw new MemberCustomException(ResponseMessage.PROHIBITION);
        }
    }
}

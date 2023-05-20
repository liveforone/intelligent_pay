package intelligent_pay.bankbookservice.validator;

import intelligent_pay.bankbookservice.controller.restResponse.ResponseMessage;
import intelligent_pay.bankbookservice.exception.BankbookCustomException;
import intelligent_pay.bankbookservice.exception.returnBool.BindingCustomBoolException;
import intelligent_pay.bankbookservice.exception.BindingCustomException;
import intelligent_pay.bankbookservice.repository.BankbookRepository;
import intelligent_pay.bankbookservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ControllerValidator {

    private final BankbookRepository bankbookRepository;

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
            String errorMessage = Objects
                    .requireNonNull(bindingResult.getFieldError())
                    .getDefaultMessage();
            throw new BindingCustomBoolException(errorMessage);
        }
    }

    public void validateUsername(String username, String bankbookNum) {
        String foundUsername = bankbookRepository.findUsernameByBankbookNum(bankbookNum);

        if (CommonUtils.isNull(foundUsername)) {
            throw new BankbookCustomException(ResponseMessage.NOT_EXIST_MEMBER);
        }

        if (!foundUsername.equals(username)) {
            throw new BankbookCustomException(ResponseMessage.USERNAME_NOT_MATCH);
        }
    }
}

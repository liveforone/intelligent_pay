package intelligent_pay.bankbookservice.validator;

import intelligent_pay.bankbookservice.controller.restResponse.ResponseMessage;
import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.exception.BankbookCustomException;
import intelligent_pay.bankbookservice.exception.BindingCustomException;
import intelligent_pay.bankbookservice.repository.BankbookRepository;
import intelligent_pay.bankbookservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BankbookValidator {

    private final BankbookRepository bankbookRepository;

    public void validateBankbookNull(String username) {
        Bankbook foundBankbook = bankbookRepository.findOneByUsername(username);

        if (CommonUtils.isNull(foundBankbook)) {
            throw new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL);
        }
    }

    public void validateBinding(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects
                    .requireNonNull(bindingResult.getFieldError())
                    .getDefaultMessage();
            throw new BindingCustomException(errorMessage);
        }
    }
}

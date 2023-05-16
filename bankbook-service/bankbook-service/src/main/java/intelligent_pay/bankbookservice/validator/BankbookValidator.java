package intelligent_pay.bankbookservice.validator;

import intelligent_pay.bankbookservice.controller.restResponse.ResponseMessage;
import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.exception.BankbookCustomException;
import intelligent_pay.bankbookservice.exception.returnBool.BankbookCustomBoolException;
import intelligent_pay.bankbookservice.exception.returnBool.BindingCustomBoolException;
import intelligent_pay.bankbookservice.exception.BindingCustomException;
import intelligent_pay.bankbookservice.repository.BankbookRepository;
import intelligent_pay.bankbookservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BankbookValidator {

    private final BankbookRepository bankbookRepository;
    private PasswordEncoder passwordEncoder;

    public void validateBankbookNull(String identifier) {
        final int SIZE_OF_BANKBOOK_NUM = 13;

        Bankbook foundBankbook;
        if (identifier.length() == SIZE_OF_BANKBOOK_NUM) {
            foundBankbook = bankbookRepository.findOneByBankbookNum(identifier);
        } else {
            foundBankbook = bankbookRepository.findOneByUsername(identifier);
        }

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

    public void validateBindingThrowBool(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects
                    .requireNonNull(bindingResult.getFieldError())
                    .getDefaultMessage();
            throw new BindingCustomBoolException(errorMessage);
        }
    }

    public void validateDuplicateBankbook(String username) {
        Bankbook foundBankbook = bankbookRepository.findOneByUsername(username);

        if (!CommonUtils.isNull(foundBankbook)) {
            throw new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL);
        }
    }

    public void validatePassword(String password, String bankbookNum) {
        Bankbook foundBankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);

        if (CommonUtils.isNull(foundBankbook)) {
            throw new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL);
        }

        if (!passwordEncoder.matches(password, foundBankbook.getPassword())) {
            throw new BankbookCustomException(ResponseMessage.PASSWORD_NOT_MATCH);
        }
    }

    public void validatePasswordThrowBool(String password, String bankbookNum) {
        Bankbook foundBankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);

        if (CommonUtils.isNull(foundBankbook)) {
            throw new BankbookCustomBoolException();
        }

        if (!passwordEncoder.matches(password, foundBankbook.getPassword())) {
            throw new BankbookCustomBoolException();
        }
    }
}

package intelligent_pay.bankbookservice.validator;

import intelligent_pay.bankbookservice.controller.restResponse.ResponseMessage;
import intelligent_pay.bankbookservice.domain.BankbookState;
import intelligent_pay.bankbookservice.exception.BankbookCustomException;
import intelligent_pay.bankbookservice.exception.returnBool.BankbookCustomBoolException;
import intelligent_pay.bankbookservice.repository.BankbookRepository;
import intelligent_pay.bankbookservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceValidator {

    private final BankbookRepository bankbookRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void validateBankbookNull(String identifier) {
        final int SIZE_OF_BANKBOOK_NUM = 13;

        Long foundId;
        if (identifier.length() == SIZE_OF_BANKBOOK_NUM) {
            foundId = bankbookRepository.findIdByBankbookNum(identifier);
        } else {
            foundId = bankbookRepository.findIdByUsername(identifier);
        }

        if (CommonUtils.isNull(foundId)) {
            throw new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL);
        }
    }

    public void validateBankbookNullThrowBool(String identifier) {
        final int SIZE_OF_BANKBOOK_NUM = 13;

        Long foundId;
        if (identifier.length() == SIZE_OF_BANKBOOK_NUM) {
            foundId = bankbookRepository.findIdByBankbookNum(identifier);
        } else {
            foundId = bankbookRepository.findIdByUsername(identifier);
        }

        if (CommonUtils.isNull(foundId)) {
            throw new BankbookCustomBoolException();
        }
    }

    public void validateDuplicateBankbook(String username) {
        Long foundId = bankbookRepository.findIdByUsername(username);

        if (!CommonUtils.isNull(foundId)) {
            throw new BankbookCustomException(ResponseMessage.DUPLICATE_BANKBOOK);
        }
    }

    public void validateBankbookState(String bankbookNum) {
        BankbookState state = bankbookRepository.findStateByBankbookNum(bankbookNum);

        if (state == BankbookState.SUSPEND) {
            throw new BankbookCustomException(ResponseMessage.SUSPEND_BANKBOOK);
        }
    }

    public void validateBankbookStateThrowBool(String bankbookNum) {
        BankbookState state = bankbookRepository.findStateByBankbookNum(bankbookNum);

        if (state == BankbookState.SUSPEND) {
            throw new BankbookCustomBoolException();
        }
    }

    public void validatePassword(String password, String bankbookNum) {
        String foundPassword = bankbookRepository.findPasswordByBankbookNum(bankbookNum);

        if (CommonUtils.isNull(foundPassword)) {
            throw new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL);
        }

        if (!passwordEncoder.matches(password, foundPassword)) {
            throw new BankbookCustomException(ResponseMessage.PASSWORD_NOT_MATCH);
        }
    }

    public void validatePasswordThrowBool(String password, String bankbookNum) {
        String foundPassword = bankbookRepository.findPasswordByBankbookNum(bankbookNum);

        if (CommonUtils.isNull(foundPassword)) {
            throw new BankbookCustomBoolException();
        }

        if (!passwordEncoder.matches(password, foundPassword)) {
            throw new BankbookCustomBoolException();
        }
    }

    public void validateBalanceWhenSubtract(String bankbookNum) {
        long foundBalance = bankbookRepository.findBalanceByBankbookNum(bankbookNum);

        if (foundBalance == 0) {
            throw new BankbookCustomBoolException();
        }
    }
}

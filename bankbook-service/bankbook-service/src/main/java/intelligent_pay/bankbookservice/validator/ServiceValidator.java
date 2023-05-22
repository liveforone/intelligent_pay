package intelligent_pay.bankbookservice.validator;

import intelligent_pay.bankbookservice.controller.restResponse.ResponseMessage;
import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.domain.BankbookState;
import intelligent_pay.bankbookservice.dto.request.SubtractBalanceRequest;
import intelligent_pay.bankbookservice.dto.update.UpdateBankbookStateRequest;
import intelligent_pay.bankbookservice.dto.update.UpdatePasswordRequest;
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

    public void validateCreateBankbook(String username) {
        Long foundId = bankbookRepository.findIdByUsername(username);

        if (!CommonUtils.isNull(foundId)) {
            throw new BankbookCustomException(ResponseMessage.DUPLICATE_BANKBOOK);
        }
    }

    public void validateAddBalance(String bankbookNum) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);

        if (CommonUtils.isNull(bankbook)
                || (bankbook.getBankbookState() == BankbookState.SUSPEND)) {
            throw new BankbookCustomBoolException();
        }
    }

    public void validateSubtractBalance(SubtractBalanceRequest requestDto) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(requestDto.getBankbookNum());
        long balance = bankbook.getBalance();

        if (CommonUtils.isNull(bankbook)
                || (bankbook.getBankbookState() == BankbookState.SUSPEND)
                || (!passwordEncoder.matches(requestDto.getPassword(), bankbook.getPassword()))
                || (balance == 0)
                || (balance - requestDto.getMoney() < 0)) {
            throw new BankbookCustomBoolException();
        }
    }

    public void validateUpdatePassword(UpdatePasswordRequest requestDto, String username) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(requestDto.getBankbookNum());

        if (CommonUtils.isNull(bankbook)) {
            throw new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL);
        }

        if (!bankbook.getUsername().equals(username)) {
            throw new BankbookCustomException(ResponseMessage.USERNAME_NOT_MATCH);
        }

        if (bankbook.getBankbookState() == BankbookState.SUSPEND) {
            throw new BankbookCustomException(ResponseMessage.SUSPEND_BANKBOOK);
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), bankbook.getPassword())) {
            throw new BankbookCustomException(ResponseMessage.PASSWORD_NOT_MATCH);
        }
    }

    public void validateUpdateBankbookState(UpdateBankbookStateRequest requestDto, String username) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(requestDto.getBankbookNum());

        if (CommonUtils.isNull(bankbook)) {
            throw new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL);
        }

        if (!bankbook.getUsername().equals(username)) {
            throw new BankbookCustomException(ResponseMessage.USERNAME_NOT_MATCH);
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), bankbook.getPassword())) {
            throw new BankbookCustomException(ResponseMessage.PASSWORD_NOT_MATCH);
        }
    }
}

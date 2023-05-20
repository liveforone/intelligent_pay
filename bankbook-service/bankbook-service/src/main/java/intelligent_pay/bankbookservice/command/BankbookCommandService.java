package intelligent_pay.bankbookservice.command;

import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.dto.request.AddBalanceRequest;
import intelligent_pay.bankbookservice.dto.request.BankbookRequest;
import intelligent_pay.bankbookservice.dto.request.SubtractBalanceRequest;
import intelligent_pay.bankbookservice.dto.update.UpdateBankbookStateRequest;
import intelligent_pay.bankbookservice.dto.update.UpdatePasswordRequest;
import intelligent_pay.bankbookservice.repository.BankbookRepository;
import intelligent_pay.bankbookservice.validator.ServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BankbookCommandService {

    private final BankbookRepository bankbookRepository;
    private final ServiceValidator serviceValidator;

    @Transactional
    public void createBankbook(BankbookRequest bankbookRequest, String username) {
        serviceValidator.validateDuplicateBankbook(username);

        Bankbook bankbook = Bankbook.create(bankbookRequest, username);
        bankbookRepository.save(bankbook);
    }

    @Transactional
    public void addBalance(AddBalanceRequest addBalanceRequest) {
        String bankbookNum = addBalanceRequest.getBankbookNum();
        serviceValidator.validateBankbookNullThrowBool(bankbookNum);
        serviceValidator.validateBankbookStateThrowBool(bankbookNum);

        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);
        bankbook.addBalance(addBalanceRequest.getMoney());
    }

    @Transactional
    public void subtractBalance(SubtractBalanceRequest subtractBalanceRequest) {
        String bankbookNum = subtractBalanceRequest.getBankbookNum();
        serviceValidator.validateBankbookStateThrowBool(bankbookNum);
        serviceValidator.validatePasswordThrowBool(subtractBalanceRequest.getPassword(), bankbookNum);
        serviceValidator.validateBalanceWhenSubtract(bankbookNum, subtractBalanceRequest.getMoney());

        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);
        bankbook.subtractBalance(subtractBalanceRequest.getMoney());
    }

    @Transactional
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        String bankbookNum = updatePasswordRequest.getBankbookNum();
        serviceValidator.validateBankbookState(bankbookNum);
        serviceValidator.validatePassword(updatePasswordRequest.getPassword(), bankbookNum);

        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);
        bankbook.updatePassword(updatePasswordRequest.getNewPassword());
    }

    @Transactional
    public void suspendBankbook(UpdateBankbookStateRequest requestDto) {
        String bankbookNum = requestDto.getBankbookNum();
        serviceValidator.validateBankbookNull(bankbookNum);
        serviceValidator.validatePassword(requestDto.getPassword(), bankbookNum);

        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);
        bankbook.suspend();
    }

    @Transactional
    public void cancelSuspendBankbook(UpdateBankbookStateRequest requestDto) {
        String bankbookNum = requestDto.getBankbookNum();
        serviceValidator.validateBankbookNull(bankbookNum);
        serviceValidator.validatePassword(requestDto.getPassword(), bankbookNum);

        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);
        bankbook.cancelSuspend();
    }
}

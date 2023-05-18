package intelligent_pay.bankbookservice.command;

import intelligent_pay.bankbookservice.async.AsyncConstant;
import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.dto.request.AddBalanceRequest;
import intelligent_pay.bankbookservice.dto.request.BankbookRequest;
import intelligent_pay.bankbookservice.dto.request.SubtractBalanceRequest;
import intelligent_pay.bankbookservice.dto.update.UpdatePasswordRequest;
import intelligent_pay.bankbookservice.repository.BankbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BankbookCommandService {

    private final BankbookRepository bankbookRepository;

    @Transactional
    public void createBankbook(BankbookRequest bankbookRequest, String username) {
        Bankbook bankbook = Bankbook.create(bankbookRequest, username);
        bankbookRepository.save(bankbook);
    }

    @Transactional
    public void addBalance(AddBalanceRequest addBalanceRequest) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(addBalanceRequest.getBankbookNum());
        bankbook.addBalance(addBalanceRequest.getMoney());
    }

    @Transactional
    public void subtractBalance(SubtractBalanceRequest subtractBalanceRequest) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(subtractBalanceRequest.getBankbookNum());
        bankbook.subtractBalance(subtractBalanceRequest.getMoney());
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(updatePasswordRequest.getBankbookNum());
        bankbook.updatePassword(updatePasswordRequest.getNewPassword());
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void suspendBankbook(String bankbookNum) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);
        bankbook.suspend();
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void cancelSuspendBankbook(String bankbookNum) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);
        bankbook.cancelSuspend();
    }
}
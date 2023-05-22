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
    public void createBankbook(BankbookRequest requestDto, String username) {
        serviceValidator.validateCreateBankbook(username);

        Bankbook bankbook = Bankbook.create(requestDto, username);
        bankbookRepository.save(bankbook);
    }

    @Transactional
    public void addBalance(AddBalanceRequest requestDto) {
        String bankbookNum = requestDto.getBankbookNum();
        serviceValidator.validateAddBalance(bankbookNum);

        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);
        bankbook.addBalance(requestDto.getMoney());
    }

    @Transactional
    public void subtractBalance(SubtractBalanceRequest requestDto) {
        String bankbookNum = requestDto.getBankbookNum();
        serviceValidator.validateSubtractBalance(requestDto);

        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);
        bankbook.subtractBalance(requestDto.getMoney());
    }

    @Transactional
    public void updatePassword(UpdatePasswordRequest requestDto, String username) {
        String bankbookNum = requestDto.getBankbookNum();
        serviceValidator.validateUpdatePassword(requestDto, username);

        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);
        bankbook.updatePassword(requestDto.getNewPassword());
    }

    @Transactional
    public void suspendBankbook(UpdateBankbookStateRequest requestDto, String username) {
        String bankbookNum = requestDto.getBankbookNum();
        serviceValidator.validateUpdateBankbookState(requestDto, username);

        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);
        bankbook.suspend();
    }

    @Transactional
    public void cancelSuspendBankbook(UpdateBankbookStateRequest requestDto, String username) {
        String bankbookNum = requestDto.getBankbookNum();
        serviceValidator.validateUpdateBankbookState(requestDto, username);

        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(bankbookNum);
        bankbook.cancelSuspend();
    }
}

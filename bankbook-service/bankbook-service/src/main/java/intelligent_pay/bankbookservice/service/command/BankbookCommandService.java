package intelligent_pay.bankbookservice.service.command;

import intelligent_pay.bankbookservice.controller.restResponse.ResponseMessage;
import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.dto.request.AddBalanceRequest;
import intelligent_pay.bankbookservice.dto.request.BankbookRequest;
import intelligent_pay.bankbookservice.dto.request.SubtractBalanceForCancel;
import intelligent_pay.bankbookservice.dto.request.SubtractBalanceRequest;
import intelligent_pay.bankbookservice.dto.update.UpdateBankbookStateRequest;
import intelligent_pay.bankbookservice.dto.update.UpdatePasswordRequest;
import intelligent_pay.bankbookservice.exception.BankbookCustomException;
import intelligent_pay.bankbookservice.exception.returnBool.BankbookCustomBoolException;
import intelligent_pay.bankbookservice.repository.BankbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BankbookCommandService {

    private final BankbookRepository bankbookRepository;

    public void createBankbook(BankbookRequest requestDto, String username) {
        Bankbook bankbook = Bankbook.create(requestDto.getPassword(), username);
        bankbookRepository.save(bankbook);
    }

    public void addBalance(AddBalanceRequest requestDto) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(requestDto.getBankbookNum())
                .orElseThrow(BankbookCustomBoolException::new);
        bankbook.addBalance(requestDto.getMoney());
    }

    public void subtractBalance(SubtractBalanceRequest requestDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(requestDto.getBankbookNum())
                .orElseThrow(BankbookCustomBoolException::new);

        if (!passwordEncoder.matches(requestDto.getPassword(), bankbook.getPassword())) {
            throw new BankbookCustomBoolException();
        }

        bankbook.subtractBalance(requestDto.getMoney());
    }

    public void subtractBalanceForCancel(SubtractBalanceForCancel requestDto) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(requestDto.getBankbookNum())
                .orElseThrow(BankbookCustomBoolException::new);
        bankbook.subtractBalance(requestDto.getMoney());
    }

    public void updatePassword(UpdatePasswordRequest requestDto, String username) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(requestDto.getBankbookNum())
                .orElseThrow(() -> new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL));
        bankbook.updatePassword(requestDto.getNewPassword(), requestDto.getPassword(),  username);
    }

    public void suspendBankbook(UpdateBankbookStateRequest requestDto, String username) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(requestDto.getBankbookNum())
                .orElseThrow(() -> new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL));
        bankbook.suspend(requestDto.getPassword(), username);
    }

    public void cancelSuspendBankbook(UpdateBankbookStateRequest requestDto, String username) {
        Bankbook bankbook = bankbookRepository.findOneByBankbookNum(requestDto.getBankbookNum())
                .orElseThrow(() -> new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL));
        bankbook.cancelSuspend(requestDto.getPassword(), username);
    }

    public void removeBankbook(String username) {
        Bankbook bankbook = bankbookRepository.findOneByUsername(username)
                .orElseThrow(() -> new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL));
        bankbookRepository.delete(bankbook);
    }
}

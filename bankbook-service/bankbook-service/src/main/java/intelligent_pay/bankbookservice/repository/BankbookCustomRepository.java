package intelligent_pay.bankbookservice.repository;

import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.dto.response.BasicInfoResponse;

import java.util.Optional;

public interface BankbookCustomRepository {
    BasicInfoResponse findBasicInfoByUsername(String username);
    String checkBankbookByBankbookNum(String bankbookNum);
    Optional<Bankbook> findOneByUsername(String username);
    Optional<Bankbook> findOneByBankbookNum(String bankbookNum);
}

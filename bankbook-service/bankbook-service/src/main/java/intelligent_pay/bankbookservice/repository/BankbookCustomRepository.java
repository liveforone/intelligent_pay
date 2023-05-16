package intelligent_pay.bankbookservice.repository;

import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.domain.BankbookState;

public interface BankbookCustomRepository {

    Long findIdByUsername(String username);
    Long findIdByBankbookNum(String bankbookNum);
    String findPasswordByBankbookNum(String bankbookNum);
    BankbookState findStateByBankbookNum(String bankbookNum);
    Bankbook findOneByUsername(String username);
    Bankbook findOneByBankbookNum(String bankbookNum);
}

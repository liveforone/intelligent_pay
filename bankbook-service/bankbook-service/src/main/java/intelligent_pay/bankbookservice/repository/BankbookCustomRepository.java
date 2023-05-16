package intelligent_pay.bankbookservice.repository;

import intelligent_pay.bankbookservice.domain.Bankbook;

public interface BankbookCustomRepository {

    Long findIdByUsername(String username);
    Long findIdByBankbookNum(String bankbookNum);
    Bankbook findOneByUsername(String username);
    Bankbook findOneByBankbookNum(String bankbookNum);
}

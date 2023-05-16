package intelligent_pay.bankbookservice.repository;

import intelligent_pay.bankbookservice.domain.Bankbook;

public interface BankbookCustomRepository {
    Bankbook findOneByUsername(String username);
}

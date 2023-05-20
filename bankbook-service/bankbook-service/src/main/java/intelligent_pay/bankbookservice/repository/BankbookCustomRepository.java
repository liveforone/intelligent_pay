package intelligent_pay.bankbookservice.repository;

import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.domain.BankbookState;
import intelligent_pay.bankbookservice.dto.response.BasicInfoResponse;

public interface BankbookCustomRepository {

    Long findIdByUsername(String username);
    Long findIdByBankbookNum(String bankbookNum);
    String findPasswordByBankbookNum(String bankbookNum);
    BankbookState findStateByBankbookNum(String bankbookNum);
    long findBalanceByBankbookNum(String bankbookNum);
    BasicInfoResponse findBasicInfoByUsername(String username);
    Bankbook findOneByUsername(String username);
    Bankbook findOneByBankbookNum(String bankbookNum);
}

package intelligent_pay.bankbookservice.repository;

import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.dto.response.BasicInfoResponse;

public interface BankbookCustomRepository {

    Long findIdByUsername(String username);
    BasicInfoResponse findBasicInfoByUsername(String username);
    Bankbook findOneByUsername(String username);
    Bankbook findOneByBankbookNum(String bankbookNum);
}

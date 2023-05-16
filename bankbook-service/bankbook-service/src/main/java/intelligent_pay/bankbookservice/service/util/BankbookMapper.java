package intelligent_pay.bankbookservice.service.util;

import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.dto.BankbookResponse;

public class BankbookMapper {

    public BankbookResponse entityToDto(Bankbook bankbook) {
        return BankbookResponse.builder()
                .id(bankbook.getId())
                .balance(bankbook.getBalance())
                .bankbookNum(bankbook.getBankbookNum())
                .bankbookState(bankbook.getBankbookState())
                .createdDate(bankbook.getCreatedDate())
                .build();
    }
}

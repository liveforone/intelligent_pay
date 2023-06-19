package intelligent_pay.bankbookservice.dto.util;

import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.dto.response.BankbookResponse;

public class BankbookMapper {

    public static BankbookResponse entityToDto(Bankbook bankbook) {
        return BankbookResponse.builder()
                .id(bankbook.getId())
                .balance(bankbook.getBalance())
                .bankbookNum(bankbook.getBankbookNum())
                .bankbookState(bankbook.getBankbookState())
                .createdDate(bankbook.getCreatedDate())
                .build();
    }
}

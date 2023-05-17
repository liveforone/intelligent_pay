package intelligent_pay.bankbookservice.service.util;

import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.dto.response.BankbookResponse;
import intelligent_pay.bankbookservice.utility.CommonUtils;

public class BankbookMapper {

    public static BankbookResponse entityToDto(Bankbook bankbook) {
        if (CommonUtils.isNull(bankbook)) {
            return new BankbookResponse();
        }
        return BankbookResponse.builder()
                .id(bankbook.getId())
                .balance(bankbook.getBalance())
                .bankbookNum(bankbook.getBankbookNum())
                .bankbookState(bankbook.getBankbookState())
                .createdDate(bankbook.getCreatedDate())
                .build();
    }
}

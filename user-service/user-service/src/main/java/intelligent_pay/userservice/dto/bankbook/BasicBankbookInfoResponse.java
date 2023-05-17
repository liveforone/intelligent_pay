package intelligent_pay.userservice.dto.bankbook;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasicBankbookInfoResponse {

    private String bankbookNum;
    private long balance;
}

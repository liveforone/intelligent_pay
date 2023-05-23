package intelligent_pay.remitservice.dto.bankbook;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubtractBalanceRequest {

    private String bankbookNum;
    private long money;
    private String password;
}

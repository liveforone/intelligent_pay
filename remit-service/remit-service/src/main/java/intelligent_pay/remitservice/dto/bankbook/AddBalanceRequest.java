package intelligent_pay.remitservice.dto.bankbook;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddBalanceRequest {

    private String bankbookNum;
    private long money;
}

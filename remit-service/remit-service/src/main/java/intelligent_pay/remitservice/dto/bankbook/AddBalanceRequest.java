package intelligent_pay.remitservice.dto.bankbook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddBalanceRequest {

    private String bankbookNum;
    private long money;
}

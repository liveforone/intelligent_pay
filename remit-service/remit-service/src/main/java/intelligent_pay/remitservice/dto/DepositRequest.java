package intelligent_pay.remitservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepositRequest {

    private String bankbookNum;
    private long money;
}

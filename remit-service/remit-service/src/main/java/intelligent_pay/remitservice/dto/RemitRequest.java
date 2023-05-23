package intelligent_pay.remitservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RemitRequest {

    private String bankbookNum;
    private long money;
    private String password;
    private String otherBankbookNum;
}

package intelligent_pay.bankbookservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicInfoResponse {

    private String bankbookNum;
    private long balance;
}

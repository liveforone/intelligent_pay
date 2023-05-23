package intelligent_pay.payservice.dto.bankbook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubtractBalanceForCancel {
    private String bankbookNum;
    private long money;
}

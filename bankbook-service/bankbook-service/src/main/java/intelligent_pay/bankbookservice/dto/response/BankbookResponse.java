package intelligent_pay.bankbookservice.dto.response;

import intelligent_pay.bankbookservice.domain.BankbookState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankbookResponse {

    private Long id;
    private long balance;
    private String bankbookNum;
    private BankbookState bankbookState;
    private LocalDate createdDate;
}

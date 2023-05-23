package intelligent_pay.remitservice.dto.remit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepositRequest {

    @NotBlank(message = "입금 통장번호를 입력해주세요.")
    @Size(min = 13, message = "계좌번호 양식이 올바르지 않습니다.")
    private String bankbookNum;

    @Positive(message = "입금 금액은 양수로 입력해주세요")
    private long money;

    @NotBlank(message = "입금자 통장번호를 입력해주세요.")
    @Size(min = 13, message = "계좌번호 양식이 올바르지 않습니다.")
    private String otherBankbookNum;
}

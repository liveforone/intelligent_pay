package intelligent_pay.bankbookservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddBalanceRequest {

    @NotBlank(message = "계좌번호를 입력해주세요.")
    @Size(min = 13, message = "계좌번호 양식이 올바르지 않습니다.")
    private String bankbookNum;

    @Positive(message = "입금금액은 양수만 가능합니다.")
    private long money;
}

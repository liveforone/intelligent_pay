package intelligent_pay.payservice.dto.pay;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PayCancelRequest {

    @NotBlank(message = "구매자 통장번호를 입력해주세요.")
    @Size(min = 13, message = "계좌번호 양식이 올바르지 않습니다.")
    private String buyerBankbookNum;

    @Positive(message = "결제 금액은 양수로 입력해주세요")
    private long money;

    @NotBlank(message = "판매자 통장번호를 입력해주세요.")
    @Size(min = 13, message = "계좌번호 양식이 올바르지 않습니다.")
    private String sellerBankbookNum;

    @NotBlank(message = "결제 상품명을 입력해주세요.")
    private String payTitle;
}

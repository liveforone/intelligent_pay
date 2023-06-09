package intelligent_pay.bankbookservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankbookRequest {

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 8, message = "비밀번호는 8자리 이상이어야 합니다.")
    private String password;
}

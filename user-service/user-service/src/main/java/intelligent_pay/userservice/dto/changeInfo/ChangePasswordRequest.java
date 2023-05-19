package intelligent_pay.userservice.dto.changeInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "현재 비밀번호를 반드시 입력하세요.")
    private String oldPassword;

    @NotBlank(message = "새 비밀번호를 반드시 입력하세요.")
    private String newPassword;
}

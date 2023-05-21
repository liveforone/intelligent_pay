package intelligent_pay.recordservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CancelStateRequest {

    @NotNull(message = "입금 id를 기입해주세요.")
    private Long depositRecordId;

    @NotNull(message = "출금 id를 기입해주세요.")
    private Long withdrawRecordId;
}

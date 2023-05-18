package intelligent_pay.recordservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecordRequest {

    @NotBlank(message = "거래내역 제목을 기입해주세요.")
    private String title;

    @NotBlank(message = "계좌번호를 기입해주세요.")
    private String bankBookNum;

    @Positive(message = "양수로 입력해주세요.")
    private long money;
}

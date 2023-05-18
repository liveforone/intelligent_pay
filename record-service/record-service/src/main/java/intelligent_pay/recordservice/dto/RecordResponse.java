package intelligent_pay.recordservice.dto;

import intelligent_pay.recordservice.domain.RecordState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordResponse {

    private Long id;
    private String title;
    private long money;
    private RecordState recordState;
    private int createdYear;
    private Month createdMonth;
}

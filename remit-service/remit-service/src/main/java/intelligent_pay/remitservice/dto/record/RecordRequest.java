package intelligent_pay.remitservice.dto.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordRequest {

    private String title;
    private String bankBookNum;
    private long money;
}

package intelligent_pay.remitservice.dto.record;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecordRequest {

    private String title;
    private String bankBookNum;
    private long money;
}

package intelligent_pay.recordservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    RECORD_SAVE_SUCCESS(201, "거래내역을 성공적으로 등록하였습니다.");

    private final int status;
    private final String value;
}

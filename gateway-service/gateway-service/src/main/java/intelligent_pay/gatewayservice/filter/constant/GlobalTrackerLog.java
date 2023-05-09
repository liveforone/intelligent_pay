package intelligent_pay.gatewayservice.filter.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalTrackerLog {

    BASE_LOG("Global filter base message: {}"),
    START_LOG("Global filter start: request id -> {}"),
    END_LOG("Global filter end: response code -> {}");

    private final String value;

}

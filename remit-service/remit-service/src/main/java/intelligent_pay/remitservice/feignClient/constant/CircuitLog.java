package intelligent_pay.remitservice.feignClient.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CircuitLog {

    REMIT_CIRCUIT_LOG("timetable-circuit");

    private final String value;
}

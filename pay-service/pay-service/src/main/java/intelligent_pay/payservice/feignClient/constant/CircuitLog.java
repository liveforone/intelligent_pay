package intelligent_pay.payservice.feignClient.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CircuitLog {

    PAY_CIRCUIT_LOG("pay-circuit");

    private final String value;
}

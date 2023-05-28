package intelligent_pay.payservice.clientWrapper;

import intelligent_pay.payservice.dto.bankbook.AddBalanceRequest;
import intelligent_pay.payservice.dto.bankbook.SubtractBalanceRequest;
import intelligent_pay.payservice.feignClient.BankbookClient;
import intelligent_pay.payservice.feignClient.constant.CircuitLog;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankbookClientWrapper {

    private final BankbookClient bankbookClient;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    public boolean addBalance(AddBalanceRequest requestDto) {
        return circuitBreakerFactory
                .create(CircuitLog.PAY_CIRCUIT_LOG.getValue())
                .run(
                        () -> bankbookClient.addBalance(requestDto),
                        throwable -> false
                );
    }

    public boolean subtractBalance(SubtractBalanceRequest requestDto) {
        return circuitBreakerFactory
                .create(CircuitLog.PAY_CIRCUIT_LOG.getValue())
                .run(
                        () -> bankbookClient.subtractBalance(requestDto),
                        throwable -> false
                );
    }
}

package intelligent_pay.userservice.clientWrapper;

import intelligent_pay.userservice.dto.bankbook.BasicBankbookInfoResponse;
import intelligent_pay.userservice.feignClient.BankbookClient;
import intelligent_pay.userservice.feignClient.constant.CircuitLog;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankbookClientWrapper {

    private final BankbookClient bankbookClient;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    public BasicBankbookInfoResponse getBasicBankbookInfoByUsername(String username) {
        return circuitBreakerFactory
                .create(CircuitLog.USER_CIRCUIT_LOG.getValue())
                .run(
                        () -> bankbookClient.getBasicInfoByUsername(username),
                        throwable -> new BasicBankbookInfoResponse()
                );
    }
}

package intelligent_pay.remitservice.command;

import intelligent_pay.remitservice.dto.bankbook.AddBalanceRequest;
import intelligent_pay.remitservice.dto.record.RecordRequest;
import intelligent_pay.remitservice.dto.remit.DepositRequest;
import intelligent_pay.remitservice.feignClient.BankbookFeignService;
import intelligent_pay.remitservice.feignClient.RecordFeignService;
import intelligent_pay.remitservice.feignClient.constant.CircuitLog;
import intelligent_pay.remitservice.validator.ServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemitCommandService {

    private final BankbookFeignService bankbookFeignService;
    private final RecordFeignService recordFeignService;
    private final ServiceValidator serviceValidator;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    public void deposit(DepositRequest requestDto) {
        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(requestDto.getBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean addBalanceResult = addBalance(addRequest);
        serviceValidator.validateAddBalance(addBalanceResult);

        RecordRequest recordRequest = RecordRequest.builder()
                .title(requestDto.getOtherBankbookNum() + "입금")
                .bankBookNum(requestDto.getBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean depositRecordResult = depositRecord(recordRequest);
        serviceValidator.validateDepositRecord(depositRecordResult);
    }

    private boolean addBalance(AddBalanceRequest requestDto) {
        return circuitBreakerFactory
                .create(CircuitLog.REMIT_CIRCUIT_LOG.getValue())
                .run(
                        () -> bankbookFeignService.addBalance(requestDto),
                        throwable -> false
                );
    }

    private boolean depositRecord(RecordRequest requestDto) {
        return circuitBreakerFactory
                .create(CircuitLog.REMIT_CIRCUIT_LOG.getValue())
                .run(
                        () -> recordFeignService.depositRecord(requestDto),
                        throwable -> false
                );
    }
}

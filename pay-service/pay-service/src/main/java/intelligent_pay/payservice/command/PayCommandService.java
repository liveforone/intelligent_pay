package intelligent_pay.payservice.command;

import intelligent_pay.payservice.dto.bankbook.AddBalanceRequest;
import intelligent_pay.payservice.dto.bankbook.SubtractBalanceForCancel;
import intelligent_pay.payservice.dto.bankbook.SubtractBalanceRequest;
import intelligent_pay.payservice.dto.pay.PayCancelRequest;
import intelligent_pay.payservice.dto.pay.PayRequest;
import intelligent_pay.payservice.dto.record.RecordRequest;
import intelligent_pay.payservice.feignClient.BankbookFeignService;
import intelligent_pay.payservice.feignClient.RecordFeignService;
import intelligent_pay.payservice.feignClient.constant.CircuitLog;
import intelligent_pay.payservice.validator.ServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayCommandService {

    private final BankbookFeignService bankbookFeignService;
    private final RecordFeignService recordFeignService;
    private final ServiceValidator serviceValidator;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    public void pay(PayRequest requestDto) {
        SubtractBalanceRequest subtractRequest = SubtractBalanceRequest.builder()
                .bankbookNum(requestDto.getBuyerBankbookNum())
                .money(requestDto.getMoney())
                .password(requestDto.getPassword())
                .build();
        boolean subtractBalanceResult = subtractBalance(subtractRequest);
        serviceValidator.validatePay(subtractBalanceResult);

        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(requestDto.getSellerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean addBalanceResult = addBalance(addRequest);
        serviceValidator.validatePay(addBalanceResult);

        RecordRequest withdrawRequest = RecordRequest.builder()
                .title(requestDto.getPayTitle() + "결제")
                .bankBookNum(requestDto.getBuyerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean withdrawRecordResult = withdrawRecord(withdrawRequest);
        serviceValidator.validatePayRecord(withdrawRecordResult);

        RecordRequest depositRequest = RecordRequest.builder()
                .title(requestDto.getPayTitle() + "결제" + requestDto.getBuyerBankbookNum() + "입금")
                .bankBookNum(requestDto.getSellerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean depositRecordResult = depositRecord(depositRequest);
        serviceValidator.validatePayRecord(depositRecordResult);
    }

    public void cancelPay(PayCancelRequest requestDto) {
        SubtractBalanceForCancel subtractRequest = SubtractBalanceForCancel.builder()
                .bankbookNum(requestDto.getSellerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean subtractBalanceResult = subtractBalanceForCancel(subtractRequest);
        serviceValidator.validateCancelPay(subtractBalanceResult);

        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(requestDto.getBuyerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean addBalanceResult = addBalance(addRequest);
        serviceValidator.validateCancelPay(addBalanceResult);

        RecordRequest withdrawRequest = RecordRequest.builder()
                .title(requestDto.getPayTitle() + "결제취소" + requestDto.getBuyerBankbookNum() + "송금")
                .bankBookNum(requestDto.getSellerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean withdrawRecordResult = withdrawRecord(withdrawRequest);
        serviceValidator.validateCancelPayRecord(withdrawRecordResult);

        RecordRequest depositRequest = RecordRequest.builder()
                .title(requestDto.getPayTitle() + "결제취소")
                .bankBookNum(requestDto.getBuyerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean depositRecordResult = depositRecord(depositRequest);
        serviceValidator.validateCancelPayRecord(depositRecordResult);
    }

    private boolean addBalance(AddBalanceRequest requestDto) {
        return circuitBreakerFactory
                .create(CircuitLog.PAY_CIRCUIT_LOG.getValue())
                .run(
                        () -> bankbookFeignService.addBalance(requestDto),
                        throwable -> false
                );
    }

    private boolean depositRecord(RecordRequest requestDto) {
        return circuitBreakerFactory
                .create(CircuitLog.PAY_CIRCUIT_LOG.getValue())
                .run(
                        () -> recordFeignService.depositRecord(requestDto),
                        throwable -> false
                );
    }

    private boolean subtractBalance(SubtractBalanceRequest requestDto) {
        return circuitBreakerFactory
                .create(CircuitLog.PAY_CIRCUIT_LOG.getValue())
                .run(
                        () -> bankbookFeignService.subtractBalance(requestDto),
                        throwable -> false
                );
    }

    private boolean subtractBalanceForCancel(SubtractBalanceForCancel requestDto) {
        return circuitBreakerFactory
                .create(CircuitLog.PAY_CIRCUIT_LOG.getValue())
                .run(
                        () -> bankbookFeignService.subtractBalanceForCancel(requestDto),
                        throwable -> false
                );
    }

    private boolean withdrawRecord(RecordRequest requestDto) {
        return circuitBreakerFactory
                .create(CircuitLog.PAY_CIRCUIT_LOG.getValue())
                .run(
                        () -> recordFeignService.withdrawRecord(requestDto),
                        throwable -> false
                );
    }
}

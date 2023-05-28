package intelligent_pay.remitservice.command;

import intelligent_pay.remitservice.clientWrapper.BankbookClientWrapper;
import intelligent_pay.remitservice.controller.restResponse.ResponseMessage;
import intelligent_pay.remitservice.dto.bankbook.AddBalanceRequest;
import intelligent_pay.remitservice.dto.bankbook.SubtractBalanceRequest;
import intelligent_pay.remitservice.dto.record.RecordRequest;
import intelligent_pay.remitservice.dto.remit.DepositRequest;
import intelligent_pay.remitservice.dto.remit.RemitRequest;
import intelligent_pay.remitservice.exception.RemitCustomException;
import intelligent_pay.remitservice.validator.ServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemitEventHandler {

    private final BankbookClientWrapper bankbookClientWrapper;
    private final RecordProducer recordProducer;
    private final ServiceValidator serviceValidator;

    public void deposit(DepositRequest requestDto) {
        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(requestDto.getBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean addBalanceResult = bankbookClientWrapper.addBalance(addRequest);
        serviceValidator.validateAddBalance(addBalanceResult);

        RecordRequest recordRequest = RecordRequest.builder()
                .title(requestDto.getOtherBankbookNum() + "입금")
                .bankBookNum(requestDto.getBankbookNum())
                .money(requestDto.getMoney())
                .build();
        recordProducer.depositRecord(recordRequest);
    }

    public void remit(RemitRequest requestDto) {
        SubtractBalanceRequest subtractRequest = SubtractBalanceRequest.builder()
                .bankbookNum(requestDto.getBankbookNum())
                .money(requestDto.getMoney())
                .password(requestDto.getPassword())
                .build();
        boolean subtractBalanceResult = bankbookClientWrapper.subtractBalance(subtractRequest);
        serviceValidator.validateSubtractBalance(subtractBalanceResult);

        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(requestDto.getOtherBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean addBalanceResult = bankbookClientWrapper.addBalance(addRequest);

        if (!addBalanceResult) {
            remitRollback(subtractRequest);
        }

        RecordRequest withdrawRequest = RecordRequest.builder()
                .title(requestDto.getOtherBankbookNum() + "출금")
                .bankBookNum(requestDto.getBankbookNum())
                .money(requestDto.getMoney())
                .build();
        recordProducer.withdrawRecord(withdrawRequest);

        RecordRequest depositRequest = RecordRequest.builder()
                .title(requestDto.getBankbookNum() + "입금")
                .bankBookNum(requestDto.getOtherBankbookNum())
                .money(requestDto.getMoney())
                .build();
        recordProducer.depositRecord(depositRequest);
    }

    private void remitRollback(SubtractBalanceRequest subtractRequest) {
        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(subtractRequest.getBankbookNum())
                .money(subtractRequest.getMoney())
                .build();
        bankbookClientWrapper.addBalance(addRequest);

        throw new RemitCustomException(ResponseMessage.REMIT_FAIL);
    }
}

package intelligent_pay.payservice.eventHandler;

import intelligent_pay.payservice.clientWrapper.BankbookClientWrapper;
import intelligent_pay.payservice.command.RecordProducer;
import intelligent_pay.payservice.controller.restResponse.ResponseMessage;
import intelligent_pay.payservice.dto.bankbook.AddBalanceRequest;
import intelligent_pay.payservice.dto.bankbook.SubtractBalanceForCancel;
import intelligent_pay.payservice.dto.bankbook.SubtractBalanceRequest;
import intelligent_pay.payservice.dto.pay.PayCancelRequest;
import intelligent_pay.payservice.dto.pay.PayRequest;
import intelligent_pay.payservice.dto.record.RecordRequest;
import intelligent_pay.payservice.exception.PayCustomException;
import intelligent_pay.payservice.validator.ServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayEventHandler {

    private final BankbookClientWrapper bankbookClientWrapper;
    private final RecordProducer recordProducer;
    private final ServiceValidator serviceValidator;

    public void pay(PayRequest requestDto) {
        SubtractBalanceRequest subtractRequest = SubtractBalanceRequest.builder()
                .bankbookNum(requestDto.getBuyerBankbookNum())
                .money(requestDto.getMoney())
                .password(requestDto.getPassword())
                .build();
        boolean subtractBalanceResult = bankbookClientWrapper.subtractBalance(subtractRequest);
        serviceValidator.validatePay(subtractBalanceResult);

        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(requestDto.getSellerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean addBalanceResult = bankbookClientWrapper.addBalance(addRequest);

        if (!addBalanceResult) {
            payRollback(subtractRequest);
        }

        RecordRequest withdrawRequest = RecordRequest.builder()
                .title(requestDto.getPayTitle() + "결제")
                .bankBookNum(requestDto.getBuyerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        recordProducer.withdrawRecord(withdrawRequest);

        RecordRequest depositRequest = RecordRequest.builder()
                .title(requestDto.getPayTitle() + "결제" + requestDto.getBuyerBankbookNum() + "입금")
                .bankBookNum(requestDto.getSellerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        recordProducer.depositRecord(depositRequest);
    }

    private void payRollback(SubtractBalanceRequest subtractRequest) {
        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(subtractRequest.getBankbookNum())
                .money(subtractRequest.getMoney())
                .build();
        bankbookClientWrapper.addBalance(addRequest);

        throw new PayCustomException(ResponseMessage.PAY_FAIL);
    }

    public void cancelPay(PayCancelRequest requestDto) {
        SubtractBalanceForCancel subtractRequest = SubtractBalanceForCancel.builder()
                .bankbookNum(requestDto.getSellerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean subtractBalanceResult = bankbookClientWrapper.subtractBalanceForCancel(subtractRequest);
        serviceValidator.validateCancelPay(subtractBalanceResult);

        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(requestDto.getBuyerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        boolean addBalanceResult = bankbookClientWrapper.addBalance(addRequest);

        if (!addBalanceResult) {
            cancelPayRollback(subtractRequest);
        }

        RecordRequest withdrawRequest = RecordRequest.builder()
                .title(requestDto.getPayTitle() + "결제취소" + requestDto.getBuyerBankbookNum() + "송금")
                .bankBookNum(requestDto.getSellerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        recordProducer.withdrawRecord(withdrawRequest);

        RecordRequest depositRequest = RecordRequest.builder()
                .title(requestDto.getPayTitle() + "결제취소")
                .bankBookNum(requestDto.getBuyerBankbookNum())
                .money(requestDto.getMoney())
                .build();
        recordProducer.depositRecord(depositRequest);
    }

    private void cancelPayRollback(SubtractBalanceForCancel subtractRequest) {
        AddBalanceRequest addRequest = AddBalanceRequest.builder()
                .bankbookNum(subtractRequest.getBankbookNum())
                .money(subtractRequest.getMoney())
                .build();
        bankbookClientWrapper.addBalance(addRequest);

        throw new PayCustomException(ResponseMessage.CANCEL_PAY_FAIL);
    }
}

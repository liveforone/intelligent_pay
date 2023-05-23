package intelligent_pay.remitservice.validator;

import intelligent_pay.remitservice.controller.restResponse.ResponseMessage;
import intelligent_pay.remitservice.exception.RemitCustomException;
import org.springframework.stereotype.Component;

@Component
public class ServiceValidator {

    public void validateAddBalance(Boolean addBalanceResult) {
        if (!addBalanceResult) {
            throw new RemitCustomException(ResponseMessage.DEPOSIT_FAIL);
        }
    }

    public void validateDepositRecord(Boolean depositRecordResult) {
        if (!depositRecordResult) {
            throw new RemitCustomException(ResponseMessage.DEPOSIT_RECORD_FAIL);
        }
    }

    public void validateSubtractBalance(Boolean subtractBalanceResult) {
        if (!subtractBalanceResult) {
            throw new RemitCustomException(ResponseMessage.REMIT_FAIL);
        }
    }

    public void validateWithdrawRecord(Boolean withdrawRecordResult) {
        if (!withdrawRecordResult) {
            throw new RemitCustomException(ResponseMessage.REMIT_RECORD_FAIL);
        }
    }
}

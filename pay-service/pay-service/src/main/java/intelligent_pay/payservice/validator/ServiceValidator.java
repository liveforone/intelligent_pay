package intelligent_pay.payservice.validator;

import intelligent_pay.payservice.controller.restResponse.ResponseMessage;
import intelligent_pay.payservice.exception.PayCustomException;
import org.springframework.stereotype.Component;

@Component
public class ServiceValidator {

    public void validatePay(Boolean payResult) {
        if (!payResult) {
            throw new PayCustomException(ResponseMessage.PAY_FAIL);
        }
    }

    public void validatePayRecord(Boolean payRecordResult) {
        if (!payRecordResult) {
            throw new PayCustomException(ResponseMessage.PAY_RECORD_FAIL);
        }
    }

    public void validateCancelPay(Boolean cancelPayResult) {
        if (!cancelPayResult) {
            throw new PayCustomException(ResponseMessage.CANCEL_PAY_FAIL);
        }
    }

    public void validateCancelPayRecord(Boolean cancelPayRecordResult) {
        if (!cancelPayRecordResult) {
            throw new PayCustomException(ResponseMessage.CANCEL_PAY_RECORD_FAIL);
        }
    }
}

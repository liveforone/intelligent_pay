package intelligent_pay.bankbookservice.exception;

import intelligent_pay.bankbookservice.controller.restResponse.ResponseMessage;
import lombok.Getter;

@Getter
public class BankbookCustomException extends RuntimeException{
    private ResponseMessage responseMessage;

    public BankbookCustomException(ResponseMessage responseMessage) {
        super(responseMessage.getValue());
        this.responseMessage = responseMessage;
    }
}

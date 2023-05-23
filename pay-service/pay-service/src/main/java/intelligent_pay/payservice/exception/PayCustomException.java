package intelligent_pay.payservice.exception;

import intelligent_pay.payservice.controller.restResponse.ResponseMessage;
import lombok.Getter;

@Getter
public class PayCustomException extends RuntimeException{
    private ResponseMessage responseMessage;

    public PayCustomException(ResponseMessage responseMessage) {
        super(responseMessage.getValue());
        this.responseMessage = responseMessage;
    }
}

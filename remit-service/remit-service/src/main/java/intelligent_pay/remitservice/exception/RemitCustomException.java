package intelligent_pay.remitservice.exception;

import intelligent_pay.remitservice.controller.restResponse.ResponseMessage;
import lombok.Getter;

@Getter
public class RemitCustomException extends RuntimeException{
    private ResponseMessage responseMessage;

    public RemitCustomException(ResponseMessage responseMessage) {
        super(responseMessage.getValue());
        this.responseMessage = responseMessage;
    }
}

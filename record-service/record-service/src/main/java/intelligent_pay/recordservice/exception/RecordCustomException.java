package intelligent_pay.recordservice.exception;

import intelligent_pay.recordservice.controller.restResponse.ResponseMessage;
import lombok.Getter;

@Getter
public class RecordCustomException extends RuntimeException{
    private ResponseMessage responseMessage;

    public RecordCustomException(ResponseMessage responseMessage) {
        super(responseMessage.getValue());
        this.responseMessage = responseMessage;
    }
}

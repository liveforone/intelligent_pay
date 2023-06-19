package intelligent_pay.payservice.controller.command;

import intelligent_pay.payservice.eventHandler.PayEventHandler;
import intelligent_pay.payservice.controller.restResponse.RestResponse;
import intelligent_pay.payservice.dto.pay.PayCancelRequest;
import intelligent_pay.payservice.dto.pay.PayRequest;
import intelligent_pay.payservice.validator.ControllerValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static intelligent_pay.payservice.controller.constant.PayUrl.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PayCommandController {

    private final PayEventHandler payEventHandler;
    private final ControllerValidator controllerValidator;

    @PostMapping(PAY)
    public ResponseEntity<?> pay(
            @RequestBody @Valid PayRequest requestDto,
            BindingResult bindingResult
    ) {
        controllerValidator.validateBinding(bindingResult);
        payEventHandler.pay(requestDto);
        return RestResponse.paySuccess();
    }

    @PostMapping(CANCEL_PAY)
    public ResponseEntity<?> cancelPay(
            @RequestBody @Valid PayCancelRequest requestDto,
            BindingResult bindingResult
    ) {
        controllerValidator.validateBinding(bindingResult);
        payEventHandler.cancelPay(requestDto);
        return RestResponse.cancelPaySuccess();
    }
}

package intelligent_pay.remitservice.controller.command;

import intelligent_pay.remitservice.eventHandler.RemitEventHandler;
import intelligent_pay.remitservice.controller.restResponse.RestResponse;
import intelligent_pay.remitservice.dto.remit.DepositRequest;
import intelligent_pay.remitservice.dto.remit.RemitRequest;
import intelligent_pay.remitservice.validator.ControllerValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static intelligent_pay.remitservice.controller.constant.RemitUrl.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RemitCommandController {

    private final RemitEventHandler remitEventHandler;
    private final ControllerValidator controllerValidator;

    @PostMapping(DEPOSIT)
    public ResponseEntity<?> deposit(
            @RequestBody @Valid DepositRequest requestDto,
            BindingResult bindingResult
    ) {
        controllerValidator.validateBinding(bindingResult);
        remitEventHandler.deposit(requestDto);
        return RestResponse.depositSuccess();
    }

    @PostMapping(REMIT)
    public ResponseEntity<?> remit(
            @RequestBody @Valid RemitRequest requestDto,
            BindingResult bindingResult
    ) {
        controllerValidator.validateBinding(bindingResult);
        remitEventHandler.remit(requestDto);
        return RestResponse.remitSuccess();
    }
}

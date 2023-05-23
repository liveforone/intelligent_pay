package intelligent_pay.remitservice.controller;

import intelligent_pay.remitservice.command.RemitCommandService;
import intelligent_pay.remitservice.controller.restResponse.RestResponse;
import intelligent_pay.remitservice.dto.remit.DepositRequest;
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
public class RemitController {

    private final RemitCommandService remitCommandService;
    private final ControllerValidator controllerValidator;

    @PostMapping(DEPOSIT)
    public ResponseEntity<?> deposit(
            @RequestBody @Valid DepositRequest requestDto,
            BindingResult bindingResult
    ) {
        controllerValidator.validateBinding(bindingResult);
        remitCommandService.deposit(requestDto);
        return RestResponse.depositSuccess();
    }
}

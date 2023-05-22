package intelligent_pay.bankbookservice.controller;

import intelligent_pay.bankbookservice.authentication.AuthenticationInfo;
import intelligent_pay.bankbookservice.command.BankbookCommandService;
import intelligent_pay.bankbookservice.controller.constant.BankbookParam;
import intelligent_pay.bankbookservice.controller.constant.ControllerLog;
import intelligent_pay.bankbookservice.controller.restResponse.RestResponse;
import intelligent_pay.bankbookservice.dto.request.AddBalanceRequest;
import intelligent_pay.bankbookservice.dto.request.BankbookRequest;
import intelligent_pay.bankbookservice.dto.request.SubtractBalanceRequest;
import intelligent_pay.bankbookservice.dto.response.BankbookResponse;
import intelligent_pay.bankbookservice.dto.response.BasicInfoResponse;
import intelligent_pay.bankbookservice.dto.update.UpdateBankbookStateRequest;
import intelligent_pay.bankbookservice.dto.update.UpdatePasswordRequest;
import intelligent_pay.bankbookservice.query.BankbookQueryService;
import intelligent_pay.bankbookservice.validator.ControllerValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static intelligent_pay.bankbookservice.controller.constant.BankbookUrl.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BankbookController {

    private final BankbookCommandService bankbookCommandService;
    private final BankbookQueryService bankbookQueryService;
    private final AuthenticationInfo authenticationInfo;
    private final ControllerValidator controllerValidator;

    @GetMapping(BASIC_INFO)
    public BasicInfoResponse basicInfo(
            @PathVariable(BankbookParam.USERNAME) String username
    ) {
        return bankbookQueryService.getBasicInfoByUsername(username);
    }

    @GetMapping(INFO)
    public ResponseEntity<?> bankbookInfo(HttpServletRequest request) {
        String username = authenticationInfo.getUsername(request);
        BankbookResponse bankbook = bankbookQueryService.getBankbookByUsername(username);
        return ResponseEntity.ok(bankbook);
    }

    @PostMapping(CREATE)
    public ResponseEntity<?> createBankbook(
            @RequestBody @Valid BankbookRequest requestDto,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        controllerValidator.validateBinding(bindingResult);

        String username = authenticationInfo.getUsername(request);
        bankbookCommandService.createBankbook(requestDto, username);
        log.info(ControllerLog.CREATE_BANKBOOK_SUCCESS.getValue());

        return RestResponse.createBankbookSuccess();
    }

    @PostMapping(ADD_BALANCE)
    public boolean addBalance(
            @RequestBody @Valid AddBalanceRequest requestDto,
            BindingResult bindingResult
    ) {
        controllerValidator.validateBindingThrowBool(bindingResult);

        bankbookCommandService.addBalance(requestDto);
        log.info(ControllerLog.ADD_BALANCE_SUCCESS.getValue() + requestDto.getBankbookNum());

        return true;
    }

    @PostMapping(SUBTRACT_BALANCE)
    public boolean subtractBalance(
            @RequestBody @Valid SubtractBalanceRequest requestDto,
            BindingResult bindingResult
    ) {
        controllerValidator.validateBindingThrowBool(bindingResult);

        bankbookCommandService.subtractBalance(requestDto);
        log.info(ControllerLog.SUBTRACT_BALANCE_SUCCESS.getValue() + requestDto.getBankbookNum());

        return true;
    }

    @PutMapping(UPDATE_PASSWORD)
    public ResponseEntity<?> updatePassword(
            @RequestBody @Valid UpdatePasswordRequest requestDto,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        controllerValidator.validateBinding(bindingResult);

        String username = authenticationInfo.getUsername(request);
        bankbookCommandService.updatePassword(requestDto, username);
        log.info(ControllerLog.UPDATE_PASSWORD_SUCCESS.getValue() + requestDto.getBankbookNum());

        return RestResponse.updatePasswordSuccess();
    }

    @PutMapping(SUSPEND)
    public ResponseEntity<?> suspendBankbook(
            @RequestBody @Valid UpdateBankbookStateRequest requestDto,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        controllerValidator.validateBinding(bindingResult);

        String username = authenticationInfo.getUsername(request);
        bankbookCommandService.suspendBankbook(requestDto, username);
        log.info(ControllerLog.SUSPEND_SUCCESS.getValue() + requestDto.getBankbookNum());

        return RestResponse.suspendSuccess();
    }

    @PutMapping(CANCEL_SUSPEND)
    public ResponseEntity<?> cancelSuspend(
            @RequestBody @Valid UpdateBankbookStateRequest requestDto,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        controllerValidator.validateBinding(bindingResult);

        String username = authenticationInfo.getUsername(request);
        bankbookCommandService.cancelSuspendBankbook(requestDto, username);
        log.info(ControllerLog.CANCEL_SUSPEND_SUCCESS.getValue() + requestDto.getBankbookNum());

        return RestResponse.cancelSuspendSuccess();
    }
}
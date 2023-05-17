package intelligent_pay.bankbookservice.controller;

import intelligent_pay.bankbookservice.authentication.AuthenticationInfo;
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
import intelligent_pay.bankbookservice.service.BankbookService;
import intelligent_pay.bankbookservice.validator.BankbookValidator;
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

    private final BankbookService bankbookService;
    private final AuthenticationInfo authenticationInfo;
    private final BankbookValidator bankbookValidator;

    @GetMapping(BASIC_INFO)
    public BasicInfoResponse basicInfo(
            @PathVariable(BankbookParam.USERNAME) String username
    ) {
        return bankbookService.getBasicInfoByUsername(username);
    }

    @GetMapping(INFO)
    public ResponseEntity<?> bankbookInfo(HttpServletRequest request) {
        String username = authenticationInfo.getUsername(request);
        bankbookValidator.validateBankbookNull(username);

        BankbookResponse bankbook = bankbookService.getBankbookByUsername(username);
        return ResponseEntity.ok(bankbook);
    }

    @PostMapping(CREATE)
    public ResponseEntity<?> createBankbook(
            @RequestBody @Valid BankbookRequest bankbookRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        bankbookValidator.validateBinding(bindingResult);

        String username = authenticationInfo.getUsername(request);
        bankbookValidator.validateDuplicateBankbook(username);

        bankbookService.createBankbook(bankbookRequest, username);
        log.info(ControllerLog.CREATE_BANKBOOK_SUCCESS.getValue());

        return RestResponse.createBankbookSuccess();
    }

    @PostMapping(ADD_BALANCE)
    public boolean addBalance(
            @RequestBody @Valid AddBalanceRequest addBalanceRequest,
            BindingResult bindingResult
    ) {
        bankbookValidator.validateBindingThrowBool(bindingResult);
        String bankbookNum = addBalanceRequest.getBankbookNum();
        bankbookValidator.validateBankbookNullThrowBool(bankbookNum);
        bankbookValidator.validateBankbookStateThrowBool(bankbookNum);

        bankbookService.addBalance(addBalanceRequest);
        log.info(ControllerLog.ADD_BALANCE_SUCCESS.getValue() + bankbookNum);

        return true;
    }

    @PostMapping(SUBTRACT_BALANCE)
    public boolean subtractBalance(
            @RequestBody @Valid SubtractBalanceRequest subtractBalanceRequest,
            BindingResult bindingResult
    ) {
        bankbookValidator.validateBindingThrowBool(bindingResult);
        String bankbookNum = subtractBalanceRequest.getBankbookNum();
        bankbookValidator.validateBankbookStateThrowBool(bankbookNum);
        bankbookValidator.validatePasswordThrowBool(
                subtractBalanceRequest.getPassword(),
                bankbookNum
        );

        bankbookService.subtractBalance(subtractBalanceRequest);
        log.info(ControllerLog.SUBTRACT_BALANCE_SUCCESS.getValue() + bankbookNum);

        return true;
    }

    @PutMapping(UPDATE_PASSWORD)
    public ResponseEntity<?> updatePassword(
            @RequestBody @Valid UpdatePasswordRequest requestDto,
            BindingResult bindingResult
    ) {
        bankbookValidator.validateBinding(bindingResult);
        String bankbookNum = requestDto.getBankbookNum();
        bankbookValidator.validateBankbookState(bankbookNum);
        bankbookValidator.validatePassword(
                requestDto.getPassword(),
                bankbookNum
        );

        bankbookService.updatePassword(requestDto);
        log.info(ControllerLog.UPDATE_PASSWORD_SUCCESS.getValue() + bankbookNum);

        return RestResponse.updatePasswordSuccess();
    }

    @PutMapping(SUSPEND)
    public ResponseEntity<?> suspendBankbook(
            @RequestBody @Valid UpdateBankbookStateRequest requestDto,
            BindingResult bindingResult
    ) {
        bankbookValidator.validateBinding(bindingResult);
        String bankbookNum = requestDto.getBankbookNum();
        bankbookValidator.validateBankbookNull(bankbookNum);
        bankbookValidator.validatePassword(
                requestDto.getPassword(),
                bankbookNum
        );

        bankbookService.suspendBankbook(bankbookNum);
        log.info(ControllerLog.SUSPEND_SUCCESS.getValue() + bankbookNum);

        return RestResponse.suspendSuccess();
    }

    @PutMapping(CANCEL_SUSPEND)
    public ResponseEntity<?> cancelSuspend(
            @RequestBody @Valid UpdateBankbookStateRequest requestDto,
            BindingResult bindingResult
    ) {
        bankbookValidator.validateBinding(bindingResult);
        String bankbookNum = requestDto.getBankbookNum();
        bankbookValidator.validateBankbookNull(bankbookNum);
        bankbookValidator.validatePassword(
                requestDto.getPassword(),
                bankbookNum
        );

        bankbookService.cancelSuspendBankbook(bankbookNum);
        log.info(ControllerLog.CANCEL_SUSPEND_SUCCESS.getValue() + bankbookNum);

        return RestResponse.cancelSuspendSuccess();
    }
}
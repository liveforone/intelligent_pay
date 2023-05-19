package intelligent_pay.userservice.controller;

import intelligent_pay.userservice.authentication.AuthenticationInfo;
import intelligent_pay.userservice.command.MemberCommandService;
import intelligent_pay.userservice.command.MemberProducerService;
import intelligent_pay.userservice.controller.constant.ControllerLog;
import intelligent_pay.userservice.controller.constant.MemberParam;
import intelligent_pay.userservice.controller.restResponse.RestResponse;
import intelligent_pay.userservice.dto.changeInfo.ChangeEmailRequest;
import intelligent_pay.userservice.dto.changeInfo.ChangePasswordRequest;
import intelligent_pay.userservice.dto.bankbook.BasicBankbookInfoResponse;
import intelligent_pay.userservice.dto.response.MemberInfoResponse;
import intelligent_pay.userservice.dto.response.MemberResponse;
import intelligent_pay.userservice.dto.signupAndLogin.MemberLoginRequest;
import intelligent_pay.userservice.dto.signupAndLogin.MemberSignupRequest;
import intelligent_pay.userservice.feignClient.BankbookFeignService;
import intelligent_pay.userservice.feignClient.constant.CircuitLog;
import intelligent_pay.userservice.jwt.TokenInfo;
import intelligent_pay.userservice.jwt.constant.JwtConstant;
import intelligent_pay.userservice.query.MemberQueryService;
import intelligent_pay.userservice.validator.ControllerValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static intelligent_pay.userservice.controller.constant.MemberUrl.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final MemberProducerService memberProducerService;
    private final ControllerValidator controllerValidator;
    private final AuthenticationInfo authenticationInfo;
    private final BankbookFeignService bankbookFeignService;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    @PostMapping(SIGNUP)
    public ResponseEntity<?> signup(
            @RequestBody @Valid MemberSignupRequest memberSignupRequest,
            BindingResult bindingResult
    ) {
        controllerValidator.validateBinding(bindingResult);

        String email = memberSignupRequest.getEmail();
        controllerValidator.validateDuplicateEmail(email);

        memberCommandService.signup(memberSignupRequest);
        log.info(ControllerLog.SIGNUP_SUCCESS.getValue());

        return RestResponse.signupSuccess();
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> login(
            @RequestBody @Valid MemberLoginRequest memberLoginRequest,
            BindingResult bindingResult,
            HttpServletResponse response
    ) {
        controllerValidator.validateBinding(bindingResult);

        TokenInfo tokenInfo = memberCommandService.login(memberLoginRequest);
        log.info(ControllerLog.LOGIN_SUCCESS.getValue());

        response.addHeader(JwtConstant.ACCESS_TOKEN, tokenInfo.getAccessToken());
        response.addHeader(JwtConstant.REFRESH_TOKEN, tokenInfo.getRefreshToken());
        return RestResponse.loginSuccess();
    }

    @GetMapping(MY_INFO)
    public ResponseEntity<?> myInfo(HttpServletRequest request) {
        String username = authenticationInfo.getUsername(request);
        MemberResponse member = memberQueryService.getMemberByUsername(username);
        BasicBankbookInfoResponse bankbookInfo = getBasicBankbookInfoByUsername(username);

        MemberInfoResponse response = MemberInfoResponse.builder()
                .memberResponse(member)
                .basicBankbookInfoResponse(bankbookInfo)
                .build();

        return ResponseEntity.ok(response);
    }

    private BasicBankbookInfoResponse getBasicBankbookInfoByUsername(String username) {
        return circuitBreakerFactory
                .create(CircuitLog.USER_CIRCUIT_LOG.getValue())
                .run(
                        () -> bankbookFeignService.getBasicInfoByUsername(username),
                        throwable -> new BasicBankbookInfoResponse()
                );
    }

    @PutMapping(CHANGE_EMAIL)
    public ResponseEntity<?> changeEmail(
            @RequestBody @Valid ChangeEmailRequest changeEmailRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        controllerValidator.validateBinding(bindingResult);
        controllerValidator.validateDuplicateEmail(changeEmailRequest.getEmail());

        String username = authenticationInfo.getUsername(request);
        memberCommandService.updateEmail(changeEmailRequest, username);
        log.info(ControllerLog.CHANGE_EMAIL_SUCCESS.getValue());

        return RestResponse.changeEmailSuccess();
    }

    @PutMapping(CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        controllerValidator.validateBinding(bindingResult);

        String inputPw = changePasswordRequest.getOldPassword();
        String username = authenticationInfo.getUsername(request);
        controllerValidator.validatePassword(inputPw, username);

        String requestPw = changePasswordRequest.getNewPassword();
        memberCommandService.updatePassword(requestPw, username);
        log.info(ControllerLog.CHANGE_PASSWORD_SUCCESS.getValue());

        return RestResponse.changePasswordSuccess();
    }

    @DeleteMapping(WITHDRAW)
    public ResponseEntity<?> withdraw(
            @RequestBody String password,
            HttpServletRequest request
    ) {
        String username = authenticationInfo.getUsername(request);
        controllerValidator.validatePassword(password, username);

        memberProducerService.removeBankbook(username);
        memberProducerService.removeRecord(username);
        memberCommandService.withdrawByUsername(username);
        log.info(ControllerLog.WITHDRAW_SUCCESS.getValue() + username);

        return RestResponse.withdrawSuccess();
    }

    @GetMapping(ADMIN_SEARCH)
    public ResponseEntity<?> adminPage(
            @RequestParam(MemberParam.EMAIL) String email,
            HttpServletRequest request
    ) {
        String username = authenticationInfo.getUsername(request);
        controllerValidator.validateAdmin(username);
        log.info(ControllerLog.ADMIN_SUCCESS.getValue());

        List<MemberResponse> foundMember = memberQueryService.searchByEmail(email);
        return ResponseEntity.ok(foundMember);
    }

    @GetMapping(PROHIBITION)
    public ResponseEntity<?> prohibition() {
        return RestResponse.prohibition();
    }
}

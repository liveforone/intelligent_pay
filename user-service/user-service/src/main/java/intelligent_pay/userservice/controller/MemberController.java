package intelligent_pay.userservice.controller;

import intelligent_pay.userservice.authentication.AuthenticationInfo;
import intelligent_pay.userservice.controller.constant.ControllerLog;
import intelligent_pay.userservice.controller.constant.MemberParam;
import intelligent_pay.userservice.controller.restResponse.RestResponse;
import intelligent_pay.userservice.dto.changeInfo.ChangeEmailRequest;
import intelligent_pay.userservice.dto.changeInfo.ChangePasswordRequest;
import intelligent_pay.userservice.dto.response.MemberInfoResponse;
import intelligent_pay.userservice.dto.response.MemberResponse;
import intelligent_pay.userservice.dto.signupAndLogin.MemberLoginRequest;
import intelligent_pay.userservice.dto.signupAndLogin.MemberSignupRequest;
import intelligent_pay.userservice.feignClient.BankbookFeignService;
import intelligent_pay.userservice.feignClient.constant.CircuitLog;
import intelligent_pay.userservice.jwt.TokenInfo;
import intelligent_pay.userservice.jwt.constant.JwtConstant;
import intelligent_pay.userservice.service.MemberService;
import intelligent_pay.userservice.validator.MemberValidator;
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

    private final MemberService memberService;
    private final MemberValidator memberValidator;
    private final AuthenticationInfo authenticationInfo;
    private final BankbookFeignService bankbookFeignService;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    @PostMapping(SIGNUP)
    public ResponseEntity<?> signup(
            @RequestBody @Valid MemberSignupRequest memberSignupRequest,
            BindingResult bindingResult
    ) {
        memberValidator.validateBinding(bindingResult);

        String email = memberSignupRequest.getEmail();
        memberValidator.validateDuplicateEmail(email);

        memberService.signup(memberSignupRequest);
        log.info(ControllerLog.SIGNUP_SUCCESS.getValue());

        return RestResponse.signupSuccess();
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> login(
            @RequestBody @Valid MemberLoginRequest memberLoginRequest,
            BindingResult bindingResult,
            HttpServletResponse response
    ) {
        memberValidator.validateBinding(bindingResult);

        TokenInfo tokenInfo = memberService.login(memberLoginRequest);
        log.info(ControllerLog.LOGIN_SUCCESS.getValue());

        response.addHeader(JwtConstant.ACCESS_TOKEN, tokenInfo.getAccessToken());
        response.addHeader(JwtConstant.REFRESH_TOKEN, tokenInfo.getRefreshToken());
        return RestResponse.loginSuccess();
    }

    @GetMapping(MY_INFO)
    public ResponseEntity<?> myInfo(HttpServletRequest request) {
        String username = authenticationInfo.getUsername(request);
        MemberResponse member = memberService.getMemberByUsername(username);
        long balance = getBalanceByUsername();

        MemberInfoResponse response = MemberInfoResponse.builder()
                .memberResponse(member)
                .balance(balance)
                .build();

        return ResponseEntity.ok(response);
    }

    private long getBalanceByUsername() {
        return circuitBreakerFactory
                .create(CircuitLog.USER_CIRCUIT_LOG.getValue())
                .run(
                        bankbookFeignService::getBalanceByUsername,
                        throwable -> 0L
                );
    }

    @PutMapping(CHANGE_EMAIL)
    public ResponseEntity<?> changeEmail(
            @RequestBody @Valid ChangeEmailRequest changeEmailRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        memberValidator.validateBinding(bindingResult);
        memberValidator.validateDuplicateEmail(changeEmailRequest.getEmail());

        String username = authenticationInfo.getUsername(request);
        memberService.updateEmail(changeEmailRequest, username);
        log.info(ControllerLog.CHANGE_EMAIL_SUCCESS.getValue());

        return RestResponse.changeEmailSuccess();
    }

    @PutMapping(CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        memberValidator.validateBinding(bindingResult);

        String inputPw = changePasswordRequest.getOldPassword();
        String username = authenticationInfo.getUsername(request);
        memberValidator.validatePassword(inputPw, username);

        String requestPw = changePasswordRequest.getNewPassword();
        memberService.updatePassword(requestPw, username);
        log.info(ControllerLog.CHANGE_PASSWORD_SUCCESS.getValue());

        return RestResponse.changePasswordSuccess();
    }

    @DeleteMapping(WITHDRAW)
    public ResponseEntity<?> withdraw(
            @RequestBody String password,
            HttpServletRequest request
    ) {
        String username = authenticationInfo.getUsername(request);
        memberValidator.validatePassword(password, username);

        memberService.withdrawByUsername(username);
        log.info(ControllerLog.WITHDRAW_SUCCESS.getValue() + username);

        return RestResponse.withdrawSuccess();
    }

    @GetMapping(ADMIN_SEARCH)
    public ResponseEntity<?> adminPage(
            @RequestParam(MemberParam.EMAIL) String email,
            HttpServletRequest request
    ) {
        String username = authenticationInfo.getUsername(request);
        memberValidator.validateAdmin(username);
        log.info(ControllerLog.ADMIN_SUCCESS.getValue());

        List<MemberResponse> foundMember = memberService.searchByEmail(email);
        return ResponseEntity.ok(foundMember);
    }

    @GetMapping(PROHIBITION)
    public ResponseEntity<?> prohibition() {
        return RestResponse.prohibition();
    }
}

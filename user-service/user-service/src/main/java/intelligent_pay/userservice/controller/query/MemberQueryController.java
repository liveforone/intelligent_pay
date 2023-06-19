package intelligent_pay.userservice.controller.query;

import intelligent_pay.userservice.authentication.AuthenticationInfo;
import intelligent_pay.userservice.clientWrapper.BankbookClientWrapper;
import intelligent_pay.userservice.controller.constant.ControllerLog;
import intelligent_pay.userservice.controller.constant.MemberParam;
import intelligent_pay.userservice.controller.restResponse.RestResponse;
import intelligent_pay.userservice.dto.bankbook.BasicBankbookInfoResponse;
import intelligent_pay.userservice.dto.response.MemberInfoResponse;
import intelligent_pay.userservice.dto.response.MemberResponse;
import intelligent_pay.userservice.service.query.MemberQueryService;
import intelligent_pay.userservice.validator.ControllerValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static intelligent_pay.userservice.controller.constant.MemberUrl.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberQueryController {

    private final MemberQueryService memberQueryService;
    private final ControllerValidator controllerValidator;
    private final AuthenticationInfo authenticationInfo;
    private final BankbookClientWrapper bankbookClientWrapper;

    @GetMapping(MY_INFO)
    public ResponseEntity<?> myInfo(HttpServletRequest request) {
        String username = authenticationInfo.getUsername(request);
        MemberResponse member = memberQueryService.getMemberByUsername(username);
        BasicBankbookInfoResponse bankbookInfo = bankbookClientWrapper.getBasicBankbookInfoByUsername(username);

        MemberInfoResponse response = MemberInfoResponse.builder()
                .memberResponse(member)
                .basicBankbookInfoResponse(bankbookInfo)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(ADMIN_SEARCH)
    public ResponseEntity<?> adminPage(
            @RequestParam(MemberParam.EMAIL) String email,
            HttpServletRequest request
    ) {
        controllerValidator.validateAdmin(authenticationInfo.getAuth(request));
        log.info(ControllerLog.ADMIN_SUCCESS.getValue());

        List<MemberResponse> foundMember = memberQueryService.searchByEmail(email);
        return ResponseEntity.ok(foundMember);
    }

    @GetMapping(PROHIBITION)
    public ResponseEntity<?> prohibition() {
        return RestResponse.prohibition();
    }
}

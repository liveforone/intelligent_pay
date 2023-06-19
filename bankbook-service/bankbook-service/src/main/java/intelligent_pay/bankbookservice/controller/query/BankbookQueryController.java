package intelligent_pay.bankbookservice.controller.query;

import intelligent_pay.bankbookservice.authentication.AuthenticationInfo;
import intelligent_pay.bankbookservice.controller.constant.BankbookParam;
import intelligent_pay.bankbookservice.dto.response.BankbookResponse;
import intelligent_pay.bankbookservice.dto.response.BasicInfoResponse;
import intelligent_pay.bankbookservice.service.query.BankbookQueryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static intelligent_pay.bankbookservice.controller.constant.BankbookUrl.*;

@RestController
@RequiredArgsConstructor
public class BankbookQueryController {

    private final BankbookQueryService bankbookQueryService;
    private final AuthenticationInfo authenticationInfo;

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

    @GetMapping(CHECK_BANKBOOK)
    public ResponseEntity<?> checkBankbook(
            @PathVariable(BankbookParam.BANKBOOK_NUM) String bankbookNum
    ) {
        String bankbook = bankbookQueryService.checkBankbookByBankbookNum(bankbookNum);
        return ResponseEntity.ok(bankbook);
    }
}

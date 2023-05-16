package intelligent_pay.bankbookservice.controller;

import intelligent_pay.bankbookservice.authentication.AuthenticationInfo;
import intelligent_pay.bankbookservice.controller.constant.BankbookUrl;
import intelligent_pay.bankbookservice.dto.BankbookResponse;
import intelligent_pay.bankbookservice.service.BankbookService;
import intelligent_pay.bankbookservice.validator.BankbookValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BankbookController {

    private final BankbookService bankbookService;
    private final AuthenticationInfo authenticationInfo;
    private final BankbookValidator bankbookValidator;

    @GetMapping(BankbookUrl.RETURN_BALANCE)
    public ResponseEntity<?> returnBalance(HttpServletRequest request) {
        String username = authenticationInfo.getUsername(request);
        bankbookValidator.validateBankbookNull(username);

        BankbookResponse bankbook = bankbookService.getBankbookByUsername(username);
        return ResponseEntity.ok(bankbook.getBalance());
    }
}

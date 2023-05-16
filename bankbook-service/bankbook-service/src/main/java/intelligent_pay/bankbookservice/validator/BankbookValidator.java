package intelligent_pay.bankbookservice.validator;

import intelligent_pay.bankbookservice.repository.BankbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankbookValidator {

    private final BankbookRepository bankbookRepository;


}

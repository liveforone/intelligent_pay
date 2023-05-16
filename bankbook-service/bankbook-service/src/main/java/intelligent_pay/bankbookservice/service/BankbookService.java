package intelligent_pay.bankbookservice.service;

import intelligent_pay.bankbookservice.repository.BankbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BankbookService {

    private final BankbookRepository bankbookRepository;


}

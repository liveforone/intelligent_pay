package intelligent_pay.bankbookservice.service;

import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.dto.BankbookRequest;
import intelligent_pay.bankbookservice.dto.BankbookResponse;
import intelligent_pay.bankbookservice.repository.BankbookRepository;
import intelligent_pay.bankbookservice.service.util.BankbookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BankbookService {

    private final BankbookRepository bankbookRepository;

    public BankbookResponse getBankbookByUsername(String username) {
        return BankbookMapper.entityToDto(
                bankbookRepository.findOneByUsername(username)
        );
    }

    @Transactional
    public void createBankbook(BankbookRequest bankbookRequest, String username) {
        Bankbook bankbook = Bankbook.create(bankbookRequest, username);
        bankbookRepository.save(bankbook);
    }
}

package intelligent_pay.bankbookservice.query;

import intelligent_pay.bankbookservice.controller.restResponse.ResponseMessage;
import intelligent_pay.bankbookservice.dto.response.BankbookResponse;
import intelligent_pay.bankbookservice.dto.response.BasicInfoResponse;
import intelligent_pay.bankbookservice.exception.BankbookCustomException;
import intelligent_pay.bankbookservice.repository.BankbookRepository;
import intelligent_pay.bankbookservice.query.util.BankbookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BankbookQueryService {

    private final BankbookRepository bankbookRepository;

    public BankbookResponse getBankbookByUsername(String username) {
        return BankbookMapper.entityToDto(
                bankbookRepository.findOneByUsername(username)
                        .orElseThrow(() -> new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL))
        );
    }

    public BasicInfoResponse getBasicInfoByUsername(String username) {
        return bankbookRepository.findBasicInfoByUsername(username);
    }

    public String checkBankbookByBankbookNum(String bankbookNum) {
        return bankbookRepository.checkBankbookByBankbookNum(bankbookNum);
    }
}

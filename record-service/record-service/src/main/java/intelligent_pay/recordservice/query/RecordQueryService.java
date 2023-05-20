package intelligent_pay.recordservice.query;

import intelligent_pay.recordservice.domain.RecordState;
import intelligent_pay.recordservice.dto.RecordResponse;
import intelligent_pay.recordservice.query.util.RecordMapper;
import intelligent_pay.recordservice.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordQueryService {

    private final RecordRepository recordRepository;

    public RecordResponse getRecordById(Long id) {
        return RecordMapper.entityToDto(recordRepository.findOneById(id));
    }

    public List<RecordResponse> getRecordsByBankbookNum(String bankbookNum, Long lastId) {
        return recordRepository.findRecordsByBankbookNum(bankbookNum, lastId);
    }

    public List<RecordResponse> getDepositRecords(String bankbookNum, Long lastId) {
        return recordRepository.findRecordsByBankbookNumAndRecordState(
                bankbookNum, RecordState.DEPOSIT, lastId
        );
    }

    public List<RecordResponse> getWithdrawRecords(String bankbookNum, Long lastId) {
        return recordRepository.findRecordsByBankbookNumAndRecordState(
                bankbookNum, RecordState.WITHDRAW, lastId
        );
    }

    public List<RecordResponse> searchYear(int year, String bankbookNum, Long lastId) {
        return recordRepository.searchRecordsByYear(year, bankbookNum, lastId);
    }

    public List<RecordResponse> searchMonth(int year, int month, String bankbookNum, Long lastId) {
        return recordRepository.searchRecordsByMonth(year, month, bankbookNum, lastId);
    }
}

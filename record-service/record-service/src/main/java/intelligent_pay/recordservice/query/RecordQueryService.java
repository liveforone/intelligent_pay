package intelligent_pay.recordservice.query;

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
}
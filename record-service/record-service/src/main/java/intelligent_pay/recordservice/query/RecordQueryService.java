package intelligent_pay.recordservice.query;

import intelligent_pay.recordservice.domain.Record;
import intelligent_pay.recordservice.dto.RecordResponse;
import intelligent_pay.recordservice.query.util.RecordMapper;
import intelligent_pay.recordservice.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordQueryService {

    private final RecordRepository recordRepository;

    public RecordResponse getRecordById(Long id) {
        return RecordMapper.entityToDto(recordRepository.findOneById(id));
    }
    //detail 은 id로
    //내것 페이징은 bankbookNum
}

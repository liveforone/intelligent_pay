package intelligent_pay.recordservice.repository;

import intelligent_pay.recordservice.domain.Record;
import intelligent_pay.recordservice.domain.RecordState;
import intelligent_pay.recordservice.dto.RecordResponse;

import java.util.List;

public interface RecordCustomRepository {

    Record findOneByUsername(String username);
    Record findOneById(Long id);
    List<RecordResponse> findRecordsByBankbookNum(String bankbookNum, Long lastId);
    List<RecordResponse> findRecordsByBankbookNumAndRecordState(String bankbookNum, RecordState recordState, Long lastId);
}

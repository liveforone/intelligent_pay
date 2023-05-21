package intelligent_pay.recordservice.repository;

import intelligent_pay.recordservice.domain.Record;
import intelligent_pay.recordservice.domain.RecordState;
import intelligent_pay.recordservice.dto.RecordResponse;

import java.util.List;

public interface RecordCustomRepository {

    Long findIdById(Long id);
    Record findOneByUsername(String username);
    Record findOneById(Long id);
    List<RecordResponse> findRecordsByBankbookNum(String bankbookNum, Long lastId);
    List<RecordResponse> findRecordsByBankbookNumAndRecordState(String bankbookNum, RecordState recordState, Long lastId);
    List<RecordResponse> searchRecordsByYear(int year, String bankbookNum, Long lastId);
    List<RecordResponse> searchRecordsByMonth(int year, int month, String bankbookNum, Long lastId);
    List<RecordResponse> searchRecordsByTitle(String keyword, String bankbookNum, Long lastId);
}

package intelligent_pay.recordservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import intelligent_pay.recordservice.domain.QRecord;
import intelligent_pay.recordservice.domain.Record;
import intelligent_pay.recordservice.domain.RecordState;
import intelligent_pay.recordservice.dto.RecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static intelligent_pay.recordservice.repository.util.RecordRepositoryUtil.*;

@Repository
@RequiredArgsConstructor
public class RecordRepositoryImpl implements RecordCustomRepository {

    private final JPAQueryFactory queryFactory;
    QRecord record = QRecord.record;

    public Record findOneByUsername(String username) {
        return queryFactory
                .selectFrom(record)
                .where(record.username.eq(username))
                .fetchOne();
    }

    public Record findOneById(Long id) {
        return queryFactory
                .selectFrom(record)
                .where(record.id.eq(id))
                .fetchOne();
    }

    public List<RecordResponse> findRecordsByBankbookNum(
            String bankbookNum,
            Long lastId
    ) {
        return queryFactory
                .select(dtoConstructor())
                .from(record)
                .where(
                        record.bankBookNum.eq(bankbookNum),
                        ltRecordId(lastId)
                )
                .orderBy(record.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }

    public List<RecordResponse> findRecordsByBankbookNumAndRecordState(
            String bankbookNum,
            RecordState recordState,
            Long lastId
    ) {
        return queryFactory
                .select(dtoConstructor())
                .from(record)
                .where(
                        record.bankBookNum.eq(bankbookNum),
                        record.recordState.eq(recordState),
                        ltRecordId(lastId)
                )
                .orderBy(record.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }
}

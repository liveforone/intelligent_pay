package intelligent_pay.recordservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import intelligent_pay.recordservice.domain.QRecord;
import intelligent_pay.recordservice.domain.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}

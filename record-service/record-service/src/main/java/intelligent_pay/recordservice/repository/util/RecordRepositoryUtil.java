package intelligent_pay.recordservice.repository.util;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import intelligent_pay.recordservice.domain.QRecord;
import intelligent_pay.recordservice.dto.RecordResponse;
import intelligent_pay.recordservice.utility.CommonUtils;

public class RecordRepositoryUtil {

    public static final int PAGE_SIZE = 10;
    private static final QRecord record;

    static {
        record = QRecord.record;
    }

    public static BooleanExpression ltRecordId(Long lastId) {
        if (lastId == 0) {
            return null;
        }

        return record.id.lt(lastId);
    }

    public static ConstructorExpression<RecordResponse> dtoConstructor() {
        return Projections.constructor(RecordResponse.class,
                record.id,
                record.title,
                record.money,
                record.recordState,
                record.createdYear,
                record.createdMonth);
    }

    public static BooleanExpression searchTitle(String title) {
        if (CommonUtils.isNull(title)) {
            return null;
        }

        return record.title.startsWith(title);
    }
}

package intelligent_pay.recordservice.repository.util;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import intelligent_pay.recordservice.domain.QRecord;
import intelligent_pay.recordservice.dto.RecordResponse;
import intelligent_pay.recordservice.utility.CommonUtils;

import java.time.LocalDate;

public class RecordRepositoryUtil {

    public static final int PAGE_SIZE = 10;
    private static final int DEFAULT_INT = 0;
    private static final QRecord record;

    static {
        record = QRecord.record;
    }

    public static BooleanExpression ltRecordId(Long lastId) {
        if (lastId == DEFAULT_INT) {
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

    public static BooleanExpression dynamicYear(int year) {
        if (year == DEFAULT_INT) {
            return record.createdYear.eq(LocalDate.now().getYear());
        }

        return record.createdYear.eq(year);
    }

    public static BooleanExpression dynamicMonth(int month) {
        if (month == DEFAULT_INT) {

        }
    }
}

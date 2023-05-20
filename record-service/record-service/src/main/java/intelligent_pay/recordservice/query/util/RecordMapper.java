package intelligent_pay.recordservice.query.util;

import intelligent_pay.recordservice.domain.Record;
import intelligent_pay.recordservice.dto.RecordResponse;
import intelligent_pay.recordservice.utility.CommonUtils;

public class RecordMapper {

    public static RecordResponse entityToDto(Record record) {
        if (CommonUtils.isNull(record)) {
            return new RecordResponse();
        }

        return RecordResponse.builder()
                .id(record.getId())
                .title(record.getTitle())
                .money(record.getMoney())
                .recordState(record.getRecordState())
                .createdYear(record.getCreatedYear())
                .createdMonth(record.getCreatedMonth())
                .build();
    }
}

package intelligent_pay.recordservice.dto.util;

import intelligent_pay.recordservice.domain.Record;
import intelligent_pay.recordservice.dto.RecordResponse;

public class RecordMapper {

    public static RecordResponse entityToDto(Record record) {
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

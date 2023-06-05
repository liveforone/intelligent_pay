package intelligent_pay.recordservice.converter;

import intelligent_pay.recordservice.domain.RecordState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RecordStateConverter implements AttributeConverter<RecordState, String> {

    @Override
    public String convertToDatabaseColumn(RecordState attribute) {
        return attribute.name();
    }

    @Override
    public RecordState convertToEntityAttribute(String dbData) {
        return RecordState.valueOf(dbData);
    }
}

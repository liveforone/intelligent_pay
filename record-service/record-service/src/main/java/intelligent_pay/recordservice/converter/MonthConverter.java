package intelligent_pay.recordservice.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Month;

@Converter
public class MonthConverter implements AttributeConverter<Month, String> {

    @Override
    public String convertToDatabaseColumn(Month attribute) {
        return attribute.name();
    }

    @Override
    public Month convertToEntityAttribute(String dbData) {
        return Month.valueOf(dbData);
    }
}

package intelligent_pay.bankbookservice.converter;

import intelligent_pay.bankbookservice.domain.BankbookState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BankbookStateConverter implements AttributeConverter<BankbookState, String> {

    @Override
    public String convertToDatabaseColumn(BankbookState attribute) {
        return attribute.name();
    }

    @Override
    public BankbookState convertToEntityAttribute(String dbData) {
        return BankbookState.valueOf(dbData);
    }
}

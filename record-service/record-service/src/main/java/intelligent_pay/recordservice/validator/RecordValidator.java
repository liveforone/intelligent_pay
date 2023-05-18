package intelligent_pay.recordservice.validator;

import intelligent_pay.recordservice.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecordValidator {

    private final RecordRepository recordRepository;
}

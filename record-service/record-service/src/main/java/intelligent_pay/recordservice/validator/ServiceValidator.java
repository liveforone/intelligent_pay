package intelligent_pay.recordservice.validator;

import intelligent_pay.recordservice.exception.returnBool.RecordCustomBoolException;
import intelligent_pay.recordservice.repository.RecordRepository;
import intelligent_pay.recordservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceValidator {

    private final RecordRepository recordRepository;

    public void validateRecordNull(Long id) {
        Long foundId = recordRepository.findIdById(id);

        if (CommonUtils.isNull(foundId)) {
            throw new RecordCustomBoolException();
        }
    }
}

package intelligent_pay.recordservice.command;

import intelligent_pay.recordservice.domain.Record;
import intelligent_pay.recordservice.domain.RecordState;
import intelligent_pay.recordservice.dto.RecordRequest;
import intelligent_pay.recordservice.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordCommandService {

    private final RecordRepository recordRepository;

    public Long createDepositRecord(RecordRequest requestDto) {
        Record record = Record.create(requestDto, RecordState.DEPOSIT);
        return recordRepository.save(record).getId();
    }

    public Long createWithdrawRecord(RecordRequest requestDto) {
        Record record = Record.create(requestDto, RecordState.WITHDRAW);
        return recordRepository.save(record).getId();
    }
}

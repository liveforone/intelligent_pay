package intelligent_pay.recordservice.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import intelligent_pay.recordservice.async.AsyncConstant;
import intelligent_pay.recordservice.domain.Record;
import intelligent_pay.recordservice.kafka.KafkaLog;
import intelligent_pay.recordservice.kafka.Topic;
import intelligent_pay.recordservice.repository.RecordRepository;
import intelligent_pay.recordservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RecordConsumerService {

    private final RecordRepository recordRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = Topic.REMOVE_RECORD_BELONG_USER)
    @Async(AsyncConstant.commandAsync)
    public void removeRecord(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        String username = objectMapper.readValue(kafkaMessage, String.class);

        if (CommonUtils.isNull(username)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            Record record = recordRepository.findOneByUsername(username);
            recordRepository.delete(record);
            log.info(KafkaLog.REMOVE_RECORD_BELONG_USER.getValue() + username);
        }
    }
}

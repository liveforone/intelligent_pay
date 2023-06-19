package intelligent_pay.recordservice.consumer.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import intelligent_pay.recordservice.dto.RecordRequest;
import intelligent_pay.recordservice.consumer.log.ConsumerLog;
import intelligent_pay.recordservice.service.command.RecordCommandService;
import intelligent_pay.recordservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static intelligent_pay.recordservice.consumer.model.ConsumerTopic.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecordConsumer {

    private final RecordCommandService recordCommandService;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = DEPOSIT_RECORD)
    public void depositRecord(String kafkaMessage) throws JsonProcessingException {
        log.info(ConsumerLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        RecordRequest recordRequest = objectMapper.readValue(kafkaMessage, RecordRequest.class);

        if (CommonUtils.isNull(recordRequest)) {
            log.info(ConsumerLog.KAFKA_NULL_LOG.getValue());
        } else {
            recordCommandService.createDepositRecord(recordRequest);
            log.info(ConsumerLog.DEPOSIT_RECORD.getValue() + recordRequest.getBankBookNum());
        }
    }

    @KafkaListener(topics = WITHDRAW_RECORD)
    public void withdrawRecord(String kafkaMessage) throws JsonProcessingException {
        log.info(ConsumerLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        RecordRequest recordRequest = objectMapper.readValue(kafkaMessage, RecordRequest.class);

        if (CommonUtils.isNull(recordRequest)) {
            log.info(ConsumerLog.KAFKA_NULL_LOG.getValue());
        } else {
            recordCommandService.createWithdrawRecord(recordRequest);
            log.info(ConsumerLog.WITHDRAW_RECORD.getValue() + recordRequest.getBankBookNum());
        }
    }
}

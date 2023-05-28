package intelligent_pay.recordservice.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import intelligent_pay.recordservice.async.AsyncConstant;
import intelligent_pay.recordservice.dto.RecordRequest;
import intelligent_pay.recordservice.kafka.KafkaLog;
import intelligent_pay.recordservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static intelligent_pay.recordservice.kafka.Topic.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShopConsumer {

    private final RecordCommandService recordCommandService;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = DEPOSIT)
    @Async(AsyncConstant.commandAsync)
    public void deposit(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        RecordRequest recordRequest = objectMapper.readValue(kafkaMessage, RecordRequest.class);

        if (CommonUtils.isNull(recordRequest)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            recordCommandService.createDepositRecord(recordRequest);
            log.info(KafkaLog.DEPOSIT.getValue() + recordRequest.getBankBookNum());
        }
    }

    @KafkaListener(topics = WITHDRAW)
    @Async(AsyncConstant.commandAsync)
    public void withdraw(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        RecordRequest recordRequest = objectMapper.readValue(kafkaMessage, RecordRequest.class);

        if (CommonUtils.isNull(recordRequest)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            recordCommandService.createWithdrawRecord(recordRequest);
            log.info(KafkaLog.WITHDRAW.getValue() + recordRequest.getBankBookNum());
        }
    }
}

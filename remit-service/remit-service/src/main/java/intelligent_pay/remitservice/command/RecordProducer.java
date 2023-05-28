package intelligent_pay.remitservice.command;

import com.google.gson.Gson;
import intelligent_pay.remitservice.dto.record.RecordRequest;
import intelligent_pay.remitservice.kafka.KafkaLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static intelligent_pay.remitservice.kafka.Topic.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecordProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    public void depositRecord(RecordRequest recordRequest) {
        String jsonOrder = gson.toJson(recordRequest);
        String topic = DEPOSIT_RECORD;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }

    public void withdrawRecord(RecordRequest recordRequest) {
        String jsonOrder = gson.toJson(recordRequest);
        String topic = WITHDRAW_RECORD;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }
}

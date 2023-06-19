package intelligent_pay.remitservice.producer.model;

import com.google.gson.Gson;
import intelligent_pay.remitservice.async.AsyncConstant;
import intelligent_pay.remitservice.dto.record.RecordRequest;
import intelligent_pay.remitservice.producer.log.ProducerLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static intelligent_pay.remitservice.producer.model.ProducerTopic.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class RemitProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    @Async(AsyncConstant.commandAsync)
    public void depositRecord(RecordRequest recordRequest) {
        String jsonOrder = gson.toJson(recordRequest);
        String topic = DEPOSIT_RECORD;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(ProducerLog.KAFKA_SEND_LOG.getValue() + topic);
    }

    @Async(AsyncConstant.commandAsync)
    public void withdrawRecord(RecordRequest recordRequest) {
        String jsonOrder = gson.toJson(recordRequest);
        String topic = WITHDRAW_RECORD;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(ProducerLog.KAFKA_SEND_LOG.getValue() + topic);
    }
}

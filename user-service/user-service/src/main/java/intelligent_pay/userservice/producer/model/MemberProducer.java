package intelligent_pay.userservice.producer.model;

import com.google.gson.Gson;
import intelligent_pay.userservice.async.AsyncConstant;
import intelligent_pay.userservice.producer.log.ProducerLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    @Async(AsyncConstant.commandAsync)
    public void removeBankbook(String username) {
        String jsonOrder = gson.toJson(username);
        String topic = ProducerTopic.REMOVE_BANKBOOK;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(ProducerLog.KAFKA_SEND_LOG.getValue() + topic);
    }
}

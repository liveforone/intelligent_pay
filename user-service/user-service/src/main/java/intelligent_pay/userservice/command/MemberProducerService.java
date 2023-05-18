package intelligent_pay.userservice.command;

import com.google.gson.Gson;
import intelligent_pay.userservice.async.AsyncConstant;
import intelligent_pay.userservice.kafka.CommandTopic;
import intelligent_pay.userservice.kafka.KafkaLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    @Async(AsyncConstant.commandAsync)
    public void removeBankbook(String username) {
        String jsonOrder = gson.toJson(username);
        String topic = CommandTopic.REMOVE_BANKBOOK;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }

    @Async(AsyncConstant.commandAsync)
    public void removeRecord(String username) {
        String jsonOrder = gson.toJson(username);
        String topic = CommandTopic.REMOVE_RECORD;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }
}

package intelligent_pay.bankbookservice.consumer.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import intelligent_pay.bankbookservice.consumer.log.ConsumerLog;
import intelligent_pay.bankbookservice.service.command.BankbookCommandService;
import intelligent_pay.bankbookservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BankbookConsumer {

    private final BankbookCommandService bankbookCommandService;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = ConsumerTopic.REMOVE_BANKBOOK_BELONG_USER)
    public void removeBankbook(String kafkaMessage) throws JsonProcessingException {
        log.info(ConsumerLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        String username = objectMapper.readValue(kafkaMessage, String.class);

        if (CommonUtils.isNull(username)) {
            log.info(ConsumerLog.KAFKA_NULL_LOG.getValue());
        } else {
            bankbookCommandService.removeBankbook(username);
            log.info(ConsumerLog.REMOVE_BANKBOOK_BELONG_USER.getValue() + username);
        }
    }
}

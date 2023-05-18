package intelligent_pay.bankbookservice.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import intelligent_pay.bankbookservice.async.AsyncConstant;
import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.kafka.KafkaLog;
import intelligent_pay.bankbookservice.kafka.QueryTopic;
import intelligent_pay.bankbookservice.repository.BankbookRepository;
import intelligent_pay.bankbookservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankbookConsumerService {

    private final BankbookRepository bankbookRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = QueryTopic.REMOVE_BANKBOOK_BELONG_USER)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void removeBankbook(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        String username = objectMapper.readValue(kafkaMessage, String.class);

        if (CommonUtils.isNull(username)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            Bankbook bankbook = bankbookRepository.findOneByUsername(username);
            bankbookRepository.delete(bankbook);
            log.info(KafkaLog.REMOVE_BANKBOOK_BELONG_USER.getValue() + username);
        }
    }
}

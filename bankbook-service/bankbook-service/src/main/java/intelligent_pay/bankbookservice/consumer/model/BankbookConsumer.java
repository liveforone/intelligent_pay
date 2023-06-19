package intelligent_pay.bankbookservice.consumer.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import intelligent_pay.bankbookservice.async.AsyncConstant;
import intelligent_pay.bankbookservice.controller.restResponse.ResponseMessage;
import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.exception.BankbookCustomException;
import intelligent_pay.bankbookservice.consumer.log.ConsumerLog;
import intelligent_pay.bankbookservice.repository.BankbookRepository;
import intelligent_pay.bankbookservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BankbookConsumer {

    private final BankbookRepository bankbookRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = ConsumerTopic.REMOVE_BANKBOOK_BELONG_USER)
    @Async(AsyncConstant.commandAsync)
    public void removeBankbook(String kafkaMessage) throws JsonProcessingException {
        log.info(ConsumerLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        String username = objectMapper.readValue(kafkaMessage, String.class);

        if (CommonUtils.isNull(username)) {
            log.info(ConsumerLog.KAFKA_NULL_LOG.getValue());
        } else {
            Bankbook bankbook = bankbookRepository.findOneByUsername(username)
                    .orElseThrow(() -> new BankbookCustomException(ResponseMessage.BANKBOOK_IS_NULL));
            bankbookRepository.delete(bankbook);
            log.info(ConsumerLog.REMOVE_BANKBOOK_BELONG_USER.getValue() + username);
        }
    }
}

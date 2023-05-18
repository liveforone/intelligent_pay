package intelligent_pay.bankbookservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaLog {

    KAFKA_RECEIVE_LOG("Consumer receive Kafka Message -> "),
    KAFKA_NULL_LOG("!! Kafka Message is Null !!"),
    REMOVE_BANKBOOK_BELONG_USER("Remove Bankbook Belong User | Username : ");

    private final String value;
}

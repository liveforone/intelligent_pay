package intelligent_pay.bankbookservice.consumer.log;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConsumerLog {

    KAFKA_RECEIVE_LOG("Consumer receive Kafka Message -> "),
    KAFKA_NULL_LOG("!! Kafka Message is Null !!"),
    REMOVE_BANKBOOK_BELONG_USER("Remove Bankbook Belong User | Username : ");

    private final String value;
}

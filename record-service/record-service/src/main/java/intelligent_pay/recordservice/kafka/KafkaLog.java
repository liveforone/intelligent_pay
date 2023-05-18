package intelligent_pay.recordservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaLog {

    KAFKA_RECEIVE_LOG("Consumer receive Kafka Message -> "),
    KAFKA_NULL_LOG("!! Kafka Message is Null !!"),
    REMOVE_RECORD_BELONG_USER("Remove Record Belong User | Username : ");

    private final String value;
}

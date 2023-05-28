package intelligent_pay.recordservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaLog {

    KAFKA_RECEIVE_LOG("Consumer receive Kafka Message -> "),
    KAFKA_NULL_LOG("!! Kafka Message is Null !!"),
    ADD_BALANCE("Add Balance | Bankbook Num : "),
    SUBTRACT_BALANCE("Subtract Balance | Bankbook Num : ");

    private final String value;
}

package intelligent_pay.recordservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaLog {

    KAFKA_RECEIVE_LOG("Consumer receive Kafka Message -> "),
    KAFKA_NULL_LOG("!! Kafka Message is Null !!"),
    DEPOSIT_RECORD("Deposit Record Success | Bankbook Num : "),
    WITHDRAW_RECORD("Withdraw Record Success | Bankbook Num : ");

    private final String value;
}

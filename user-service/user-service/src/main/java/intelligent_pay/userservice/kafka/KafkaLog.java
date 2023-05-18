package intelligent_pay.userservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaLog {

    KAFKA_SEND_LOG("Kafka send Success | Topic : ");

    private final String value;
}

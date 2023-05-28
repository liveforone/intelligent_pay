package intelligent_pay.payservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaLog {

    KAFKA_SEND_LOG("Kafka send Success | Topic : ");

    private final String value;
}

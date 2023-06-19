package intelligent_pay.payservice.producer.log;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProducerLog {

    KAFKA_SEND_LOG("Kafka send Success | Topic : ");

    private final String value;
}

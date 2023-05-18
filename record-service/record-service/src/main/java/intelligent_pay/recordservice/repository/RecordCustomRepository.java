package intelligent_pay.recordservice.repository;

import intelligent_pay.recordservice.domain.Record;

public interface RecordCustomRepository {

    Record findOneByUsername(String username);
}

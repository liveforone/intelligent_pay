package intelligent_pay.recordservice.repository;

import intelligent_pay.recordservice.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long>, RecordCustomRepository {
}

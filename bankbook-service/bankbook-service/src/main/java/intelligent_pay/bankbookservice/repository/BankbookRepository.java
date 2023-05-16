package intelligent_pay.bankbookservice.repository;

import intelligent_pay.bankbookservice.domain.Bankbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankbookRepository extends JpaRepository<Bankbook, Long>, BankbookCustomRepository {
}

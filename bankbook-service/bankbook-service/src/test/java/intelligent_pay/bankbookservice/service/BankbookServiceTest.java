package intelligent_pay.bankbookservice.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankbookServiceTest {

    @Autowired
    BankbookService bankbookService;

    @Autowired
    EntityManager em;

    @Test
    void createBankbook() {
    }

    @Test
    void addBalance() {
    }

    @Test
    void subtractBalance() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void suspendBankbook() {
    }

    @Test
    void cancelSuspendBankbook() {
    }
}
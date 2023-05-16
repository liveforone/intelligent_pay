package intelligent_pay.bankbookservice.service;

import intelligent_pay.bankbookservice.domain.BankbookState;
import intelligent_pay.bankbookservice.dto.BankbookRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BankbookServiceTest {

    @Autowired
    BankbookService bankbookService;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void createBankbookTest() {
        //given
        String password = "1111111111111111";
        String username = "onofwnofecaoeflefnaxxxxefeaowfewqfefa";
        BankbookRequest request = new BankbookRequest();
        request.setPassword(password);

        //when
        bankbookService.createBankbook(request, username);
        em.flush();
        em.clear();

        //then
        assertThat(bankbookService.getBankbookByUsername(username).getBankbookState())
                .isEqualTo(BankbookState.WORK);
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
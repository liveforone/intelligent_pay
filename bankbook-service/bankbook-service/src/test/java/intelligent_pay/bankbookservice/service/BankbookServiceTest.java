package intelligent_pay.bankbookservice.service;

import intelligent_pay.bankbookservice.domain.BankbookState;
import intelligent_pay.bankbookservice.dto.*;
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

    void createBankbook(String password, String username) {
        BankbookRequest request = new BankbookRequest();
        request.setPassword(password);
        bankbookService.createBankbook(request, username);
        em.flush();
        em.clear();
    }

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
    @Transactional
    void addBalanceTest() {
        //given
        String password = "2389238923892398";
        String username = "ncnanfpqenfoqnvapojfoqpnfoenvhgerahfdqpoeuropeq";
        createBankbook(password, username);

        //when
        BankbookResponse bankbook = bankbookService.getBankbookByUsername(username);
        AddBalanceRequest request = new AddBalanceRequest();
        request.setBankbookNum(bankbook.getBankbookNum());
        request.setMoney(10000);
        bankbookService.addBalance(request);
        em.flush();
        em.clear();

        //then
        assertThat(bankbookService.getBankbookByUsername(username).getBalance())
                .isEqualTo(10000L);
    }

    @Test
    @Transactional
    void subtractBalanceTest() {
        //given
        String password = "2389238923892398";
        String username = "ncnanfpqenfoqnvapojfoqpnfoenvhgerahfdqpoeuropeq";
        createBankbook(password, username);
        BankbookResponse bankbook = bankbookService.getBankbookByUsername(username);
        AddBalanceRequest addRequest = new AddBalanceRequest();
        addRequest.setBankbookNum(bankbook.getBankbookNum());
        addRequest.setMoney(10000);
        bankbookService.addBalance(addRequest);
        em.flush();
        em.clear();

        //when
        SubtractBalanceRequest request = new SubtractBalanceRequest();
        request.setBankbookNum(bankbook.getBankbookNum());
        request.setPassword(password);
        request.setMoney(5000L);
        bankbookService.subtractBalance(request);
        em.flush();
        em.clear();

        //then
        long resultMoney = 5000L;
        assertThat(bankbookService.getBankbookByUsername(username).getBalance())
                .isEqualTo(resultMoney);
    }

    @Test
    @Transactional
    void suspendBankbookTest() {
        //given
        String password = "2389238923892398";
        String username = "ncnanfpqenfoqnvapojfoqpnfoenvhgerahfdqpoeuropeq";
        createBankbook(password, username);

        //when
        BankbookResponse bankbook = bankbookService.getBankbookByUsername(username);
        bankbookService.suspendBankbook(bankbook.getBankbookNum());
        em.flush();
        em.clear();

        //then
        assertThat(bankbookService.getBankbookByUsername(username).getBankbookState())
                .isEqualTo(BankbookState.SUSPEND);
    }

    @Test
    void cancelSuspendBankbook() {
    }
}
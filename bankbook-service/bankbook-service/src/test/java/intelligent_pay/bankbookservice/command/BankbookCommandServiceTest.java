package intelligent_pay.bankbookservice.command;

import intelligent_pay.bankbookservice.domain.BankbookState;
import intelligent_pay.bankbookservice.dto.request.AddBalanceRequest;
import intelligent_pay.bankbookservice.dto.request.BankbookRequest;
import intelligent_pay.bankbookservice.dto.request.SubtractBalanceRequest;
import intelligent_pay.bankbookservice.dto.response.BankbookResponse;
import intelligent_pay.bankbookservice.query.BankbookQueryService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BankbookCommandServiceTest {

    @Autowired
    BankbookCommandService bankbookCommandService;

    @Autowired
    BankbookQueryService bankbookQueryService;

    @Autowired
    EntityManager em;

    void createBankbook(String password, String username) {
        BankbookRequest request = new BankbookRequest();
        request.setPassword(password);
        bankbookCommandService.createBankbook(request, username);
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
        bankbookCommandService.createBankbook(request, username);
        em.flush();
        em.clear();

        //then
        assertThat(bankbookQueryService.getBankbookByUsername(username).getBankbookState())
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
        BankbookResponse bankbook = bankbookQueryService.getBankbookByUsername(username);
        AddBalanceRequest request = new AddBalanceRequest();
        request.setBankbookNum(bankbook.getBankbookNum());
        request.setMoney(10000);
        bankbookCommandService.addBalance(request);
        em.flush();
        em.clear();

        //then
        assertThat(bankbookQueryService.getBankbookByUsername(username).getBalance())
                .isEqualTo(10000L);
    }

    @Test
    @Transactional
    void subtractBalanceTest() {
        //given
        String password = "2389238923892398";
        String username = "ncnanfpqenfoqnvapojfoqpnfoenvhgerahfdqpoeuropeq";
        createBankbook(password, username);
        BankbookResponse bankbook = bankbookQueryService.getBankbookByUsername(username);
        AddBalanceRequest addRequest = new AddBalanceRequest();
        addRequest.setBankbookNum(bankbook.getBankbookNum());
        addRequest.setMoney(10000);
        bankbookCommandService.addBalance(addRequest);
        em.flush();
        em.clear();

        //when
        SubtractBalanceRequest request = new SubtractBalanceRequest();
        request.setBankbookNum(bankbook.getBankbookNum());
        request.setPassword(password);
        request.setMoney(5000L);
        bankbookCommandService.subtractBalance(request);
        em.flush();
        em.clear();

        //then
        long resultMoney = 5000L;
        assertThat(bankbookQueryService.getBankbookByUsername(username).getBalance())
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
        BankbookResponse bankbook = bankbookQueryService.getBankbookByUsername(username);
        bankbookCommandService.suspendBankbook(bankbook.getBankbookNum());
        em.flush();
        em.clear();

        //then
        assertThat(bankbookQueryService.getBankbookByUsername(username).getBankbookState())
                .isEqualTo(BankbookState.SUSPEND);
    }

    @Test
    @Transactional
    void cancelSuspendBankbookTest() {
        //given
        String password = "2389238923892398";
        String username = "ncnanfpqenfoqnvapojfoqpnfoenvhgerahfdqpoeuropeq";
        createBankbook(password, username);
        BankbookResponse bankbook = bankbookQueryService.getBankbookByUsername(username);
        String bankbookNum = bankbook.getBankbookNum();
        bankbookCommandService.suspendBankbook(bankbookNum);
        em.flush();
        em.clear();

        //when
        bankbookCommandService.cancelSuspendBankbook(bankbookNum);
        em.flush();
        em.clear();

        //then
        assertThat(bankbookQueryService.getBankbookByUsername(username).getBankbookState())
                .isEqualTo(BankbookState.WORK);
    }

}
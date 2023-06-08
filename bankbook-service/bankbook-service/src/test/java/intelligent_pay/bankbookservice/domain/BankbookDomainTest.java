package intelligent_pay.bankbookservice.domain;

import intelligent_pay.bankbookservice.dto.request.BankbookRequest;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;

class BankbookDomainTest {

    @Test
    void addBalanceTest() {
        //given
        String password = "12345678";
        BankbookRequest request = new BankbookRequest();
        request.setPassword(password);
        String username = "jdsolafeoaefjoenfojaefoiejwfaeofjoaf";
        Bankbook bankbook = Bankbook.create(request, username);

        //when
        long money = 3000;
        bankbook.addBalance(money);

        //then
        assertThat(bankbook.getBalance())
                .isEqualTo(money);
    }

    @Test
    void subtractBalanceTest() {
        //given
        String username = "jdsolafeoaefjoenfojaefoiejwfaeofjoaf";
        String password = "12345678";
        long money = 3000;
        BankbookRequest request = new BankbookRequest();
        request.setPassword(password);
        Bankbook bankbook = Bankbook.create(request, username);
        bankbook.addBalance(money);

        //when
        long subtractMoney = 2000;
        bankbook.subtractBalance(subtractMoney);

        //then
        assertThat(bankbook.getBalance())
                .isEqualTo(money - subtractMoney);
    }

    @Test
    void updatePasswordTest() {
        //given
        String username = "jdsolafeoaefjoenfojaefoiejwfaeofjoaf";
        String password = "12345678";
        BankbookRequest request = new BankbookRequest();
        request.setPassword(password);
        Bankbook bankbook = Bankbook.create(request, username);

        //when
        String updatedPassword = "1111111111";
        bankbook.updatePassword(updatedPassword, password, username);

        //then
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertThat(encoder.matches(updatedPassword, bankbook.getPassword()))
                .isTrue();
    }

    @Test
    void suspendTest() {
        //given
        String username = "jdsolafeoaefjoenfojaefoiejwfaeofjoaf";
        String password = "12345678";
        BankbookRequest request = new BankbookRequest();
        request.setPassword(password);
        Bankbook bankbook = Bankbook.create(request, username);

        //when
        bankbook.suspend(password, username);

        //then
        assertThat(bankbook.getBankbookState())
                .isEqualTo(BankbookState.SUSPEND);
    }

    @Test
    void cancelSuspendTest() {
        //given
        String username = "jdsolafeoaefjoenfojaefoiejwfaeofjoaf";
        String password = "12345678";
        BankbookRequest request = new BankbookRequest();
        request.setPassword(password);
        Bankbook bankbook = Bankbook.create(request, username);
        bankbook.suspend(password, username);

        //when
        bankbook.cancelSuspend(password, username);

        //then
        assertThat(bankbook.getBankbookState())
                .isEqualTo(BankbookState.WORK);
    }
}
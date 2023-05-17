package intelligent_pay.bankbookservice.domain;

import intelligent_pay.bankbookservice.dto.BankbookRequest;
import org.junit.jupiter.api.Test;

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
    void overSubtractBalanceTest() {

    }

    @Test
    void updatePassword() {
    }

    @Test
    void suspend() {
    }

    @Test
    void cancelSuspend() {
    }
}
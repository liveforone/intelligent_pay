package intelligent_pay.recordservice.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class RecordTest {

    @Test
    void createDepositTest() {
        //given
        String bankbookNum = "8128139239821";
        long money = 4000;
        String title = "홍길동 입금";

        //when
        Record record = Record.createDeposit(title, bankbookNum, money);

        //then
        int nowYear = LocalDate.now().getYear();
        assertThat(record.getCreatedYear())
                .isEqualTo(nowYear);
        assertThat(record.getRecordState())
                .isEqualTo(RecordState.DEPOSIT);
        assertThat(record.getMoney())
                .isEqualTo(money);
    }

    @Test
    void createWithdrawTest() {
        //given
        String bankbookNum = "8128139239821";
        long money = 4000;
        String title = "홍길동 출금";

        //when
        Record record = Record.createWithdraw(title, bankbookNum, money);

        //then
        int nowYear = LocalDate.now().getYear();
        assertThat(record.getCreatedYear())
                .isEqualTo(nowYear);
        assertThat(record.getRecordState())
                .isEqualTo(RecordState.WITHDRAW);
        assertThat(record.getMoney())
                .isEqualTo(-money);
    }
}
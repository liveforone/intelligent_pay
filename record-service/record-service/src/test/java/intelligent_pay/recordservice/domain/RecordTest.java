package intelligent_pay.recordservice.domain;

import intelligent_pay.recordservice.dto.RecordRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class RecordTest {

    @Test
    void createTest() {
        //given
        String bankbookNum = "8128139239821";
        long money = 4000;
        String title = "홍길동 입금";
        RecordRequest request = new RecordRequest();
        request.setBankBookNum(bankbookNum);
        request.setMoney(money);
        request.setTitle(title);

        //when
        Record record = Record.create(request, RecordState.DEPOSIT);

        //then
        int nowYear = LocalDate.now().getYear();
        assertThat(record.getCreatedYear())
                .isEqualTo(nowYear);
        assertThat(record.getRecordState())
                .isEqualTo(RecordState.DEPOSIT);
        assertThat(record.getTitle())
                .isEqualTo(title);
    }
}
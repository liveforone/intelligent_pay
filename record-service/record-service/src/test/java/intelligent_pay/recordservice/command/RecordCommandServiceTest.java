package intelligent_pay.recordservice.command;

import intelligent_pay.recordservice.dto.RecordRequest;
import intelligent_pay.recordservice.query.RecordQueryService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class RecordCommandServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    RecordCommandService recordCommandService;

    @Autowired
    RecordQueryService recordQueryService;

    @Test
    @Transactional
    void createDepositRecordTest() {
        //given
        String bankbookNum = "8128139239821";
        long money = 4000;
        String title = "홍길동 입금";
        RecordRequest request = new RecordRequest();
        request.setBankBookNum(bankbookNum);
        request.setMoney(money);
        request.setTitle(title);

        //when
        Long depositRecordId = recordCommandService.createDepositRecord(request);
        em.flush();
        em.clear();

        //then
        assertThat(recordQueryService.getRecordById(depositRecordId).getId())
                .isEqualTo(depositRecordId);
    }

    @Test
    @Transactional
    void createWithdrawRecordTest() {
        //given
        String bankbookNum = "8128139239821";
        long money = 4000;
        String title = "홍길동 출금";
        RecordRequest request = new RecordRequest();
        request.setBankBookNum(bankbookNum);
        request.setMoney(money);
        request.setTitle(title);

        //when
        Long depositRecordId = recordCommandService.createWithdrawRecord(request);
        em.flush();
        em.clear();

        //then
        assertThat(recordQueryService.getRecordById(depositRecordId).getId())
                .isEqualTo(depositRecordId);
    }

    @Test
    void cancelState() {
    }
}
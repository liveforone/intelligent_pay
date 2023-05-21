package intelligent_pay.recordservice.domain;

import intelligent_pay.recordservice.dto.RecordRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Month;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String title;

    @Column(nullable = false, updatable = false)
    private String bankBookNum;

    @Column(updatable = false)
    private long money;

    @Enumerated(value = EnumType.STRING)
    private RecordState recordState;

    @Column(updatable = false)
    private int createdYear;

    @Enumerated(value = EnumType.STRING)
    @Column(updatable = false)
    private Month createdMonth;

    private Record(String title, String bankBookNum, long money, RecordState recordState) {
        this.title = title;
        this.bankBookNum = bankBookNum;
        this.money = money;
        this.recordState = recordState;
        this.createdYear = LocalDate.now().getYear();
        this.createdMonth = LocalDate.now().getMonth();
    }

    public static Record create(RecordRequest recordRequest, RecordState recordState) {
        return new Record(
                recordRequest.getTitle(),
                recordRequest.getBankBookNum(),
                recordRequest.getMoney(),
                recordState
        );
    }

    public void cancelState() {
        this.recordState = RecordState.CANCEL;
    }
}

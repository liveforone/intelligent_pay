package intelligent_pay.recordservice.domain;

import intelligent_pay.recordservice.converter.MonthConverter;
import intelligent_pay.recordservice.converter.RecordStateConverter;
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

    @Convert(converter = RecordStateConverter.class)
    private RecordState recordState;

    @Column(updatable = false)
    private int createdYear;

    @Convert(converter = MonthConverter.class)
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

    public static Record createDeposit(RecordRequest recordRequest) {
        return new Record(
                recordRequest.getTitle(),
                recordRequest.getBankBookNum(),
                recordRequest.getMoney(),
                RecordState.DEPOSIT
        );
    }

    public static Record createWithdraw(RecordRequest recordRequest) {
        return new Record(
                recordRequest.getTitle(),
                recordRequest.getBankBookNum(),
                -recordRequest.getMoney(),
                RecordState.WITHDRAW
        );
    }
}

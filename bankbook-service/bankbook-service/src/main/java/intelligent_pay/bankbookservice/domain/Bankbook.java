package intelligent_pay.bankbookservice.domain;

import intelligent_pay.bankbookservice.domain.util.PasswordUtil;
import intelligent_pay.bankbookservice.dto.BankbookRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

import static intelligent_pay.bankbookservice.domain.constant.EntityConstant.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Bankbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long balance;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String bankbookNum;

    @Column(unique = true, nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    private BankbookState bankbookState;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDate;

    private Bankbook(String password, String bankbookNum, String username) {
        this.balance = INITIAL_BALANCE;
        this.password = password;
        this.bankbookNum = bankbookNum;
        this.username = username;
        this.bankbookState = BankbookState.WORK;
    }

    public static Bankbook create(BankbookRequest bankbookRequest, String username) {
        String encodedPassword = PasswordUtil.encodePassword(bankbookRequest.getPassword());
        String bankbookNum = RandomStringUtils.randomNumeric(SIZE_OF_BANKBOOK_NUM);
        return new Bankbook(encodedPassword, bankbookNum, username);
    }

    public void addBalance(long inputMoney) {
        this.balance += inputMoney;
    }

    public void subtractBalance(long inputMoney) {
        if ((this.balance > ZERO) && (this.balance - inputMoney >= ZERO)) {
            this.balance -= inputMoney;
        }
    }
}

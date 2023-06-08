package intelligent_pay.bankbookservice.domain;

import intelligent_pay.bankbookservice.controller.restResponse.ResponseMessage;
import intelligent_pay.bankbookservice.converter.BankbookStateConverter;
import intelligent_pay.bankbookservice.domain.util.PasswordUtil;
import intelligent_pay.bankbookservice.dto.request.BankbookRequest;
import intelligent_pay.bankbookservice.exception.BankbookCustomException;
import intelligent_pay.bankbookservice.exception.returnBool.BankbookCustomBoolException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @Convert(converter = BankbookStateConverter.class)
    @Column(nullable = false)
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
        if (bankbookState == BankbookState.SUSPEND) {
            throw new BankbookCustomBoolException();
        }

        this.balance += inputMoney;
    }

    public void subtractBalance(long inputMoney) {
        if ((bankbookState == BankbookState.SUSPEND)
                || (balance == ZERO)
                || (balance - inputMoney < ZERO)
        ) {
            throw new BankbookCustomBoolException();
        }

        this.balance -= inputMoney;
    }

    public void updatePassword(String newPassword, String inputPassword, String inputUsername) {
        if (bankbookState == BankbookState.SUSPEND) {
            throw new BankbookCustomException(ResponseMessage.SUSPEND_BANKBOOK);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(inputPassword, password)) {
            throw new BankbookCustomException(ResponseMessage.PASSWORD_NOT_MATCH);
        }

        if (!username.equals(inputUsername)) {
            throw new BankbookCustomException(ResponseMessage.USERNAME_NOT_MATCH);
        }

        this.password = PasswordUtil.encodePassword(newPassword);
    }

    public void suspend(String inputPassword, String inputUsername) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(inputPassword, password)) {
            throw new BankbookCustomException(ResponseMessage.PASSWORD_NOT_MATCH);
        }

        if (!username.equals(inputUsername)) {
            throw new BankbookCustomException(ResponseMessage.USERNAME_NOT_MATCH);
        }

        this.bankbookState = BankbookState.SUSPEND;
    }

    public void cancelSuspend(String inputPassword, String inputUsername) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(inputPassword, password)) {
            throw new BankbookCustomException(ResponseMessage.PASSWORD_NOT_MATCH);
        }

        if (!username.equals(inputUsername)) {
            throw new BankbookCustomException(ResponseMessage.USERNAME_NOT_MATCH);
        }

        this.bankbookState = BankbookState.WORK;
    }
}

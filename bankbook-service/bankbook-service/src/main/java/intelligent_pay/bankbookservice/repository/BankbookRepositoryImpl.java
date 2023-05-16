package intelligent_pay.bankbookservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.domain.QBankbook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BankbookRepositoryImpl implements BankbookCustomRepository{

    private final JPAQueryFactory queryFactory;
    QBankbook bankbook = QBankbook.bankbook;

    public Long findIdByUsername(String username) {
        return queryFactory
                .select(bankbook.id)
                .from(bankbook)
                .where(bankbook.username.eq(username))
                .fetchOne();
    }

    public Long findIdByBankbookNum(String bankbookNum) {
        return queryFactory
                .select(bankbook.id)
                .from(bankbook)
                .where(bankbook.bankbookNum.eq(bankbookNum))
                .fetchOne();
    }

    public String findPasswordByBankbookNum(String bankbookNum) {
        return queryFactory
                .select(bankbook.password)
                .from(bankbook)
                .where(bankbook.bankbookNum.eq(bankbookNum))
                .fetchOne();
    }

    public Bankbook findOneByUsername(String username) {
        return queryFactory
                .selectFrom(bankbook)
                .where(bankbook.username.eq(username))
                .fetchOne();
    }

    public Bankbook findOneByBankbookNum(String bankbookNum) {
        return queryFactory
                .selectFrom(bankbook)
                .where(bankbook.bankbookNum.eq(bankbookNum))
                .fetchOne();
    }
}

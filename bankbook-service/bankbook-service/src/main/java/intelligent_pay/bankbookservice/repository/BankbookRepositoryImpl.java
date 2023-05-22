package intelligent_pay.bankbookservice.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.domain.QBankbook;
import intelligent_pay.bankbookservice.dto.response.BasicInfoResponse;
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

    public BasicInfoResponse findBasicInfoByUsername(String username) {
        return queryFactory
                .select(Projections.constructor(BasicInfoResponse.class,
                        bankbook.bankbookNum,
                        bankbook.balance)
                )
                .from(bankbook)
                .where(bankbook.username.eq(username))
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

package intelligent_pay.bankbookservice.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import intelligent_pay.bankbookservice.domain.Bankbook;
import intelligent_pay.bankbookservice.domain.QBankbook;
import intelligent_pay.bankbookservice.dto.response.BasicInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BankbookRepositoryImpl implements BankbookCustomRepository{

    private final JPAQueryFactory queryFactory;
    QBankbook bankbook = QBankbook.bankbook;

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

    public String checkBankbookByBankbookNum(String bankbookNum) {
        return queryFactory
                .select(bankbook.bankbookNum)
                .from(bankbook)
                .where(bankbook.bankbookNum.eq(bankbookNum))
                .fetchOne();
    }

    public Optional<Bankbook> findOneByUsername(String username) {
        return Optional.ofNullable(queryFactory
                .selectFrom(bankbook)
                .where(bankbook.username.eq(username))
                .fetchOne());
    }

    public Optional<Bankbook> findOneByBankbookNum(String bankbookNum) {
        return Optional.ofNullable(queryFactory
                .selectFrom(bankbook)
                .where(bankbook.bankbookNum.eq(bankbookNum))
                .fetchOne());
    }
}

package intelligent_pay.bankbookservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BankbookRepositoryImpl implements BankbookCustomRepository{

    private final JPAQueryFactory queryFactory;


}

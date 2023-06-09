package intelligent_pay.userservice.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import intelligent_pay.userservice.domain.Member;
import intelligent_pay.userservice.domain.QMember;
import intelligent_pay.userservice.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    QMember member = QMember.member;

    public Optional<Member> findByUsername(String username) {
        return Optional.ofNullable(queryFactory.selectFrom(member)
                .where(member.username.eq(username))
                .fetchOne());
    }

    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(queryFactory.selectFrom(member)
                .where(member.email.eq(email))
                .fetchOne());
    }

    public List<MemberResponse> searchMemberByEmail(String email) {
        return queryFactory
                .select(Projections.constructor(MemberResponse.class,
                        member.id,
                        member.email,
                        member.realName,
                        member.auth
                ))
                .from(member)
                .where(member.email.startsWith(email))
                .fetch();
    }
}

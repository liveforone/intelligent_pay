package intelligent_pay.userservice.repository;


import intelligent_pay.userservice.domain.Member;
import intelligent_pay.userservice.domain.Role;
import intelligent_pay.userservice.dto.response.MemberResponse;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);
    List<MemberResponse> searchMemberByEmail(String email);
}

package intelligent_pay.userservice.repository;


import intelligent_pay.userservice.domain.Member;
import intelligent_pay.userservice.dto.response.MemberResponse;

import java.util.List;

public interface MemberRepositoryCustom {

    Member findByUsername(String username);
    Member findByEmail(String email);
    List<MemberResponse> searchMemberByEmail(String email);
}

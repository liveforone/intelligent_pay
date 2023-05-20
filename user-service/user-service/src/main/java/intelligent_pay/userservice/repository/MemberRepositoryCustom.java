package intelligent_pay.userservice.repository;


import intelligent_pay.userservice.domain.Member;
import intelligent_pay.userservice.domain.Role;
import intelligent_pay.userservice.dto.response.MemberResponse;

import java.util.List;

public interface MemberRepositoryCustom {

    Role findAuthByUsername(String username);
    Long findIdByEmail(String email);
    String findPasswordByUsername(String username);
    Member findByUsername(String username);
    Member findByEmail(String email);
    List<MemberResponse> searchMemberByEmail(String email);
}

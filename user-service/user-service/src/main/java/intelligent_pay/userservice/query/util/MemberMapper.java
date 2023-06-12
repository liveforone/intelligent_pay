package intelligent_pay.userservice.query.util;

import intelligent_pay.userservice.domain.Member;
import intelligent_pay.userservice.dto.response.MemberResponse;

public class MemberMapper {

    public static MemberResponse entityToDto(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .realName(member.getRealName())
                .auth(member.getAuth())
                .build();
    }
}

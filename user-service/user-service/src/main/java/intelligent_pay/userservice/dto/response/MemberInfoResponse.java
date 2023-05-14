package intelligent_pay.userservice.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberInfoResponse {

    private MemberResponse memberResponse;
    private long balance;
}

package intelligent_pay.userservice.dto.response;

import intelligent_pay.userservice.dto.bankbook.BasicBankbookInfoResponse;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberInfoResponse {

    private MemberResponse memberResponse;
    private BasicBankbookInfoResponse basicBankbookInfoResponse;
}

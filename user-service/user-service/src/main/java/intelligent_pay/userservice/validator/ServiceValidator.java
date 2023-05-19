package intelligent_pay.userservice.validator;

import intelligent_pay.userservice.controller.restResponse.ResponseMessage;
import intelligent_pay.userservice.domain.Member;
import intelligent_pay.userservice.exception.MemberCustomException;
import intelligent_pay.userservice.repository.MemberRepository;
import intelligent_pay.userservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceValidator {

    private final MemberRepository memberRepository;

    public void validateDuplicateEmail(String email) {
        Member member = memberRepository.findByEmail(email);

        if (!CommonUtils.isNull(member)) {
            throw new MemberCustomException(ResponseMessage.DUPLICATE_EMAIL);
        }
    }
}

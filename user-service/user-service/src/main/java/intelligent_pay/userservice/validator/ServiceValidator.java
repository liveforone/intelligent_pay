package intelligent_pay.userservice.validator;

import intelligent_pay.userservice.controller.restResponse.ResponseMessage;
import intelligent_pay.userservice.domain.Member;
import intelligent_pay.userservice.exception.MemberCustomException;
import intelligent_pay.userservice.repository.MemberRepository;
import intelligent_pay.userservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceValidator {

    private final MemberRepository memberRepository;

    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void validateDuplicateEmail(String email) {
        Member member = memberRepository.findByEmail(email);

        if (!CommonUtils.isNull(member)) {
            throw new MemberCustomException(ResponseMessage.DUPLICATE_EMAIL);
        }
    }

    public void validatePassword(String inputPassword, String username) {
        Member foundMember = memberRepository.findByUsername(username);
        String originalPassword = foundMember.getPassword();

        if (!passwordEncoder.matches(inputPassword, originalPassword)) {
            throw new MemberCustomException(ResponseMessage.NOT_MATCH_PASSWORD);
        }
    }
}

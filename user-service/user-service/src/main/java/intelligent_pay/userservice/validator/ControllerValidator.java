package intelligent_pay.userservice.validator;

import intelligent_pay.userservice.controller.constant.ControllerLog;
import intelligent_pay.userservice.controller.restResponse.ResponseMessage;
import intelligent_pay.userservice.domain.Member;
import intelligent_pay.userservice.domain.Role;
import intelligent_pay.userservice.exception.BindingCustomException;
import intelligent_pay.userservice.exception.MemberCustomException;
import intelligent_pay.userservice.repository.MemberRepository;
import intelligent_pay.userservice.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class ControllerValidator {

    private final MemberRepository memberRepository;

    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void validatePassword(String inputPassword, String username) {
        Member foundMember = memberRepository.findByUsername(username);
        String originalPassword = foundMember.getPassword();

        if (!passwordEncoder.matches(inputPassword, originalPassword)) {
            throw new MemberCustomException(ResponseMessage.NOT_MATCH_PASSWORD);
        }
    }

    public void validateBinding(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects
                    .requireNonNull(bindingResult.getFieldError())
                    .getDefaultMessage();
            throw new BindingCustomException(errorMessage);
        }
    }

    public void validateAdmin(String username) {
        Member member = memberRepository.findByUsername(username);
        if (!member.getAuth().equals(Role.ADMIN)) {
            log.error(ControllerLog.ADMIN_FAIL.getValue());
            throw new MemberCustomException(ResponseMessage.PROHIBITION);
        }
    }
}

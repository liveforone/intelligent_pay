package intelligent_pay.userservice.command;

import intelligent_pay.userservice.domain.Member;
import intelligent_pay.userservice.dto.changeInfo.ChangeEmailRequest;
import intelligent_pay.userservice.dto.changeInfo.ChangePasswordRequest;
import intelligent_pay.userservice.dto.changeInfo.WithdrawRequest;
import intelligent_pay.userservice.dto.signupAndLogin.MemberLoginRequest;
import intelligent_pay.userservice.dto.signupAndLogin.MemberSignupRequest;
import intelligent_pay.userservice.jwt.JwtTokenProvider;
import intelligent_pay.userservice.jwt.TokenInfo;
import intelligent_pay.userservice.repository.MemberRepository;
import intelligent_pay.userservice.validator.ServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final ServiceValidator serviceValidator;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public String signup(MemberSignupRequest memberSignupRequest) {
        serviceValidator.validateDuplicateEmail(memberSignupRequest.getEmail());

        Member member = Member.create(memberSignupRequest);
        return memberRepository.save(member).getUsername();
    }

    public TokenInfo login(MemberLoginRequest memberLoginRequest) {
        String email = memberLoginRequest.getEmail();
        String password = memberLoginRequest.getPassword();

        Member member = memberRepository.findByEmail(email);
        String username = member.getUsername();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);

        return jwtTokenProvider
                .generateToken(authentication);
    }

    public void updateEmail(ChangeEmailRequest changeEmailRequest, String username) {
        String newEmail = changeEmailRequest.getEmail();
        serviceValidator.validateDuplicateEmail(newEmail);

        Member member = memberRepository.findByUsername(username);
        member.updateEmail(newEmail);
    }

    public void updatePassword(ChangePasswordRequest changePasswordRequest, String username) {
        serviceValidator.validatePassword(changePasswordRequest.getOldPassword(), username);

        Member member = memberRepository.findByUsername(username);
        member.updatePassword(changePasswordRequest.getNewPassword());
    }

    public void withdrawByUsername(WithdrawRequest withdrawRequest, String username) {
        serviceValidator.validatePassword(withdrawRequest.getPassword(), username);

        Member member = memberRepository.findByUsername(username);
        memberRepository.delete(member);
    }
}

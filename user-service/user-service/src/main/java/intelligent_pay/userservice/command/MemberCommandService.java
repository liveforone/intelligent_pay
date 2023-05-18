package intelligent_pay.userservice.command;

import intelligent_pay.userservice.async.AsyncConstant;
import intelligent_pay.userservice.domain.Member;
import intelligent_pay.userservice.dto.changeInfo.ChangeEmailRequest;
import intelligent_pay.userservice.dto.signupAndLogin.MemberLoginRequest;
import intelligent_pay.userservice.dto.signupAndLogin.MemberSignupRequest;
import intelligent_pay.userservice.jwt.JwtTokenProvider;
import intelligent_pay.userservice.jwt.TokenInfo;
import intelligent_pay.userservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
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
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String signup(MemberSignupRequest memberSignupRequest) {
        Member member = Member.create(memberSignupRequest);
        return memberRepository.save(member).getUsername();
    }

    @Transactional
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

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void updateEmail(ChangeEmailRequest changeEmailRequest, String username) {
        String newEmail = changeEmailRequest.getEmail();
        Member member = memberRepository.findByUsername(username);
        member.updateEmail(newEmail);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void updatePassword(String inputPassword, String username) {
        Member member = memberRepository.findByUsername(username);
        member.updatePassword(inputPassword);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void withdrawByUsername(String username) {
        memberRepository.deleteByUsername(username);
    }
}

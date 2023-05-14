package intelligent_pay.userservice.service;

import intelligent_pay.userservice.async.AsyncConstant;
import intelligent_pay.userservice.domain.Member;
import intelligent_pay.userservice.dto.changeInfo.ChangeEmailRequest;
import intelligent_pay.userservice.dto.response.MemberResponse;
import intelligent_pay.userservice.dto.signupAndLogin.MemberLoginRequest;
import intelligent_pay.userservice.dto.signupAndLogin.MemberSignupRequest;
import intelligent_pay.userservice.jwt.JwtTokenProvider;
import intelligent_pay.userservice.jwt.TokenInfo;
import intelligent_pay.userservice.repository.MemberRepository;
import intelligent_pay.userservice.service.util.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberResponse getMemberByUsername(String username) {
        return MemberMapper.entityToDto(memberRepository.findByUsername(username));
    }

    public List<MemberResponse> searchByEmail(String email) {
        return memberRepository.searchMemberByEmail(email);
    }

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

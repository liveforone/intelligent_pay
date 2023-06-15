package intelligent_pay.userservice.command;

import intelligent_pay.userservice.controller.restResponse.ResponseMessage;
import intelligent_pay.userservice.domain.Member;
import intelligent_pay.userservice.dto.changeInfo.ChangeEmailRequest;
import intelligent_pay.userservice.dto.changeInfo.ChangePasswordRequest;
import intelligent_pay.userservice.dto.signupAndLogin.MemberLoginRequest;
import intelligent_pay.userservice.dto.signupAndLogin.MemberSignupRequest;
import intelligent_pay.userservice.exception.MemberCustomException;
import intelligent_pay.userservice.jwt.JwtTokenProvider;
import intelligent_pay.userservice.jwt.TokenInfo;
import intelligent_pay.userservice.repository.MemberRepository;
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
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public String signup(MemberSignupRequest requestDto) {
        Member member = Member.create(
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getRealName()
        );
        return memberRepository.save(member).getUsername();
    }

    public TokenInfo login(MemberLoginRequest requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberCustomException(ResponseMessage.MEMBER_IS_NULL));
        String username = member.getUsername();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);

        return jwtTokenProvider
                .generateToken(authentication);
    }

    public void updateEmail(ChangeEmailRequest requestDto, String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberCustomException(ResponseMessage.MEMBER_IS_NULL));
        member.updateEmail(requestDto.getEmail());
    }

    public void updatePassword(ChangePasswordRequest requestDto, String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberCustomException(ResponseMessage.MEMBER_IS_NULL));
        member.updatePassword(requestDto.getNewPassword(), requestDto.getOldPassword());
    }

    public void withdrawByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberCustomException(ResponseMessage.MEMBER_IS_NULL));

        if (!username.equals(member.getUsername())) {
            throw new MemberCustomException(ResponseMessage.USERNAME_NOT_MATCH);
        }

        memberRepository.delete(member);
    }
}

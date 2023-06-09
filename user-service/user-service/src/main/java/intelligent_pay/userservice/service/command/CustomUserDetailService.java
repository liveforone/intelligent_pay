package intelligent_pay.userservice.service.command;

import intelligent_pay.userservice.controller.restResponse.ResponseMessage;
import intelligent_pay.userservice.domain.Member;
import intelligent_pay.userservice.domain.Role;
import intelligent_pay.userservice.exception.MemberCustomException;
import intelligent_pay.userservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return createUserDetails(memberRepository.findByUsername(email)
                .orElseThrow(() -> new MemberCustomException(ResponseMessage.MEMBER_IS_NULL)));
    }

    private UserDetails createUserDetails(Member member) {
        if (member.getAuth() == Role.ADMIN) {
            String ADMIN_ROLE = "ADMIN";
            return User.builder()
                    .username(member.getUsername())
                    .password(member.getPassword())
                    .roles(ADMIN_ROLE)
                    .build();
        } else {
            String MEMBER_ROLE = "MEMBER";
            return User.builder()
                    .username(member.getUsername())
                    .password(member.getPassword())
                    .roles(MEMBER_ROLE)
                    .build();
        }
    }
}

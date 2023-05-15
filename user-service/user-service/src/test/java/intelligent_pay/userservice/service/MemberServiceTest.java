package intelligent_pay.userservice.service;

import intelligent_pay.userservice.dto.changeInfo.ChangeEmailRequest;
import intelligent_pay.userservice.dto.signupAndLogin.MemberLoginRequest;
import intelligent_pay.userservice.dto.signupAndLogin.MemberSignupRequest;
import intelligent_pay.userservice.jwt.TokenInfo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    String createMember(String email, String password, String realName) {
        MemberSignupRequest memberSignupRequest = new MemberSignupRequest();
        memberSignupRequest.setEmail(email);
        memberSignupRequest.setPassword(password);
        memberSignupRequest.setRealName(realName);
        return memberService.signup(memberSignupRequest);
    }

    @Test
    @Transactional
    void signupTest() {
        //given
        String email = "member1111@gmail.com";
        String password = "1111";
        String realName = "test_member";
        MemberSignupRequest memberSignupRequest = new MemberSignupRequest();
        memberSignupRequest.setEmail(email);
        memberSignupRequest.setPassword(password);
        memberSignupRequest.setRealName(realName);

        //when
        String username = memberService.signup(memberSignupRequest);
        em.flush();
        em.clear();

        //then
        assertThat(memberService.getMemberByUsername(username).getRealName())
                .isEqualTo(realName);
    }

    @Test
    @Transactional
    void updateEmailTest() {
        //given
        String email = "abcd1234@gmail.com";
        String password = "1234";
        String realName = "test_signup";
        String username = createMember(email, password, realName);
        em.flush();
        em.clear();

        //when
        String updatedEmail = "zzzz1111@gmail.com";
        ChangeEmailRequest request = new ChangeEmailRequest();
        request.setEmail(updatedEmail);
        memberService.updateEmail(request, username);
        em.flush();
        em.clear();

        //then
        assertThat(memberService.getMemberByUsername(username).getEmail())
                .isEqualTo(updatedEmail);

    }

    @Test
    @Transactional
    void updatePasswordTest() {
        //given
        String email = "password112423@gmail.com";
        String password = "1234";
        String realName = "test_password";
        String username = createMember(email, password, realName);
        em.flush();
        em.clear();

        //when
        String updatedPassword = "9999";
        memberService.updatePassword(updatedPassword, username);
        em.flush();
        em.clear();

        //then
        MemberLoginRequest request = new MemberLoginRequest();
        request.setEmail(email);
        request.setPassword(updatedPassword);
        TokenInfo token = memberService.login(request);
        assertThat(token).isNotNull();
    }
}
package intelligent_pay.userservice.domain;

import intelligent_pay.userservice.dto.signupAndLogin.MemberSignupRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;

class MemberDomainTest {

    @Test
    void createAdminTest() {
        //given
        String email = "admin@intelligentpay.com";
        String realName = "admin";
        String password = "111111111111";
        MemberSignupRequest request = new MemberSignupRequest();
        request.setEmail(email);
        request.setRealName(realName);
        request.setPassword(password);

        //when
        Member member = Member.create(request);

        //then
        assertThat(member.getAuth())
                .isEqualTo(Role.ADMIN);
    }

    @Test
    void updateEmailTest() {
        //given
        String email = "test@gmail.com";
        String realName = "test_name";
        String password = "12345678";
        MemberSignupRequest request = new MemberSignupRequest();
        request.setEmail(email);
        request.setRealName(realName);
        request.setPassword(password);
        Member member = Member.create(request);

        //when
        String newEmail = "test_new@naver.com";
        member.updateEmail(newEmail);

        //then
        assertThat(member.getEmail())
                .isEqualTo(newEmail);
    }

    @Test
    void updatePasswordTest() {
        //given
        String email = "test@gmail.com";
        String realName = "test_name";
        String password = "12345678";
        MemberSignupRequest request = new MemberSignupRequest();
        request.setEmail(email);
        request.setRealName(realName);
        request.setPassword(password);
        Member member = Member.create(request);

        //when
        String newPassword = "9999999999";
        member.updatePassword(newPassword);

        //then
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertThat(encoder.matches(newPassword, member.getPassword()))
                .isTrue();
    }
}
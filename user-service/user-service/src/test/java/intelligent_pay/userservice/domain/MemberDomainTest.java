package intelligent_pay.userservice.domain;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;

class MemberDomainTest {

    @Test
    void createAdminTest() {
        //given
        String email = "admin@intelligentpay.com";
        String password = "111111111111";
        String realName = "admin";

        //when
        Member member = Member.create(email, password, realName);

        //then
        assertThat(member.getAuth())
                .isEqualTo(Role.ADMIN);
    }

    @Test
    void updateEmailTest() {
        //given
        String email = "test@gmail.com";
        String password = "12345678";
        String realName = "test_name";
        Member member = Member.create(email, password, realName);

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
        String password = "12345678";
        String realName = "test_name";
        Member member = Member.create(email, password, realName);

        //when
        String newPassword = "9999999999";
        member.updatePassword(newPassword, password);

        //then
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertThat(encoder.matches(newPassword, member.getPassword()))
                .isTrue();
    }
}
package intelligent_pay.userservice.domain;

import intelligent_pay.userservice.dto.signupAndLogin.MemberSignupRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
    void updateEmail() {
    }

    @Test
    void updatePassword() {
    }
}
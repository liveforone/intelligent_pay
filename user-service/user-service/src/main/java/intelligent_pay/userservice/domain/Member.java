package intelligent_pay.userservice.domain;

import intelligent_pay.userservice.controller.restResponse.ResponseMessage;
import intelligent_pay.userservice.converter.RoleConverter;
import intelligent_pay.userservice.domain.util.MemberConstant;
import intelligent_pay.userservice.domain.util.PasswordUtils;
import intelligent_pay.userservice.exception.MemberCustomException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private String realName;

    @Convert(converter = RoleConverter.class)
    private Role auth;

    private Member(String username, String email, String password, String realName, Role auth) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.realName = realName;
        this.auth = auth;
    }

    public static Member create(String email, String password, String realName) {
        final String ADMIN = "admin@intelligentpay.com";

        return new Member(
                createUsername(),
                email,
                PasswordUtils.encodePassword(password),
                realName,
                ADMIN.equals(email) ? Role.ADMIN : Role.MEMBER
        );
    }

    private static String createUsername() {
        return UUID.randomUUID() + RandomStringUtils.randomAlphabetic(MemberConstant.RANDOM_STRING_LENGTH);
    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }

    public void updatePassword(String newPassword, String originalPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(originalPassword, password)) {
            throw new MemberCustomException(ResponseMessage.NOT_MATCH_PASSWORD);
        }

        this.password = PasswordUtils.encodePassword(newPassword);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(auth.getAuth()));
        return authList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

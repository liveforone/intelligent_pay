# 회원 서비스

## 회원 서비스 소개
* 회원 정보관리를 담당하는 서비스 입니다.
* Member 엔티티와 inteliigent_pay_user DB가 회원 서비스에 속해있습니다.

## 회원관리 기술
* 서버의 성능을 중요시 하는 전체적인 정책때문에 회원 관리 또한 세션처럼 서버에서 부담을 주는 방식이 아닌,
* Jwt 토큰을 활용해 stateless 하게 서버에 부담을 주지 않는 방식을 채택하였습니다.
* 토큰관리는 가장 보편적인 방식인 프론트엔드의 로컬스토리지에서 jwt토큰을 관리하는것을 전제로 합니다.
* 토큰의 만료시간은 2시간입니다.

## 상세 요구사항
* 회원은 일반회원(MEMBER)과 관리자(ADMIN)로 총 두 종류가 있다.
* 모든 회원은 uuid기반인 username으로 식별한다.
* username은 uuid + 문자4개의 형태이다. uuid의 중복을 완전히 없애기 위해 뒤에 문자를 붙였다.
* jwt에는 회원의 권한(role)과 username(식별자)가 있다.
* 회원가입시 이메일/비밀번호와 함께 실명도 입력받는다.
* 회원은 이메일과 비밀번호의 변경이 가능해야한다.
* 어드민 페이지는 어드민만 접근 가능하며 모든 회원을 검색할 수 있다.
* 회원은 마이페이지에서 내 정보를 볼 수 있다.
* 회원은 탈퇴가 가능하다.

## API 설계
```
[GET] / : 홈(토큰 불필요)
[GET/POST] /signup : 회원가입(토큰 불필요), MemberRequest 형식 필요
[GET/POST] /login : 로그인(토큰 불필요)
[GET] /logout : 로그아웃, get으로 받아도 정상 작동(토큰 불필요)
[GET] /my-info : 마이페이지(토큰 필요)
[PATCH] /change/email : 이메일 변경(토큰 필요), ChangeEmailRequest 형식
[PATCH] /change/password : 비밀번호 변경(토큰 필요), ChangePasswordRequest 형식
[DELETE] /withdraw : 회원탈퇴(토큰 필요), text 형식 문자열 비밀번호 필요
[GET] /admin : 어드민 페이지(토큰 필요)
[GET] /prohibition : 403 페이지(토큰 불필요)
```

## Json body 예시
```
[일반 유저]
{
    "email" : "yc1234@gmail.com",
    "password" : "1234",
    "realName" : "chan"
}

[어드민]
{
    "email" : "admin@intelligentpay.com",
    "password" : "1234",
    "realName" : "admin"
}

[이메일 변경]
{
    "email" : "yc1111@gmail.com"
}

[비밀번호 변경]
{
    "oldPassword" : "12345678
    "newPassword" : "13579345"
}
```

## 서비스간 통신
### 잔액 요청
* 회원 정보 조회시 잔액을 계좌서비스에 요청하여 받아온다.
```
[GET] : /balance/{username}
```

## 어드민 회원가입
* 어드민 회원가입은 일반 회원 가입과 사뭇다릅니다.
* 어드민은 지정된 id(email)로 가입해야합니다.
* 비밀번호는 어드민이 정하고, 또 바꿀 수 있지만 첫 가입시는 반드시 지정된 이메일을 사용해야합니다.
* 이것을 통해서 어드민인지 파악하고 권한을 매핑하기 때문입니다.
* 이번 프로젝트에서 지정된 어드민 이메일은 admin@intelligentpay.com입니다.
* 이메일은 비밀번호와 마찬가지로 회원가입 후 변경이 가능합니다.
package intelligent_pay.userservice.jwt.constant;

public final class JwtConstant {

    private JwtConstant() {}

    public static final String ACCESS_TOKEN = "access-token";
    public static final String REFRESH_TOKEN = "refresh-token";
    public static final String HEADER = "Authorization";
    public static final String CLAIM_NAME = "auth";
    public static final int TWO_HOUR_MS = 7200000;
    public static final String BEARER_TOKEN = "Bearer";
    public static final String NO_AUTH_INFO_TOKEN_MESSAGE = "권한 정보가 없는 토큰입니다.";
    public static final String INVALID_MESSAGE = "Invalid JWT Token";
    public static final String EXPIRED_MESSAGE = "Expired JWT Token";
    public static final String UNSUPPORTED_MESSAGE = "Unsupported JWT Token";
    public static final String EMPTY_MESSAGE = "JWT claims string is empty.";
    public static final String SECRET_KEY_PATH = "${jwt.secret}";
    public static final int TOKEN_SUB_INDEX = 7;
}

package intelligent_pay.bankbookservice.authentication.constant;

public final class JwtConstant {
    private JwtConstant() {}
    public static final String HEADER = "Authorization";
    public static final String CLAIM_NAME = "auth";
    public static final String BEARER_TOKEN = "Bearer";
    public static final String SECRET_KEY_PATH = "${jwt.secret}";
    public static final int TOKEN_SUB_INDEX = 7;
}

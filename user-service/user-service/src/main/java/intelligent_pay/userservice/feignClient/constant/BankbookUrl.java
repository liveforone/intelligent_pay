package intelligent_pay.userservice.feignClient.constant;

public final class BankbookUrl {
    private BankbookUrl() {}

    public static final String BASE = "bankbook-service";
    public static final String BASIC_INFO = "/basic/info/{username}";
}
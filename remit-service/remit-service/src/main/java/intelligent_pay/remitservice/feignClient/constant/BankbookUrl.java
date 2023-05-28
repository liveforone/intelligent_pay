package intelligent_pay.remitservice.feignClient.constant;

public final class BankbookUrl {
    private BankbookUrl() {}

    public static final String BANKBOOK_BASE = "bankbook-service";
    public static final String ADD_BALANCE = "/provide/add/balance";
    public static final String SUBTRACT_BALANCE = "/provide/subtract/balance";
}

package intelligent_pay.payservice.feignClient.constant;

public final class BankbookUrl {
    private BankbookUrl() {}

    public static final String BASE = "bankbook-service";
    public static final String ADD_BALANCE = "/provide/add/balance";
    public static final String SUBTRACT_BALANCE = "/provide/subtract/balance";
    public static final String SUBTRACT_BALANCE_FOR_CANCEL = "/provide/subtract/balance/cancel";
}

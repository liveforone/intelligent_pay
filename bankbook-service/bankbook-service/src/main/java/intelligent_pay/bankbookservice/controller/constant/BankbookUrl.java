package intelligent_pay.bankbookservice.controller.constant;

public final class BankbookUrl {
    private BankbookUrl() {}

    public static final String BASIC_INFO = "/basic/info/{username}";
    public static final String INFO = "/info";
    public static final String CREATE = "/create";
    public static final String ADD_BALANCE = "/provide/add/balance";
    public static final String SUBTRACT_BALANCE = "/provide/subtract/balance";
    public static final String SUBTRACT_BALANCE_FOR_CANCEL = "/provide/subtract/balance/cancel";
    public static final String UPDATE_PASSWORD = "/update/password";
    public static final String SUSPEND = "/suspend";
    public static final String CANCEL_SUSPEND = "/cancel/suspend";
}

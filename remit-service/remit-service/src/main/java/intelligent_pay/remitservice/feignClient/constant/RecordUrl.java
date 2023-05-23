package intelligent_pay.remitservice.feignClient.constant;

public final class RecordUrl {
    private RecordUrl() {}

    public static final String BASE = "record-service";
    public static final String DEPOSIT_RECORD = "/provide/record/deposit";
    public static final String WITHDRAW_RECORD = "/provide/record/withdraw";
}

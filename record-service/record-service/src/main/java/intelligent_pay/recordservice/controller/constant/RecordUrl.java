package intelligent_pay.recordservice.controller.constant;

public final class RecordUrl {
    private RecordUrl() {}

    public static final String RECORD_DETAIL = "/record/info/{id}";
    public static final String RECORD_HOME = "/record/{bankbookNum}";
    public static final String DEPOSIT_RECORD = "/record/deposit/info/{bankbookNum}";
}

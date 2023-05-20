package intelligent_pay.recordservice.controller.constant;

public final class RecordUrl {
    private RecordUrl() {}

    public static final String RECORD_DETAIL = "/record/info/{id}";
    public static final String RECORD_HOME = "/record/{bankbookNum}";
    public static final String DEPOSIT_RECORD = "/record/deposit/info/{bankbookNum}";
    public static final String WITHDRAW_RECORD = "/record/withdraw/info/{bankbookNum}";
    public static final String SEARCH_YEAR = "/record/search/year/{bankbookNum}";
    public static final String SEARCH_MONTH = "/record/search/month/{bankbookNum}";
    public static final String SEARCH_TITLE = "/record/search/title/{bankbookNum}";
}

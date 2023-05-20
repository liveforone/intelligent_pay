package intelligent_pay.recordservice.controller.constant;

import java.time.LocalDate;

public final class RecordParam {
    private RecordParam() {}

    public static final String ID = "id";
    public static final String BANKBOOK_NUM = "bankbookNum";
    public static final String LAST_ID = "lastId";
    public static final String DEFAULT_ID = "0";
    public static final String YEAR = "year";
    public static final String DEFAULT_YEAR = String.valueOf(LocalDate.now().getYear());
}

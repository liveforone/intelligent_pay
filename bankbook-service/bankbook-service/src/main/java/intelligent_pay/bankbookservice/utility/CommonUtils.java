package intelligent_pay.bankbookservice.utility;

import java.util.List;

public class CommonUtils {

    public static boolean isNull(Object obj) {

        //일반 객체 체크
        if(obj == null) {
            return true;
        }

        //문자열 체크
        if ((obj instanceof String) && (((String)obj).trim().length() == 0)) {
            return true;
        }

        //리스트 체크
        if (obj instanceof List) {
            return ((List<?>)obj).isEmpty();
        }

        return false;
    }
}

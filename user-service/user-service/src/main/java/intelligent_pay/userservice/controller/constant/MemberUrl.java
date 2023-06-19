package intelligent_pay.userservice.controller.constant;

public final class MemberUrl {

    private MemberUrl() {}

    //==GET==//
    public static final String MY_INFO = "/my-info";
    public static final String ADMIN_SEARCH = "/admin/search";
    public static final String PROHIBITION = "/prohibition";
    public static final String ACTUATOR = "/actuator/**";

    //==POST==//
    public static final String SIGNUP = "/signup";
    public static final String LOGIN = "/login";

    //==PUT==//
    public static final String CHANGE_EMAIL = "/change/email";
    public static final String CHANGE_PASSWORD = "/change/password";

    //==DELETE==//
    public static final String WITHDRAW = "/withdraw";
}

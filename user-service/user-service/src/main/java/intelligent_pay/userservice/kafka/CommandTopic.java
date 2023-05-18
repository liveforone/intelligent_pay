package intelligent_pay.userservice.kafka;

public final class CommandTopic {

    private CommandTopic() {}

    public static final String REMOVE_BANKBOOK = "remove-bankbook-belong-user";
    public static final String REMOVE_RECORD = "remove-record-belong-user";
}
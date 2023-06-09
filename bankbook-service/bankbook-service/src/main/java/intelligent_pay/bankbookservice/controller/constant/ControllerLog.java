package intelligent_pay.bankbookservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    CREATE_BANKBOOK_SUCCESS("Create Bankbook Success"),
    ADD_BALANCE_SUCCESS("Add Balance Success | BankbookNum : "),
    SUBTRACT_BALANCE_SUCCESS("Subtract Balance Success | BankbookNum : "),
    UPDATE_PASSWORD_SUCCESS("Update password Success | BankbookNum : "),
    SUSPEND_SUCCESS("Suspend Bankbook Success | BankbookNum : "),
    CANCEL_SUSPEND_SUCCESS("Suspend Bankbook Success | BankbookNum : ");

    private final String value;
}

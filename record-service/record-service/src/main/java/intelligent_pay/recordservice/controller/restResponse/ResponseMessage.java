package intelligent_pay.recordservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    CREATE_DEPOSIT_SUCCESS(201, "입금 거래내역을 성공적으로 생성했습니다."),
    CREATE_WITHDRAW_SUCCESS(201, "출금 거래내역을 성공적으로 생성했습니다.");

    private final int status;
    private final String value;
}

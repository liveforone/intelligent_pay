package intelligent_pay.remitservice.feignClient;

import intelligent_pay.remitservice.dto.bankbook.AddBalanceRequest;
import intelligent_pay.remitservice.dto.bankbook.SubtractBalanceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static intelligent_pay.remitservice.feignClient.constant.BankbookUrl.*;

@FeignClient(name = BANKBOOK_BASE)
public interface BankbookClient {

    @PostMapping(ADD_BALANCE)
    boolean addBalance(@RequestBody AddBalanceRequest requestDto);

    @PostMapping(SUBTRACT_BALANCE)
    boolean subtractBalance(@RequestBody SubtractBalanceRequest requestDto);
}

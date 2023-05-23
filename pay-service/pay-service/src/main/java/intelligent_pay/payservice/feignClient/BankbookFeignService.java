package intelligent_pay.payservice.feignClient;

import intelligent_pay.payservice.dto.bankbook.AddBalanceRequest;
import intelligent_pay.payservice.dto.bankbook.SubtractBalanceForCancel;
import intelligent_pay.payservice.dto.bankbook.SubtractBalanceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static intelligent_pay.payservice.feignClient.constant.BankbookUrl.*;

@FeignClient(name = BASE)
public interface BankbookFeignService {

    @PostMapping(ADD_BALANCE)
    boolean addBalance(@RequestBody AddBalanceRequest requestDto);

    @PostMapping(SUBTRACT_BALANCE)
    boolean subtractBalance(@RequestBody SubtractBalanceRequest requestDto);

    @PostMapping(SUBTRACT_BALANCE_FOR_CANCEL)
    boolean subtractBalanceForCancel(@RequestBody SubtractBalanceForCancel requestDto);
}

package intelligent_pay.userservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import static intelligent_pay.userservice.feignClient.constant.BankbookUrl.*;

@FeignClient(BASE)
public interface BankbookFeignService {

    @GetMapping(MY_BALANCE)
    long getBalanceByUsername();
}

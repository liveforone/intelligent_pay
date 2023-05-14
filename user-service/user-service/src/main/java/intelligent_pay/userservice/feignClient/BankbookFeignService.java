package intelligent_pay.userservice.feignClient;

import intelligent_pay.userservice.feignClient.constant.BankbookParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static intelligent_pay.userservice.feignClient.constant.BankbookUrl.*;

@FeignClient(BASE)
public interface BankbookFeignService {

    @GetMapping(MY_BALANCE)
    long getBalanceByUsername(@PathVariable(BankbookParam.USERNAME) String username);
}
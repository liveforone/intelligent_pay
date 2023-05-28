package intelligent_pay.userservice.feignClient;

import intelligent_pay.userservice.dto.bankbook.BasicBankbookInfoResponse;
import intelligent_pay.userservice.feignClient.constant.BankbookParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static intelligent_pay.userservice.feignClient.constant.BankbookUrl.*;

@FeignClient(Bankbook_BASE)
public interface BankbookClient {

    @GetMapping(BASIC_INFO)
    BasicBankbookInfoResponse getBasicInfoByUsername(@PathVariable(BankbookParam.USERNAME) String username);
}

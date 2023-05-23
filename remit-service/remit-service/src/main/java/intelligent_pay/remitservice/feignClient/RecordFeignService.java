package intelligent_pay.remitservice.feignClient;

import intelligent_pay.remitservice.dto.record.RecordRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static intelligent_pay.remitservice.feignClient.constant.RecordUrl.*;

@FeignClient(name = BASE)
public interface RecordFeignService {

    @PostMapping(DEPOSIT_RECORD)
    boolean depositRecord(@RequestBody RecordRequest requestDto);

    @PostMapping(WITHDRAW_RECORD)
    boolean withdrawRecord(@RequestBody RecordRequest requestDto);
}

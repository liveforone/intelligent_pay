package intelligent_pay.gatewayservice.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(FallbackUrl.BASE)
public class FallbackController {

    @GetMapping(FallbackUrl.USER)
    public Mono<String> fallbackUserGet() {
        return Mono.just(FallbackMessage.USER_LOG);
    }

    @PostMapping(FallbackUrl.USER)
    public Mono<String> fallbackUserPost() {
        return Mono.just(FallbackMessage.USER_LOG);
    }

    @PutMapping(FallbackUrl.USER)
    public Mono<String> fallbackUserPatch() {
        return Mono.just(FallbackMessage.USER_LOG);
    }

    @DeleteMapping(FallbackUrl.USER)
    public Mono<String> fallbackUserDelete() {
        return Mono.just(FallbackMessage.USER_LOG);
    }

    @GetMapping(FallbackUrl.BANKBOOK)
    public Mono<String> fallbackBankbookGet() {
        return Mono.just(FallbackMessage.BANKBOOK_LOG);
    }

    @PostMapping(FallbackUrl.BANKBOOK)
    public Mono<String> fallbackBankbookPost() {
        return Mono.just(FallbackMessage.BANKBOOK_LOG);
    }

    @PutMapping(FallbackUrl.BANKBOOK)
    public Mono<String> fallbackBankbookPut() {
        return Mono.just(FallbackMessage.BANKBOOK_LOG);
    }

    @GetMapping(FallbackUrl.RECORD)
    public Mono<String> fallbackRecordGet() {
        return Mono.just(FallbackMessage.RECORD_LOG);
    }

    @PostMapping(FallbackUrl.RECORD)
    public Mono<String> fallbackRecordPost() {
        return Mono.just(FallbackMessage.RECORD_LOG);
    }

    @PutMapping(FallbackUrl.RECORD)
    public Mono<String> fallbackRecordPut() {
        return Mono.just(FallbackMessage.RECORD_LOG);
    }

    @PostMapping(FallbackUrl.REMIT)
    public Mono<String> fallbackRemitPost() {
        return Mono.just(FallbackMessage.REMIT_LOG);
    }

    @PostMapping(FallbackUrl.PAY)
    public Mono<String> fallbackPayPost() {
        return Mono.just(FallbackMessage.PAY_LOG);
    }
}

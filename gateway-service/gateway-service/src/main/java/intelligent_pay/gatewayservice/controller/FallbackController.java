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
}

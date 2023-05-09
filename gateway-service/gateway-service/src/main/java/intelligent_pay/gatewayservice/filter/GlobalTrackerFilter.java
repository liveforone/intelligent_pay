package intelligent_pay.gatewayservice.filter;

import intelligent_pay.gatewayservice.filter.constant.GlobalTrackerLog;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalTrackerFilter extends AbstractGatewayFilterFactory<GlobalTrackerFilter.Config> {

    public GlobalTrackerFilter() {
        super(Config.class);
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info(GlobalTrackerLog.BASE_LOG.getValue(), config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info(GlobalTrackerLog.START_LOG.getValue(), request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                        if (config.isPostLogger()) {
                            log.info(GlobalTrackerLog.END_LOG.getValue(), response.getStatusCode());
                        }
                    })
            );
        };
    }
}
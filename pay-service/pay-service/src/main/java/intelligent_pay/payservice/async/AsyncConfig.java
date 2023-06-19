package intelligent_pay.payservice.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(AsyncConstant.commandAsync)
    public Executor commandAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(AsyncConstant.CORE_POOL_SIZE);
        executor.setMaxPoolSize(AsyncConstant.MAX_POOL_SIZE);
        executor.setQueueCapacity(AsyncConstant.QUEUE_CAPACITY);
        executor.setThreadNamePrefix(AsyncConstant.commandAsync);
        executor.initialize();
        return executor;
    }
}

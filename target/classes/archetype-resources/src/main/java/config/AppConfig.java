package ${groupId}.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AppConfig {

    @Bean
    @Autowired
    public Executor asyncExecutor(ExecutorCfg executorCfg) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(executorCfg.getCorePoolSize());
        executor.setMaxPoolSize(executorCfg.getMaxPoolSize());
        executor.setQueueCapacity(executorCfg.getQueueCapacity());
        executor.setThreadNamePrefix(executorCfg.getThreadNamePrefix());
        executor.initialize();
        return executor;
    }
}

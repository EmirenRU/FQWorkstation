package ru.emiren.email.Config;

import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Objects;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig {

    @Autowired
    private Environment env;

    @Bean("asyncTaskExecutor")
    public Executor asyncTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Integer.parseInt(Objects.requireNonNull(env.getProperty("number.of.threads"))));
        executor.setQueueCapacity(150);
        executor.setMaxPoolSize(Integer.parseInt(Objects.requireNonNull(env.getProperty("number.of.threads"))));
        executor.setThreadNamePrefix("Async-Executor-");
        executor.initialize();
        return executor;
    }
}

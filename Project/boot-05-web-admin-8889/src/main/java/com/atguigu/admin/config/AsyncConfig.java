package com.atguigu.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 */
@Configuration
@Slf4j
public class AsyncConfig implements AsyncConfigurer {
    // 核心线程数为服务器的cpu核心数
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    // 线程池中允许的最大线程数
    private static int maxPoolSize = 2 * corePoolSize + 1;
    // 工作队列大小
    private static int queueCapacity = 5000;

    @Override
    @Bean("getAsyncExecutor")
    public Executor getAsyncExecutor() {
        log.info("核心线程数: "+ corePoolSize + " 最大线程数: " + maxPoolSize);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(corePoolSize);
        //最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //队列大小
        executor.setQueueCapacity(queueCapacity);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("QueryHealthKit-");
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}

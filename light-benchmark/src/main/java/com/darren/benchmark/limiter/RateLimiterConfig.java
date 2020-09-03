package com.darren.benchmark.limiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-04-26 18:23
 * Desc   :  *
 */
public class RateLimiterConfig {
    private static volatile RateLimiterConfig rateLimiterConfig; //单例
    private static Logger logger = LoggerFactory.getLogger(RateLimiterConfig.class);

    private ScheduledExecutorService scheduledThreadExecutor; //调度线程池

    private RateLimiterConfig() {
        //禁止new实例
    }

    public static RateLimiterConfig getInstance() {
        if (rateLimiterConfig == null) {
            synchronized (RateLimiterConfig.class) {
                if (rateLimiterConfig == null) {
                    rateLimiterConfig = new RateLimiterConfig();
                    logger.info("Starting [RateLimiter]");
                }
            }
        }
        return rateLimiterConfig;
    }

    public ScheduledExecutorService getScheduledThreadExecutor() {
        if (this.scheduledThreadExecutor == null) {
            synchronized (this) {
                if (this.scheduledThreadExecutor == null) {
                    setScheduledThreadExecutor(new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2, new ThreadPoolExecutor.DiscardOldestPolicy()));
                }
            }
        }
        return this.scheduledThreadExecutor;
    }

    private void setScheduledThreadExecutor(ScheduledExecutorService scheduledThreadExecutor) {
        this.scheduledThreadExecutor = scheduledThreadExecutor;
    }


}

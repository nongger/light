package com.darren.benchmark.limiter;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-04-26 15:15
 * Desc   : 本地限流器
 */
public interface RateLimiter {


    void init(RateLimiterRule rule);

    boolean acquire();

    boolean tryAcquire();

    String getId();

    RateLimiterRule getRule();
}

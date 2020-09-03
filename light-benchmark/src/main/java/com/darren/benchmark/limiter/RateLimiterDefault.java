package com.darren.benchmark.limiter;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-04-26 15:16
 * Desc   :  *
 */
public class RateLimiterDefault implements RateLimiter {
    private final AtomicLong bucket = new AtomicLong(0); //令牌桶初始容量：0
    private RateLimiterRule rule;
    private RateLimiterConfig config;
    private ScheduledFuture<?> scheduledFuture;

    public static RateLimiter of(RateLimiterRule rule) {
        return new RateLimiterDefault(rule, RateLimiterConfig.getInstance());
    }

    private RateLimiterDefault(RateLimiterRule rule, RateLimiterConfig config) {
        this.config = config;
        init(rule);
    }

    @Override
    public void init(RateLimiterRule rule) {
        this.rule = rule;
        putPointBucket();
    }

    /**
     * 2.Check
     */
    @Override
    public boolean acquire() {
        if (rule.isEnable()) {
            //限流功能已关闭
            return true;
        }
        return tryAcquireBlock();
    }

    @Override
    public boolean tryAcquire() {
        if (rule.isEnable()) {
            //限流功能已关闭
            return true;
        }
        return tryAcquireFailed();
    }

    /**
     * CAS获取令牌,没有令牌立即失败
     */
    private boolean tryAcquireFailed() {
        long l = bucket.longValue();
        while (l > 0) {
            if (bucket.compareAndSet(l, l - 1)) {
                return true;
            }
            l = bucket.longValue();
        }
        return false;
    }

    /**
     * CAS获取令牌,阻塞直到成功
     */
    private boolean tryAcquireBlock() {
        long l = bucket.longValue();
        while (!(l > 0 && bucket.compareAndSet(l, l - 1))) {
            sleep();
            l = bucket.longValue();
        }
        return true;
    }

    /**
     * 线程休眠
     */
    private void sleep() {
        //大于1ms强制休眠
        if (rule.getUnit().toMillis(rule.getPeriod()) < 1) {
            return;
        }
        try {
            Thread.sleep(rule.getUnit().toMillis(rule.getPeriod()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 本地限流，放入令牌
     */
    private void putPointBucket() {
        if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(true);
        }

        // 指定间隔后重置令牌桶
        this.scheduledFuture = config.getScheduledThreadExecutor()
                .scheduleAtFixedRate(() -> bucket.set(rule.getLimit()), rule.getInitialDelay(), rule.getPeriod(), rule.getUnit());
    }

    @Override
    public String getId() {
        return rule.getId();
    }

    @Override
    public RateLimiterRule getRule() {
        return this.rule;
    }


}

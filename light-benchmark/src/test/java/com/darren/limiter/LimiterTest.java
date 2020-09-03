package com.darren.limiter;

import com.darren.benchmark.limiter.RateLimiter;
import com.darren.benchmark.limiter.RateLimiterDefault;
import com.darren.benchmark.limiter.RateLimiterRule;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-04-26 15:42
 * Desc   : 自制限流器的使用
 */
public class LimiterTest {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Test
    public void test1() {
        List<List<String>> partition = Lists.partition(new ArrayList<String>(),10);

        // 1.配置规则
        RateLimiterRule rateLimiterRule = new RateLimiterRule()
                .setId("concurrent-replay")
                .setLimit(1)
                .setPeriod(1)
                .setInitialDelay(0)
                .setUnit(TimeUnit.SECONDS); //每秒令牌数为1
        // 2.工厂模式生产限流器
        RateLimiter limiter = RateLimiterDefault.of(rateLimiterRule);
        // 3.使用
        int i = 1;
        while (true) {

            if (limiter.tryAcquire()) {
                logger.info("ok");
                i++;
            }
            // 改变限流策略
            if (i == 10) {
                logger.info("speed up");
                limiter.getRule().setLimit(2);
                i = 0;
            }

        }
    }
}

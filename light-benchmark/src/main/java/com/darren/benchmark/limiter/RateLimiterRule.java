package com.darren.benchmark.limiter;

import java.util.concurrent.TimeUnit;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-04-26 15:24
 * Desc   :
 */
public class RateLimiterRule implements Comparable<RateLimiterRule> {
    // APP
    /**
     * app name
     */
    private String app = "Application";
    /**
     * 限流规则名称
     */
    private String id = "id";
    /**
     * 相同的限流规则，不同的实例标识(不需要用户配置)
     */
    private String name;

    /**
     * 是否关闭限流功能
     */
    private boolean enable;

    //QPS
    /**
     * 单位时间存放的令牌数
     */
    private volatile long limit;
    /**
     * 单位时间大小
     */
    private long period = 1;
    /**
     * 第一次放入令牌的延迟时间
     */
    private long initialDelay = 0;
    /**
     * 时间单位
     */
    private TimeUnit unit = TimeUnit.SECONDS;

    //get bucket
    /**
     * 每批次取多少个令牌
     */
    private long batch = 1;
    /**
     * 现有令牌数/批次令牌数<=? [0,1]
     */
    private double remaining = 0.5;

    //Monitor
    /**
     * 监控时长，秒，0为关闭
     */
    private long monitor = 10;

    //System
    /**
     * APP-ID实例数(不需要用户配置)
     */
    private int number;
    /**
     * 版本号(不需要用户配置)
     */
    private long version;

    public String getApp() {
        return app;
    }

    public RateLimiterRule setApp(String app) {
        this.app = app;
        return this;
    }

    public String getId() {
        return id;
    }

    public RateLimiterRule setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RateLimiterRule setName(String name) {
        this.name = name;
        return this;
    }

    public long getLimit() {
        return limit;
    }

    public RateLimiterRule setLimit(long limit) {
        this.limit = limit;
        return this;
    }

    public long getPeriod() {
        return period;
    }

    public RateLimiterRule setPeriod(long period) {
        this.period = period;
        return this;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public RateLimiterRule setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
        return this;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public RateLimiterRule setUnit(TimeUnit unit) {
        this.unit = unit;
        return this;
    }

    public double getRemaining() {
        return remaining;
    }

    public RateLimiterRule setRemaining(double remaining) {
        this.remaining = remaining;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public RateLimiterRule setNumber(int number) {
        this.number = number;
        return this;
    }

    public long getVersion() {
        return version;
    }

    public RateLimiterRule setVersion(long version) {
        this.version = version;
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    public RateLimiterRule setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }


    @Override
    public int compareTo(RateLimiterRule o) {
        if (this.version < o.getVersion()) {
            return -1;
        } else if (this.version == o.getVersion()) {
            return 0;
        }
        return 1;
    }

}

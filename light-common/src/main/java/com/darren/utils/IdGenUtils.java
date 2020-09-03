package com.darren.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

import static java.lang.Math.abs;

public class IdGenUtils {
    private static Logger log = LoggerFactory.getLogger(IdGenUtils.class);

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;

    private static long twepoch = 1288834974657L;

    private static long workerIdBits = 5L;
    private static long datacenterIdBits = 5L;
    private static long maxWorkerId = -1L ^ (-1L << (int) workerIdBits);
    private static long maxDatacenterId = -1L ^ (-1L << (int) datacenterIdBits);
    private static long sequenceBits = 12L;

    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask = -1L ^ (-1L << (int) sequenceBits);

    private long lastTimestamp = -1L;

    private static volatile IdGenUtils idGenUtils = null;

    private IdGenUtils(final long workerId) {
        this(workerId, 0);
    }

    public static IdGenUtils getInstance() {
        Integer hostId = 1;

        try {
            hostId = abs(InetAddress.getLocalHost().getHostAddress().hashCode());
        } catch (Exception e) {
            log.warn("init id gen utils failed");
        }

        return IdGenUtils.getInstance(hostId);
    }

    public static IdGenUtils getInstance(final long workerId) {
        if (idGenUtils == null) {
            synchronized (IdGenUtils.class) {
                if (idGenUtils == null) {
                    idGenUtils = new IdGenUtils(workerId);
                }
            }
            return idGenUtils;
        } else {
            return idGenUtils;
        }
    }

    public static IdGenUtils hasInstance() {
        return idGenUtils;
    }


    private IdGenUtils(long workerId, long datacenterId) {
        this.workerId = abs(workerId % maxWorkerId);
        this.datacenterId = abs(datacenterId % maxDatacenterId);
    }

    public synchronized String nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;
        return String.valueOf(((timestamp - twepoch) << (int) timestampLeftShift) | (datacenterId << (int) datacenterIdShift) | (workerId << (int) workerIdShift) | sequence);

    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
}

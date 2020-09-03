package com.darren.zookeeper;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-03-29 17:28
 * Desc   : 模拟服务的注册发现
 */
public class DemoServer {
    private Logger logger = LoggerFactory.getLogger(DemoServer.class);

    private String connectString = "127.0.0.1:2181";
    private int sessionTimeout = 2000;
    ZooKeeper zooKeeper = null;


    public static void main(String[] args) throws UnknownHostException {
        DemoServer demoServer = new DemoServer();
        // 创建连接
        demoServer.getConnect();
        // 服务注册
        demoServer.regist(InetAddress.getLocalHost().toString());
        // 其他业务处理功能
        demoServer.business(InetAddress.getLocalHost().toString());

    }

    private void regist(String connectString) {
        try {
            // 创建临时节点
            String s = zooKeeper.create("/servers/server", connectString.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            logger.info("服务注册节点：{},服务地址：{}", s, connectString);
        } catch (Exception e) {
            logger.error("服务注册异常", e);
        }
    }

    private ZooKeeper getConnect() {
        try {
            if (zooKeeper == null) {
                zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        // 监听程序处理流程
                    }
                });
            }
        } catch (Exception e) {
            logger.error("ZK连接初始化异常", e);
        }

        return zooKeeper;
    }

    // 业务功能
    public void business(String hostname) {
        try {
            System.out.println(hostname + " is working ...");

            Thread.sleep(Long.MAX_VALUE);
        } catch (Exception e) {
            logger.error("ZK服务处理异常", e);
        }
    }


}

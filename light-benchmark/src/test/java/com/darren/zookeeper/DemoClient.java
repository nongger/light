package com.darren.zookeeper;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-03-29 18:08
 * Desc   : 模拟服务的注册发现
 */
public class DemoClient {

    private Logger logger = LoggerFactory.getLogger(DemoServer.class);

    private String connectString = "127.0.0.1:2181";
    private int sessionTimeout = 2000;
    private String parentNode = "/servers";
    ZooKeeper zooKeeper = null;


    public static void main(String[] args) {
        DemoClient demoServer = new DemoClient();
        // 创建连接
        demoServer.getConnect();
        // 服务注册
        demoServer.listen();
        // 其他业务处理功能
        demoServer.business();

    }

    private void listen() {
        try {
            ArrayList<String> hosts = new ArrayList<>();
            List<String> children = zooKeeper.getChildren(parentNode, true);
            for (String node : children) {
                byte[] data = zooKeeper.getData(parentNode + "/" + node, false, null);
                hosts.add(new String(data));
            }
            logger.info("监听到服务提供者的节点：{}", StringUtils.join(hosts, ","));

        } catch (Exception e) {
            logger.error("服务监听异常", e);
        }
    }

    private ZooKeeper getConnect() {
        try {
            if (zooKeeper == null) {
                zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        // 监听程序处理流程
                        listen();
                    }
                });
            }
        } catch (Exception e) {
            logger.error("ZK连接初始化异常", e);
        }

        return zooKeeper;
    }

    // 业务功能
    private void business() {
        try {
            System.out.println("client is working ...");

            Thread.sleep(Long.MAX_VALUE);
        } catch (Exception e) {
            logger.error("ZK服务处理异常", e);
        }
    }
}

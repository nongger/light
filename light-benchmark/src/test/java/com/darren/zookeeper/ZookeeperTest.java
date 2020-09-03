package com.darren.zookeeper;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-03-28 17:30
 * Desc   : zk基本API尝试
 */
public class ZookeeperTest {
    private Logger logger = LoggerFactory.getLogger(ZookeeperTest.class);

    private String connectString = "127.0.0.1:2181";
    private int sessionTimeout = 2000;
    ZooKeeper zooKeeper = null;

    @Before
    public void init() {

        try {
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    // 监听程序处理流程
//                    try {
//                        // 收到事件通知后的回调函数（用户的业务逻辑）
//                        List<String> children = zooKeeper.getChildren("/", true);
//                        logger.info("监听到回调事件：{},路径：{}", watchedEvent.getType(), watchedEvent.getPath());
//                        for (String node : children) {
//                            logger.info(node);
//                        }
//                    } catch (Exception e) {
//                        logger.error("获取ZK节点异常", e);
//                    }
                }
            });
        } catch (Exception e) {
            logger.error("ZK连接初始化异常", e);
        }


    }


    @Test
    public void createZnode() {
        try {
            String create = zooKeeper.create("/liujingwei", "helloworld".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.info(create);
        } catch (Exception e) {
            logger.error("创建ZK节点异常", e);
        }
    }

    @Test
    public void getChildren() {
        try {
            List<String> children = zooKeeper.getChildren("/", true);
            logger.info("获取根节点：{}", StringUtils.join(children, ","));

            // 延时阻塞,测试监听事件
            Thread.sleep(Long.MAX_VALUE);
        } catch (Exception e) {
            logger.error("获取ZK节点异常", e);
        }
    }

    @Test
    public void isExist() {
        try {
            Stat stat = zooKeeper.exists("/liujingwei", false);
            logger.info("节点是否存在：{}", stat != null);
        } catch (Exception e) {
            logger.error("判断ZK节点是否存在发生异常", e);
        }

    }

}

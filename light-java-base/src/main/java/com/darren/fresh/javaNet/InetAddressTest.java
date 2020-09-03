package com.darren.fresh.javaNet;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Darren
 * @date 2018/4/19
 */
public class InetAddressTest {

    /**
     * InetAddress用于代表一个IP地址
     */
    @Test
    public void inetAddressTest() {
        try {
            InetAddress address = InetAddress.getByName("www.baidu.com");
            System.out.println(address.getHostName());//域名
            System.out.println(address.getHostAddress());//IP

            System.out.println("=====================");
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println(localHost.getHostName());
            System.out.println(localHost.getHostAddress());

        } catch (UnknownHostException e) {

        }

    }

}

package com.darren;

/**
 * @author Darren
 * @date 2018/6/14 16:28
 */
public class IdGenerator {


    // 获取主域名或IP
    private String getIpOrDomain(String address) {
        if (null == address || "".equals(address)) {
            return null;
        }
        System.out.println(address.indexOf("//"));
        if (address.contains("//")) {
            address = address.substring(address.indexOf("//") + 2);
        }
        if (address.contains("?")) {
            address = address.substring(0, address.indexOf("?"));
        }
        if (address.contains(":")) {
            address = address.substring(0, address.indexOf(":"));
        }
        if (address.contains("/")) {
            address = address.substring(0, address.indexOf("/"));
        }
        return address;
    }

}

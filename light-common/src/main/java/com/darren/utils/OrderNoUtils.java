package com.darren.utils;

import com.darren.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Project: light
 * Author : Darren
 * Time   : 2017/7/24
 * Desc   : 订单号工具类
 */

@Component
public class OrderNoUtils {

    //@Resource(type = RedisServiceImpl.class)
    @Autowired
    private RedisService redisService;

    //后期改成配置
    //@Value("${id.gen.prefix}")
    private String redisPrefix="22";

    public String genOrderNo(String firstValue) {
        IdGenUtils idGenUtils = IdGenUtils.hasInstance();
        if (idGenUtils == null) {
            Long workId = redisService.increment(redisPrefix, 1L);
            idGenUtils = IdGenUtils.getInstance(workId);
        }
        return firstValue + idGenUtils.nextId();
    }

    //public static void main(String[] args) {
    //    IdGenUtils idGenUtils = IdGenUtils.hasInstance();
    //    if (idGenUtils == null) {
    //        //Long workId = redisService.increment(redisPrefix, 1L);
    //        idGenUtils = IdGenUtils.getInstance(11L);
    //    }
    //
    //    System.out.println("1110" + idGenUtils.nextId());
    //    //int times = 100000;
    //    //Map<String,String> map = new HashMap<>();
    //    //int count = 0;
    //    //while(times-- > 0) {
    //    //    String orderNo = genOrderNo("1");
    //    //    if (map.containsKey(orderNo)) {
    //    //        count++;
    //    //        System.out.println("重复" + orderNo);
    //    //    }else{
    //    //        System.out.println(orderNo);
    //    //        map.put(orderNo,orderNo);
    //    //    }
    //    //}
    //    //System.out.println(count);
    //}
}

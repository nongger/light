package com.darren;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Project: light
 * Time   : 2021-02-09 20:41
 * Author : liujingwei05
 * Version: v1.0
 * Desc   : 枚举使用示例
 */
public enum TypeStatusEnum {
    RUNNING(0, "开始运行");


    private int status;
    private String desc;

    TypeStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    // 构建内部静态映射，提高检索性能
    private static Map<Integer, TypeStatusEnum> enumMap = new HashMap<>();

    static {
        Arrays.stream(TypeStatusEnum.values())
                .forEach(status -> enumMap.put(status.status, status));
    }

    public static TypeStatusEnum of(Integer status) {
        return enumMap.get(status);
    }
}

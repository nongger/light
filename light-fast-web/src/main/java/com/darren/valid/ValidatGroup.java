package com.darren.valid;

import javax.validation.groups.Default;

/**
 * Project: light
 * Time   : 2021-02-08 21:38
 * Author : liujingwei05
 * Version: v1.0
 * Desc   : 分组校验
 * 同一个对象要复用,比如UserDTO在更新时候要校验userId,
 * 在保存的时候不需要校验userId,在两种情况下都要校验username,那就用上groups了
 * <p>
 * 在DTO中的字段上定义好groups = {}的分组类型
 * <p>
 * 注意: 在声明分组的时候尽量加上 extend javax.validation.groups.Default否则,
 * 在你声明@Validated(Update.class)的时候,就会出现你在默认没添加groups = {}的时候的校验组
 *
 * @Email(message = "邮箱格式不对"),会不去校验,因为默认的校验组是groups = {Default.class}.
 */
public interface ValidatGroup {

    interface Create extends Default {
    }

    interface Update extends Default {
    }
}

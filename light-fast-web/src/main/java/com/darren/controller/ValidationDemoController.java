package com.darren.controller;

import com.darren.base.ResWrapperVO;
import com.darren.model.UserDTO;
import com.darren.valid.ValidatGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project: light
 * Time   : 2021-02-07 21:15
 * Author : Eric
 * Version: v1.0
 * Desc   : 校验器demo
 * JSR303 是一套JavaBean参数校验的标准，它定义了很多常用的校验注解，
 * 我们可以直接将这些注解加在我们JavaBean的属性上面(面向注解编程的时代)，就可以在需要校验的时候进行校验了.
 * <p>
 * https://mp.weixin.qq.com/s/N1R3nKg12VURhzkBLk2qHA
 */
@Slf4j
@RestController
@RequestMapping("/valid")
public class ValidationDemoController {

    @PostMapping("/save")
    public ResWrapperVO<UserDTO> save(@RequestBody @Validated(ValidatGroup.Create.class) UserDTO user) {
        log.info("注解测试验证：{}", user);

        return new ResWrapperVO().success(user);
    }

    @RequestMapping("/get")
    public String get(@Validated(ValidatGroup.Create.class) String name) {
        return "hello" + name;
    }

    @RequestMapping("/check")
    public String alive() {
        return "hello";
    }


}

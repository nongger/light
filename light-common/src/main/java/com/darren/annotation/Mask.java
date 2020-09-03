package com.darren.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 脱敏
 * Created by Darren
 * on 2018/6/5.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Mask {
    /**
     * 前置不需要脱敏的长度
     * @return
     */
    int prefixNoMaskLen() default 0;

    /**
     * 后置不需要脱敏的长度
     * @return
     */
    int suffixNoMaskLen() default 0;

    /**
     * 脱敏符号
     * @return
     */
    String maskStr() default "*";
}

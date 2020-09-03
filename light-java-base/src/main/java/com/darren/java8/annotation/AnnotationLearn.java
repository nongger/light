package com.darren.java8.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Darren
 * @date 2018/7/25 23:18
 */

public class AnnotationLearn {
    @SuppressWarnings({"unused"})
    public void say() {

    }

    @MinAnnotation("hello")
    public void show() {

    }
}


@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
@interface MinAnnotation {
    String value() default "Darren";

}

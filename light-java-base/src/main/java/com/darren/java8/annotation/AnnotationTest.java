package com.darren.java8.annotation;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author Darren
 * @date 2018/4/23
 */
public class AnnotationTest {

    @Test
    public void annotationT() {
        Class<AnnotationTest> clzz = AnnotationTest.class;
        try {
            Method show = clzz.getMethod("show");
            MyAnnotation[] annotationsByType = show.getAnnotationsByType(MyAnnotation.class);
            for (MyAnnotation myAnnotation : annotationsByType) {
                System.out.println(myAnnotation.value());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重复注解练习
     */
    @MyAnnotation("caroline")
    @MyAnnotation("love")
    @MyAnnotation
    public void show() {

    }

}

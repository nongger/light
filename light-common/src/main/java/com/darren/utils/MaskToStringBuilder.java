package com.darren.utils;

import com.darren.annotation.Mask;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

/**
 * Created by Darren
 * on 2018/6/5.
 */
public class MaskToStringBuilder {


    private MaskToStringBuilder() {
    }

    /**
     * 通过反射构建<b>toString</b>值
     *
     * @param object 需要进行"toString"的对象
     * @return
     */
    public static String toString(final Object object) {
        return toString(object, null);
    }


    /**
     * 通过反射构建<b>toString</b>值
     *
     * @param object 需要进行"toString"的对象
     * @param style  "toString"的样式
     * @return
     */
    public static String toString(final Object object, final ToStringStyle style) {
        return new ReflectionMaskToStringBuilder(object, style).toString();
    }

    /**
     * 通过反射构建<b>toString</b>值，并忽略排除字段
     *
     * @param object            需要进行"toString"的对象
     * @param excludeFieldNames 需要进行排除的字段
     * @return
     */
    public static String toStringExclude(final Object object, String... excludeFieldNames) {
        return new ReflectionMaskToStringBuilder(object).setExcludeFieldNames(excludeFieldNames).toString();
    }

    /**
     * 基于Mask重构的ReflectionToStringBuilder
     *
     * @version 1.0 2017.06.08
     */
    private static class ReflectionMaskToStringBuilder extends ReflectionToStringBuilder {

        /**
         * Instantiates a new Mask to string builder.
         *
         * @param object the object
         */
        public ReflectionMaskToStringBuilder(Object object) {
            super(object);
        }

        /**
         * Instantiates a new Mask to string builder.
         *
         * @param object the object
         * @param style  the style
         */
        public ReflectionMaskToStringBuilder(Object object, ToStringStyle style) {
            super(object, style);
        }


        @Override
        protected void appendFieldsIn(Class<?> clazz) {
            if (clazz.isArray()) {
                this.reflectionAppendArray(this.getObject());
                return;
            }
            final Field[] fields = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for (final Field field : fields) {
                final String fieldName = field.getName();
                if (this.accept(field)) {
                    try {
                        // Warning: Field.get(Object) creates wrappers objects
                        // for primitive types.
                        Object fieldValue = this.getValue(field);
                        // 脱敏处理
                        fieldValue = maskField(field, fieldValue);
                        this.append(fieldName, fieldValue);
                    } catch (final IllegalAccessException ex) {
                        //this can't happen. Would get a Security exception
                        // instead
                        //throw a runtime exception in case the impossible
                        // happens.
                        throw new InternalError("Unexpected IllegalAccessException: " + ex.getMessage());
                    }
                }
            }
        }


        /**
         * 字段脱敏
         *
         * @param field
         * @param fieldValue
         */
        private Object maskField(Field field, Object fieldValue) {
            if (fieldValue != null
                    && String.class.isAssignableFrom(fieldValue.getClass())
                    && field.isAnnotationPresent(Mask.class)) {
                Mask mask = field.getDeclaredAnnotation(Mask.class);

                String fieldValueStr = (String) fieldValue;

                //字段长度
                int length = fieldValueStr.length();

                //脱敏符号
                String overlay = mask.maskStr();

                //前置不需要脱敏的长度
                int prefixNoMaskLen = mask.prefixNoMaskLen();
                if (prefixNoMaskLen < 0) prefixNoMaskLen = 0;
                //后置不需要脱敏的长度
                int suffixNoMaskLen = mask.suffixNoMaskLen();
                if (suffixNoMaskLen < 0) suffixNoMaskLen = 0;

                //需要脱敏的长度
                int deltaLen = length - prefixNoMaskLen - suffixNoMaskLen;
                if (deltaLen < 0) deltaLen = 0;

                //脱敏处理
                return StringUtils.overlay(
                        (String) fieldValue,
                        StringUtils.repeat(overlay, deltaLen),
                        prefixNoMaskLen > 0 ? prefixNoMaskLen : 0,
                        prefixNoMaskLen > 0 ? prefixNoMaskLen + deltaLen : deltaLen
                );
            }
            return fieldValue;
        }


    }


}

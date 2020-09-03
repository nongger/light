package com.darren;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * 敏感信息脱敏处理
 *
 * @author Darren
 * @date 2018/6/19 16:59
 */
public class MaskTest {
    private static final String MARK = "*";


    @Test
    public void maskTest() {
        System.out.println(MaskString("13256424164", 3, 4));
    }

    /**
     * 信息脱敏
     * @param idNo 敏感信息
     * @param keepPrefix 保留前几位
     * @param keepSuffix 保留后几位
     * @return 已脱敏字符串 eg：132****4164
     */
    public String MaskString(String idNo, int keepPrefix, int keepSuffix) {
        int needAppend = idNo.length() - (keepPrefix + keepSuffix);

        if (needAppend > 0) {
            return new StringBuilder()
                    .append(StringUtils.left(idNo, keepPrefix))
                    .append(StringUtils.repeat(MARK, needAppend))
                    .append(StringUtils.right(idNo, keepSuffix)).toString();

        }
        return new StringBuilder()
                .append(StringUtils.repeat(MARK, idNo.length())).toString();
    }

    /**
     * 手机号脱敏方式
     * @param mobile
     * @return
     */
    public String maskMobileNo(String mobile) {
        if (StringUtils.isNotBlank(mobile)) {
            mobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return mobile;
    }

}

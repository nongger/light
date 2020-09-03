package com.darren.biz;

import com.darren.annotation.Mask;
import com.darren.utils.MaskToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

/**
 * Created by Darren
 * on 2018/6/5.
 */
public class TestBo {

    @Test
    public void toStringTest() {
        BindBankCard bindBankCard = new BindBankCard();
        bindBankCard.setIdNo("310110199999999999");
        bindBankCard.setBankCardNo("6220622062206220");
        bindBankCard.setMobile("18888888888");
        bindBankCard.setName("郭小明");
        bindBankCard.setAge(26);
        bindBankCard.setBak("1234567890");

        InnerClass innerClass = new InnerClass();
        innerClass.setIdNo("310110199999999999");
        innerClass.setBankCardNo("6220622062206220");
        innerClass.setName("郭小明");

        bindBankCard.setInnerClass(innerClass);

        System.out.println(bindBankCard);
    }



    class BindBankCard {

        /**
         * 真实姓名
         */
        @Mask(prefixNoMaskLen = 1)
        private String name;

        /**
         * 身份证号
         */
        @Mask
        private String idNo;

        /**
         * 银行卡号
         */
        @Mask(suffixNoMaskLen = 4)
        private String bankCardNo;

        /**
         * 银行预留手机号
         */
        @Mask(prefixNoMaskLen = 3, suffixNoMaskLen = 3)
        private String mobile;

        /**
         * 整型忽略测试
         */
        @Mask(prefixNoMaskLen = 1, suffixNoMaskLen = 1)
        private int age;

        @Mask(prefixNoMaskLen = 2, suffixNoMaskLen = 7, maskStr = "#")
        private String bak;

        private InnerClass innerClass;

        public String getName() {
            return name;
        }

        public BindBankCard setName(String name) {
            this.name = name;
            return this;
        }

        public String getIdNo() {
            return idNo;
        }

        public BindBankCard setIdNo(String idNo) {
            this.idNo = idNo;
            return this;
        }

        public String getBankCardNo() {
            return bankCardNo;
        }

        public BindBankCard setBankCardNo(String bankCardNo) {
            this.bankCardNo = bankCardNo;
            return this;
        }

        public String getMobile() {
            return mobile;
        }

        public BindBankCard setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public int getAge() {
            return age;
        }

        public BindBankCard setAge(int age) {
            this.age = age;
            return this;
        }

        public String getBak() {
            return bak;
        }

        public BindBankCard setBak(String bak) {
            this.bak = bak;
            return this;
        }

        public InnerClass getInnerClass() {
            return innerClass;
        }

        public BindBankCard setInnerClass(InnerClass innerClass) {
            this.innerClass = innerClass;
            return this;
        }

        @Override
        public String toString() {
            return MaskToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    class InnerClass {
        /**
         * 真实姓名
         */
        @Mask(prefixNoMaskLen = 1)
        private String name;

        /**
         * 身份证号
         */
        @Mask
        private String idNo;

        /**
         * 银行卡号
         */
        @Mask(suffixNoMaskLen = 4)
        private String bankCardNo;

        public String getName() {
            return name;
        }

        public InnerClass setName(String name) {
            this.name = name;
            return this;
        }

        public String getIdNo() {
            return idNo;
        }

        public InnerClass setIdNo(String idNo) {
            this.idNo = idNo;
            return this;
        }

        public String getBankCardNo() {
            return bankCardNo;
        }

        public InnerClass setBankCardNo(String bankCardNo) {
            this.bankCardNo = bankCardNo;
            return this;
        }

        @Override
        public String toString() {
            return MaskToStringBuilder.toStringExclude(this, "bankCardNo");
        }
    }


}

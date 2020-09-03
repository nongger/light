package com.darren.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2017/7/20
 * Desc   :
 */

public interface PayConstant {

    /**
     * 支付渠道常量
     */
    String ALIPAY = "alipay"; //支付宝支付
    String WECHAT = "wechat"; //微信支付
    String PAYPAL = "paypal"; //PayPal

    Map<Integer, String> PAY_CHANNEL_MAP = new HashMap<Integer, String>() {
        {
            put(ALIPAY_NUM, ALIPAY);
            put(WECHAT_NUM, WECHAT);
            put(PAYPAL_NUM, PAYPAL);
        }
    };

    /**
     * 支付渠道数字常量
     */
    int ALIPAY_NUM = 1; //支付宝支付
    int WECHAT_NUM = 2; //微信支付
    int PAYPAL_NUM = 3; //PayPal


    /**
     * 支付请求端常量
     */
    int DEVICE_PC = 1; //pc端
    int DEVICE_H5 = 2; //h5
    int DEVICE_APP = 3; //app

    String DEVICE_ANDROID = "ANDROID";
    String DEVICE_IOS = "IOS";
    String DEFAULT_DEVICE_TYPE = "DEFAULT";

    /**
     * 货币类型常量
     */
    String FEE_TYPE = "CNY"; //人民币
    String FEE_TYPE_USD = "USD"; //美元
    String FEE_TYPE_HKD = "HKD"; //港币
    String FEE_TYPE_JPY = "JPY"; //小日本币

    //币种符号
    String CNY_SYMBOL = "¥"; //人民币符号
    String USD_SYMBOL = "$"; //美元符号
    String HKD_SYMBOL = "HKD$"; //港币符号

    Map<String, String> FEE_TYPE_SYMBOL = new HashMap<String, String>() {
        {
            put(FEE_TYPE, CNY_SYMBOL);
            put(FEE_TYPE_USD, USD_SYMBOL);
            put(FEE_TYPE_HKD, HKD_SYMBOL);
            put(FEE_TYPE_JPY, CNY_SYMBOL);
        }
    };

    /**
     * 订单状态
     */
    int WAIT_PAY = 1; //待支付
    int PAY_CLOSED = 2; //支付关闭
    int PAY_FAIL = 3; //支付失败
    int PAY_SUCCESS = 4; //支付成功
    int PAY_DONE = 5; //交易完成
    int PAY_SUCCESS_AND_CANCEL = 6; //支付成功并且已取消
    int PAY_CANCEL = 7; //取消支付

    /**
     * 通知业务方的订单状态常量值
     */
    String WAIT_PAY_STR = "PAYING"; //待支付
    String PAY_OVERDUE_STR = "OVERDUE"; //支付过期
    String PAY_CLOSED_STR = "CLOSED"; //支付关闭
    String PAY_FAIL_STR = "FAIL"; //支付失败
    String PAY_SUCCESS_STR = "SUCCESS"; //支付成功
    String PAY_DONE_STR = "FINISHED"; //交易完成
    String PAY_SUCCESS_AND_CANCEL_STR = "PAY_SUCCESS_AND_CANCEL"; //支付成功并且已取消
    String PAY_CANCEL_STR = "PAY_CANCEL"; //取消支付

    /**
     * 设置回调 flag 通知标志 -1:无需通知,0:异步退款中,1:待回调业务方,4:通知完成
     */
    Integer NOTIFY_NOTHING = -1;
    Integer ASYN_REFUND = 0;
    Integer NOTIFY_TO_DO = 1;
    Integer NOTIFY_SUCCESS = 4;


}

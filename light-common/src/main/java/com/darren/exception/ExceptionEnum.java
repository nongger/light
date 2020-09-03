package com.darren.exception;

/**
 * Project: light
 * Author : Darren
 * Time   : 2017/7/19
 * Desc   : 接口异常响应码集合
 * 异常状态码在此处统一定义，对外含义统一
 */

public enum ExceptionEnum {

    SUCCESS(0L, "SUCCESS"),
    SIGN_ERROR(8004010001L, "SIGN_ERROR"),
    MISSING_PARAMS(8004010003L, "MISSING_PARAMS"),
    INTERNAL_ERROR(8004010004L, "INTERNAL_ERROR"),
    MISSING_TIMESTAMP(8004010005L, "MISSING_TIMESTAMP"),
    AUTH_RESPONSE_FAIL(800401006L, "auth response fail", "账户异常，请重新登录"),
    USER_NOT_LOGIN(800401007L, "user not login", "账户异常，请重新登录"),
    USER_NOT_VALID(800401008L, "user not valid"),

    TIME_PARAM_ERROR(80040100010L, "time format error", "请求已过期"),
    CHANNEL_REQUEST_ERROR(800401012L, "channel request failed"),
    REQUEST_FREQUENTLY_ERROR(8004010015L, "request frequently"),

    DEVICE_TYPE_NOT_SUPPORT(8004010100L, "not supported device"),

    // DB 操作相关
    DB_INSERT_FAILED(8004010512L, "db insert failed"),
    DB_UPDATE_FAILED(8004010513L, "db update failed"),


    // 结尾符
    END_FOR_QUOTE(8004010999L, "DO NOT MODIFY THIS LINE, NEVEL !!!"),

    TEST_TIME_OUT_ERROR(8999999999L, "testTimeoutError!");//TODO


    /**
     * 错误码
     */
    private Long code;
    /**
     * 错误信息
     */
    private String msg;

    private String showMsg;

    ExceptionEnum(Long code, String msg) {
        this(code, msg, COMMON_SHOW_ERROR_MSG);
    }

    ExceptionEnum(Long code, String msg, String showMsg) {
        this.code = code;
        this.msg = msg;
        this.showMsg = showMsg;
    }

    public Long getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public RequestException createException() {
        return new RequestException(this.code, this.msg, this.showMsg);
    }

    public RequestException createException(String msg) {
        return new RequestException(this.code, msg);
    }

    public RequestException createExceptionMsg(String msg) {
        return new RequestException(this.code, msg);
    }

    public final static String COMMON_SHOW_ERROR_MSG = "服务器开小差了";
}

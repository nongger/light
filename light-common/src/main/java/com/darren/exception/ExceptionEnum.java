package com.darren.exception;

/**
 * Project: light
 * Author : Darren
 * Time   : 2017/7/19
 * Desc   : 接口异常响应码集合
 * 异常状态码在此处统一定义，对外含义统一
 */

public enum ExceptionEnum {

    SUCCESS(0, "SUCCESS"),
    SIGN_ERROR(10001, "SIGN_ERROR"),
    MISSING_PARAMS(10003, "MISSING_PARAMS"),
    INTERNAL_ERROR(10004, "INTERNAL_ERROR"),
    MISSING_TIMESTAMP(10005, "MISSING_TIMESTAMP"),
    AUTH_RESPONSE_FAIL(10006, "auth response fail", "账户异常，请重新登录"),
    USER_NOT_LOGIN(10007, "user not login", "账户异常，请重新登录"),
    USER_NOT_VALID(10008, "user not valid"),

    TIME_PARAM_ERROR(10010, "time format error", "请求已过期"),
    CHANNEL_REQUEST_ERROR(10012, "channel request failed"),
    REQUEST_FREQUENTLY_ERROR(10015, "request frequently"),

    DEVICE_TYPE_NOT_SUPPORT(10100, "not supported device"),

    // DB 操作相关
    DB_INSERT_FAILED(10512, "db insert failed"),
    DB_UPDATE_FAILED(10513, "db update failed"),


    // 结尾符
    END_FOR_QUOTE(10999, "DO NOT MODIFY THIS LINE, NEVEL !!!"),

    TEST_TIME_OUT_ERROR(99999, "testTimeoutError!");//TODO


    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String msg;

    private String showMsg;

    ExceptionEnum(int code, String msg) {
        this(code, msg, COMMON_SHOW_ERROR_MSG);
    }

    ExceptionEnum(int code, String msg, String showMsg) {
        this.code = code;
        this.msg = msg;
        this.showMsg = showMsg;
    }

    public int getCode() {
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

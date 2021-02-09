package com.darren.exception;

/**
 * Created by Darren on 2017/7/19.
 */
public class RequestException extends RuntimeException {


    public RequestException(int code, String message) {
        super(message);
        this.code = code;
    }

    public RequestException(int code, String message, String showMsg) {
        super(message);
        this.code = code;
        this.showMsg = showMsg;
    }

    public RequestException(int code, String message, String showMsg, Object data) {
        super(message);
        this.code = code;
        this.showMsg = showMsg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private int code;

    private String showMsg;

    private Object data;

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

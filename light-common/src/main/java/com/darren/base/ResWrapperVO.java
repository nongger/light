package com.darren.base;

import com.darren.exception.ExceptionEnum;
import com.darren.exception.RequestException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2018/7/31 22:06
 * Desc   : 基础响应对象
 */
@ApiModel(value = "ResWrapperVO", description = "包装相应对象")
public class ResWrapperVO<T> {

    public static ResWrapperVO createResponse() {
        return new ResWrapperVO();
    }

    public ResWrapperVO<T> success(T data) {
        this.errno = ExceptionEnum.SUCCESS.getCode();
        this.msg   = ExceptionEnum.SUCCESS.getMsg();
        this.showMsg = "";
        this.data  = data;
        return this;
    }

    public ResWrapperVO<T> fail(RequestException error, T data) {
        this.errno = error.getCode();
        this.msg   = error.getMessage();
        this.showMsg = error.getShowMsg();
        this.data  = data;
        return this;
    }

    /**
     * 错误码, 0为正确响应
     */
    @ApiModelProperty(value = "错误码, 0为正确响应",required = true)
    private Long errno;

    /**
     * 错误信息，success为成功
     */
    @ApiModelProperty(value = "错误信息，success为成功",required = true)
    private String msg;

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    /**
     * 错误信息，success为成功
     */
    @ApiModelProperty(value = "客户端展示错误信息",required = true)
    private String showMsg;
    /**
     * 返回数据对象
     */
    @ApiModelProperty(value = "返回数据对象",required = true)
    private T data;

    public Long getErrno() {
        return errno;
    }

    public void setErrno(Long errno) {
        this.errno = errno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

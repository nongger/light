package com.darren.handle;

import com.darren.base.ResWrapperVO;
import com.darren.exception.RequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * Project: light
 * Time   : 2021-02-08 20:55
 * Author : Eric
 * Version: v1.0
 * Desc   : 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static int DUPLICATE_KEY_CODE = 1001;
    private static int PARAM_FAIL_CODE = 1002;
    private static int VALIDATION_CODE = 1003;

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RequestException.class)
    public ResWrapperVO handleRRException(RequestException e) {
        log.error(e.getMessage(), e);
        return new ResWrapperVO().fail(e, null);
    }

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResWrapperVO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常", e);
        return new ResWrapperVO(PARAM_FAIL_CODE, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public ResWrapperVO handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ResWrapperVO(VALIDATION_CODE, e.getCause().getMessage());
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResWrapperVO handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return new ResWrapperVO(PARAM_FAIL_CODE, e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResWrapperVO handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResWrapperVO(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResWrapperVO handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return new ResWrapperVO(DUPLICATE_KEY_CODE, "数据重复，请检查后提交");
    }


    @ExceptionHandler(Exception.class)
    public ResWrapperVO handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResWrapperVO(500, "系统繁忙,请稍后再试");
    }
}


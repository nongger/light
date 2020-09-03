package com.darren.utils;

import com.darren.exception.ExceptionEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 获取httpRequest工具类
 *
 * on 2018/5/25.
 */

public class WebKit {

    private static final String REQ_USER_ATTR_KEY = "E4984D0E3E47E85A474186FA6FE685704B8927E7D567488B8D99582FBA92CBED";

    private static final String COOKIE_DESC = "Cookie";

    /**
     * 获取 HttpServletRequest
     *
     * @return
     */
    public static HttpServletRequest getHttpRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request == null) {
            throw ExceptionEnum.INTERNAL_ERROR.createException();
        }
        return request;
    }

    /**
     * 获取 HttpServletResponse
     *
     * @return
     */
    public static HttpServletResponse getHttpResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    public static HttpServletRequest getHttpRequestDefNull() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        if (request == null) {
            return null;
        }
        return request;
    }


    private static final String NULL_DESC = "null";

    /**
     * 放置用户id到request
     */
    public static void pubMidToReq(String mid) {
        if (StringUtils.isBlank(mid) || StringUtils.equalsIgnoreCase(NULL_DESC, mid)) {
            throw ExceptionEnum.AUTH_RESPONSE_FAIL.createException();
        }

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw ExceptionEnum.INTERNAL_ERROR.createException();
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        if (request == null) {
            throw ExceptionEnum.INTERNAL_ERROR.createException();
        }
        request.setAttribute(REQ_USER_ATTR_KEY, mid);
    }

    /**
     * 通过HttpServletRequest 获取 用户id
     *
     * @return
     */
    public static String getMidFromReq() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request == null) {
            throw ExceptionEnum.INTERNAL_ERROR.createException();
        }
        String midStr = (String) request.getAttribute(REQ_USER_ATTR_KEY);
        return StringUtils.trimToEmpty(midStr);
    }

    /**
     * 通过HttpServletRequest 获取有效用户id
     *
     * @return
     */
    public static String getValidMidFromReq() {
        String midFromReq = getMidFromReq();
        if (StringUtils.isBlank(midFromReq)) {
            throw ExceptionEnum.USER_NOT_LOGIN.createException();
        }
        return midFromReq;
    }


    public static String getMidFromReqDefEmpty() {
        HttpServletRequest httpRequest = getHttpRequestDefNull();
        if (httpRequest == null) {
            return StringUtils.EMPTY;
        }
        String midStr = (String) httpRequest.getAttribute(REQ_USER_ATTR_KEY);
        return StringUtils.trimToEmpty(midStr);
    }

    public static String getCookieDefEmpty() {
        HttpServletRequest httpRequest = getHttpRequestDefNull();
        if (httpRequest == null) {
            return StringUtils.EMPTY;
        }
        String cookie = httpRequest.getHeader(COOKIE_DESC);
        return StringUtils.trimToEmpty(cookie);
    }

    public static String getCookie() {
        HttpServletRequest httpRequest = getHttpRequest();
        String cookie = httpRequest.getHeader(COOKIE_DESC);
        return StringUtils.trimToEmpty(cookie);
    }

}

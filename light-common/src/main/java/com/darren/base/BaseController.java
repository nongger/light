package com.darren.base;


import com.darren.exception.ExceptionEnum;
import com.darren.exception.RequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.InvalidParameterException;

/**
 * Created by Darren on 2017/7/19.
 */
public class BaseController {
    // 通用状态码
    protected static final String FAIL_STR = "FAIL"; //失败
    protected static final String SUCCESS_STR = "SUCCESS"; //成功


    @ExceptionHandler
    @ResponseBody
    public ResWrapperVO handleException(Exception e) {
        if (e instanceof RequestException) {
            RequestException re = (RequestException) e;
            return fail(re, re.getData() != null ? re.getData() : null);
        } else if (e.getClass() == InvalidParameterException.class ||
                e.getClass() == MethodArgumentNotValidException.class) {
            return fail(ExceptionEnum.MISSING_PARAMS, null);
        } else {

            RequestException exception = null;
            if (StringUtils.isNotBlank(e.getMessage())) {
                exception = ExceptionEnum.INTERNAL_ERROR.createExceptionMsg(e.getMessage());
            } else {
                exception = ExceptionEnum.INTERNAL_ERROR.createException();
            }
            return fail(exception, null);
        }
    }

    @SuppressWarnings("unchecked")
    protected ResWrapperVO fail(RequestException error, Object data) {
        return ResWrapperVO.createResponse().fail(error, data);
    }

    @SuppressWarnings("unchecked")
    public static ResWrapperVO fail(ExceptionEnum error, Object data) {
        return ResWrapperVO.createResponse().fail(error.createException(), data);
    }

    @SuppressWarnings("unchecked")
    protected ResWrapperVO success(Object data) {
        return ResWrapperVO.createResponse().success(data);
    }

    ///**
    // * 解析异步通知中的xml元素值
    // **/
    //@SuppressWarnings("unchecked")
    //protected Map<String, String> parseRequestXmlToMap(HttpServletRequest request) {
    //    Map<String, String> map = new HashMap<>();
    //    try {
    //        return ReqUtils.getXmlParamMap(request);
    //    } catch (Exception e) {
    //        CommonLog.error("解析xml失败");
    //    }
    //    return map;
    //}
    //
    ///**
    // * 响应xml格式
    // **/
    //public void setXmlResponse(HttpServletResponse response, Map<String, Object> responseMap) {
    //    response.setCharacterEncoding("UTF-8");
    //    response.setContentType("application/xml; charset=utf-8");
    //    String xml = ConvertUtils.buildXmlFormMap(responseMap, true);
    //    PrintWriter out = null;
    //    try {
    //        out = response.getWriter();
    //        out.append(xml);
    //    } catch (IOException e) {
    //    } finally {
    //        if (out != null) {
    //            out.close();
    //        }
    //    }
    //}
    //
    ///**
    // * 获取请求参数对
    // *
    // * @param request
    // * @return
    // */
//    public static Map<String, String> getRequestParams(HttpServletRequest request) {
//        Map<String, String> params = new HashMap<>();
//        if (null != request) {
//            Set<String> paramsKey = request.getParameterMap().keySet();
//            for (String key : paramsKey) {
//                params.put(key, request.getParameter(key));
//            }
//        }
//        return params;
//    }

}

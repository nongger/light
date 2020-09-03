package com.darren.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * OkHttpClient 请求工具类
 * Created by Darren
 * on 2018/5/9.
 */
public class OkHttpClientTools {

    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * json请求
     */
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    /**
     * XML请求
     */
    public static final MediaType MEDIA_TYPE_XML = MediaType.parse("text/xml; charset=UTF-8");

    public static final MediaType MEDIA_TYPE_XML_GB2312 = MediaType.parse("text/xml; charset=GB2312");

    /**
     * 通过超时时间作为获取OkHttpClient实例的key
     */
    private static final String INSTANCE_KEY_PREFIX = "OKHTTPCLIENT_CONNECTION_%s_SOCKET_%s";

    private static Integer MAX_THREAD_NUM = 4000;
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("routine-caller-%d").build();
    private static ExecutorService selfExecutor = new ThreadPoolExecutor(0, MAX_THREAD_NUM,
            60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), namedThreadFactory);


    /**
     * 存储OkHttpClient实例
     */
    private static Map<String, OkHttpClient> OK_HTTPCLIENT_TIMEOUT_MAP = new ConcurrentHashMap<>();

    /**
     * 通过Map维护超时时间对应OkHttpClient实例
     *
     * @param connectionTimeoutInMs
     * @param socketTimeoutInMs
     * @return
     */
    public static OkHttpClient getInstanceByTime(int connectionTimeoutInMs, int socketTimeoutInMs) {

        //INSTANCE_KEY_PREFIX + socketTimeoutInMs;
        String key = String.format(INSTANCE_KEY_PREFIX, connectionTimeoutInMs, socketTimeoutInMs);

        OkHttpClient okHttpClient = OK_HTTPCLIENT_TIMEOUT_MAP.get(key);
        if (okHttpClient == null) {
            okHttpClient = OkhttpClientSingleton.getOkHttpClient().newBuilder()
                    .connectTimeout(connectionTimeoutInMs, TimeUnit.MILLISECONDS)
                    .readTimeout(socketTimeoutInMs, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(Boolean.FALSE)
                    .build();
            OK_HTTPCLIENT_TIMEOUT_MAP.put(key, okHttpClient);
        }
        return okHttpClient;
    }

    /**
     * http统一请求工具
     *
     * @param callParam ConnectTimeoutMs 连接超时时间
     *                  ReadTimeoutMs socket读取超时时间
     *                  StrUrl 请求URL
     *                  ReqData 请求参数Map形式
     * @return
     * @throws Exception
     */
    //public static String callWithOKHttpClient(CallParam callParam) throws Exception {
    //    //获取当前时间戳，用于计算接口耗时
    //    long beforeRequestTimestamp = System.currentTimeMillis();
    //
    //    String sPostXml = WXPayUtil.mapToXml(callParam.getReqData());
    //    String response = "";
    //    if (callParam.isHardTimeOut()) {
    //        response = sendPostFutureXml(callParam.getStrUrl(), sPostXml, callParam.getConnectTimeoutMs(), callParam.getReadTimeoutMs());
    //    } else {
    //        response = sendPostXml(callParam.getStrUrl(), sPostXml, callParam.getConnectTimeoutMs(), callParam.getReadTimeoutMs());
    //    }
    //    long cost = System.currentTimeMillis() - beforeRequestTimestamp;
    //    if (cost > 1200) {
    //        CommonLog.error("[耗时过长留意观察][接口耗时统计]http request cost[{}], url[{}]", cost, callParam.getStrUrl());
    //    } else {
    //        CommonLog.info("[接口耗时统计]http request cost[{}], url[{}]", cost, callParam.getStrUrl());
    //    }
    //    return response;
    //}

    /**
     * fallBackRequestDTOs GET 使用 url 与 mapData 组合，自动将 mapData 进行字串拼接，加载在 url 后请求
     *                            POST 当ContentType为 formData，默认使用 mapData 为表单内容，
     *                            当ContentType为 JSON 时，如存在 rawData 非空，使用 rawData 进行 POST，否则使用 mapData 转化的 Json 进行 POST
     *                            当ContentType为 XML 是，使用 rawData 进行 POST 请求
     * @return
     * @throws Exception
     */
    //public static String failBackHttpRequest(List<FallBackRequestDTO> fallBackRequestDTOs) throws Exception {
    //    int fallbackTimes = 0;
    //    for (FallBackRequestDTO fallBackRequest : fallBackRequestDTOs) {
    //        String response = "";
    //        long startTime = System.currentTimeMillis();
    //        try {
    //            switch (fallBackRequest.getMethod()) {
    //                case GET:
    //                    response = sendGetFuture(HttpUtils.getUrl(fallBackRequest.getUrl(), fallBackRequest.getMapData()),
    //                            fallBackRequest.getConnectTimeout(),
    //                            fallBackRequest.getReadTimeout(),
    //                            fallBackRequest.getHeaderMap());
    //                    break;
    //                case PUT:
    //                    if (fallBackRequest.getContentType().equals(FallBackRequestDTO.ContentType.JSON)) {
    //                        if (StringUtils.isNotEmpty(fallBackRequest.getRawData())) {
    //                            response = sendPutFutureJson(fallBackRequest.getUrl(),
    //                                    fallBackRequest.getRawData(),
    //                                    fallBackRequest.getConnectTimeout(),
    //                                    fallBackRequest.getReadTimeout(),
    //                                    fallBackRequest.getHeaderMap());
    //                        } else {
    //                            String jsonData = JsonUtils.toJson(fallBackRequest.getMapData());
    //                            response = sendPutFutureJson(fallBackRequest.getUrl(),
    //                                    jsonData,
    //                                    fallBackRequest.getConnectTimeout(),
    //                                    fallBackRequest.getReadTimeout(),
    //                                    fallBackRequest.getHeaderMap());
    //                        }
    //                    } else {
    //                        throw ExceptionEnum.INTERNAL_ERROR.createException("PUT request just can be json formate");
    //                    }
    //                    break;
    //                case POST:
    //                    if (fallBackRequest.getContentType().equals(FallBackRequestDTO.ContentType.FORMDATA)) {
    //                        response = sendPostFutureFormData(fallBackRequest.getUrl(),
    //                                fallBackRequest.getMapData(),
    //                                fallBackRequest.getConnectTimeout(),
    //                                fallBackRequest.getReadTimeout(),
    //                                fallBackRequest.getHeaderMap());
    //                    } else if (fallBackRequest.getContentType().equals(FallBackRequestDTO.ContentType.XML)) {
    //                        response = sendPostFutureXml(fallBackRequest.getUrl(),
    //                                fallBackRequest.getRawData(),
    //                                fallBackRequest.getConnectTimeout(),
    //                                fallBackRequest.getReadTimeout(),
    //                                fallBackRequest.getHeaderMap());
    //                    } else if (fallBackRequest.getContentType().equals(FallBackRequestDTO.ContentType.JSON)) {
    //                        if (StringUtils.isNotEmpty(fallBackRequest.getRawData())) {
    //                            response = sendPostFutureJson(fallBackRequest.getUrl(),
    //                                    fallBackRequest.getRawData(),
    //                                    fallBackRequest.getConnectTimeout(),
    //                                    fallBackRequest.getReadTimeout(),
    //                                    fallBackRequest.getHeaderMap());
    //                        } else {
    //                            String jsonData = JsonUtils.toJson(fallBackRequest.getMapData());
    //                            response = sendPostFutureJson(fallBackRequest.getUrl(),
    //                                    jsonData,
    //                                    fallBackRequest.getConnectTimeout(),
    //                                    fallBackRequest.getReadTimeout(),
    //                                    fallBackRequest.getHeaderMap());
    //                        }
    //                    }
    //                    break;
    //
    //                default:
    //                    throw ExceptionEnum.INTERNAL_ERROR.createException("Wrong MethodType Using failback http client");
    //            }
    //            CommonLog.info("fallback http request success params [{}] fallbackTimes [{}] cost [{}]",
    //                    JsonUtils.toJson(fallBackRequest), fallbackTimes, System.currentTimeMillis() - startTime);
    //            return response;
    //        } catch (Exception e) {
    //            ++fallbackTimes;
    //            CommonLog.warn("request failed. error [{}] params [{}], cost [{}]",
    //                    e.getMessage(), JsonUtils.toJson(fallBackRequest),
    //                    System.currentTimeMillis() - startTime);
    //        }
    //    }
    //    throw ExceptionEnum.HTTP_REQUEST_FAIL.createException();
    //}


    public static String sendPostFormData(String url, Map<String, String> paraMaps, int connectionTimeoutInMs, int socketTimeoutInMs) throws Exception {
        OkHttpClient okHttpClient = getInstanceByTime(connectionTimeoutInMs, socketTimeoutInMs);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        paraMaps.forEach((k, v) -> {
            formBodyBuilder.add(k, v);
        });
        RequestBody body = formBodyBuilder.build();
        Response response = null;
        String responeContent;
        try {
            response = okHttpClient.newCall(requestBuilder.post(body).build()).execute();
            responeContent = responeContent(response);
        } catch (Exception e) {

            throw e;
        } finally {

            closeRespone(response);
        }
        return responeContent;
    }


    public static String sendPost(String url, String content, int connectionTimeoutInMs, int socketTimeoutInMs, MediaType mediaType) throws Exception {
        long before = System.currentTimeMillis();
        OkHttpClient okHttpClient = getInstanceByTime(connectionTimeoutInMs, socketTimeoutInMs);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        RequestBody body = RequestBody.create(mediaType, content);
        Response response = null;
        String responeContent;
        try {
            response = okHttpClient.newCall(requestBuilder.post(body).build()).execute();
            responeContent = responeContent(response);
        } catch (Exception e) {
            throw e;
        } finally {
            long after = System.currentTimeMillis();
            CommonLog.info("okHttpClient request cost:[{}],url:[{}]", (after - before), url);
            closeRespone(response);
        }
        return responeContent;
    }


    public static String sendPostXml(String url, String xml) throws Exception {
        return sendPostXml(url, xml, OkhttpClientSingleton.DEFAULT_CONNECTION_TIMEOUT_IN_MS, OkhttpClientSingleton.DEFAULT_SOCKET_TIMEOUT_IN_MS);
    }

    /**
     * OkHttpClient完成网路请求
     * <p>
     * 不强制hardTimeOut
     */
    public static String sendPostXml(String url, String xml, int connectionTimeoutInMs, int socketTimeoutInMs) throws Exception {
        return sendPost(url, xml, connectionTimeoutInMs, socketTimeoutInMs, MEDIA_TYPE_XML);
    }

    public static String sendPostJson(String url, String json, int connectionTimeoutInMs, int socketTimeoutInMs) throws Exception {
        return sendPost(url, json, connectionTimeoutInMs, socketTimeoutInMs, MEDIA_TYPE_JSON);
    }

    public static String sendPostFutureXml(String url, String xml, int connectionTimeoutInMs, int socketTimeoutInMs) throws Exception {
        return sendPostFuture(url, xml, connectionTimeoutInMs, socketTimeoutInMs, MEDIA_TYPE_XML);
    }

    public static String sendPostFutureXml(String url, String xml, int connectionTimeoutInMs, int socketTimeoutInMs, Map<String, String> headerMap) throws Exception {
        return sendPostFuture(url, xml, connectionTimeoutInMs, socketTimeoutInMs, MEDIA_TYPE_XML, headerMap);
    }

    public static String sendPostFutureJson(String url, String json, int connectionTimeoutInMs, int socketTimeoutInMs) throws Exception {
        return sendPostFuture(url, json, connectionTimeoutInMs, socketTimeoutInMs, MEDIA_TYPE_JSON);
    }

    public static String sendPostFutureJson(String url, String json, int connectionTimeoutInMs, int socketTimeoutInMs, Map<String, String> headerMap) throws Exception {
        return sendPostFuture(url, json, connectionTimeoutInMs, socketTimeoutInMs, MEDIA_TYPE_JSON, headerMap);
    }

    public static String sendPostFuture(String url, String content, int connectionTimeoutInMs, int socketTimeoutInMs, MediaType mediaType) throws Exception {
        return sendPostFuture(url, content, connectionTimeoutInMs, socketTimeoutInMs, mediaType, null);
    }

    public static String sendPostFuture(String url, String content, int connectionTimeoutInMs, int socketTimeoutInMs, MediaType mediaType, Map<String, String> headerMap) throws Exception {
        OkHttpClient okHttpClient = getInstanceByTime(connectionTimeoutInMs, socketTimeoutInMs);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        buildHeader(requestBuilder, headerMap);
        RequestBody body = RequestBody.create(mediaType, content);
        Response response = null;
        String responeContent;
        FutureTask<Response> future = null;
        Call okCall = null;
        try {
            Call finalOkCall = okHttpClient.newCall(requestBuilder.post(body).build());
            okCall = finalOkCall;
            Callable<Response> callable = () -> finalOkCall.execute();
            future = new FutureTask<Response>(callable);
            selfExecutor.execute(future);
            response = future.get(socketTimeoutInMs + connectionTimeoutInMs, TimeUnit.MILLISECONDS);
            if (response == null) {
                future.cancel(Boolean.TRUE);
            }
            responeContent = responeContent(response);
        } catch (Exception e) {
            cancelOkCall(okCall);
            cancelFuture(e, future);
            throw e;
        } finally {

            closeRespone(response);
        }
        return responeContent;
    }

    public static String sendPostFutureFormData(String url, Map<String, String> paraMaps,
                                                int connectionTimeoutInMs, int socketTimeoutInMs) throws Exception {
        return sendPostFutureFormData(url, paraMaps, connectionTimeoutInMs, socketTimeoutInMs, null);
    }

    public static String sendPutFutureJson(String url, String json, int connectionTimeoutInMs, int socketTimeoutInMs, Map<String, String> headerMap) throws Exception {
        return sendPutFuture(url, json, connectionTimeoutInMs, socketTimeoutInMs, MEDIA_TYPE_JSON, headerMap);
    }

    public static String sendPutFuture(String url, String content, int connectionTimeoutInMs, int socketTimeoutInMs, MediaType mediaType, Map<String, String> headerMap) throws Exception {
        OkHttpClient okHttpClient = getInstanceByTime(connectionTimeoutInMs, socketTimeoutInMs);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        buildHeader(requestBuilder, headerMap);
        RequestBody body = RequestBody.create(mediaType, content);
        Response response = null;
        String responeContent;
        FutureTask<Response> future = null;
        Call okCall = null;
        try {
            Call finalOkCall = okHttpClient.newCall(requestBuilder.put(body).build());
            okCall = finalOkCall;
            Callable<Response> callable = () -> finalOkCall.execute();
            future = new FutureTask<Response>(callable);
            selfExecutor.execute(future);
            response = future.get(socketTimeoutInMs + connectionTimeoutInMs, TimeUnit.MILLISECONDS);
            if (response == null) {
                future.cancel(Boolean.TRUE);
            }
            responeContent = responeContent(response);
        } catch (Exception e) {
            cancelOkCall(okCall);
            cancelFuture(e, future);
            throw e;
        } finally {

            closeRespone(response);
        }
        return responeContent;
    }

    public static String sendPostFutureFormData(String url, Map<String, String> paraMaps,
                                                int connectionTimeoutInMs, int socketTimeoutInMs,
                                                Map<String, String> headerMap) throws Exception {
        OkHttpClient okHttpClient = getInstanceByTime(connectionTimeoutInMs, socketTimeoutInMs);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        paraMaps.forEach((k, v) -> {
            formBodyBuilder.add(k, v);
        });
        buildHeader(requestBuilder, headerMap);
        RequestBody body = formBodyBuilder.build();
        Response response = null;
        String responeContent;
        FutureTask<Response> future = null;
        Call okCall = null;
        try {
            Call finalOkCall = okHttpClient.newCall(requestBuilder.post(body).build());
            okCall = finalOkCall;
            Callable<Response> callable = () -> finalOkCall.execute();
            future = new FutureTask<Response>(callable);
            selfExecutor.execute(future);
            response = future.get(socketTimeoutInMs + connectionTimeoutInMs, TimeUnit.MILLISECONDS);
            if (response == null) {
                future.cancel(Boolean.TRUE);
            }
            responeContent = responeContent(response);
        } catch (Exception e) {
            cancelOkCall(okCall);
            cancelFuture(e, future);
            throw e;
        } finally {

            closeRespone(response);
        }
        return responeContent;
    }

    /**
     * OkHttpClient完成网路请求
     * <p>
     * 不强制hardTimeOut
     */
    public static String sendGet(String url, int connectionTimeoutInMs, int socketTimeoutInMs) throws Exception {
        OkHttpClient okHttpClient = getInstanceByTime(connectionTimeoutInMs, socketTimeoutInMs);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        Response response = null;
        String responeContent;
        try {
            response = okHttpClient.newCall(requestBuilder.get().build()).execute();
            responeContent = responeContent(response);
        } catch (Exception e) {
            throw e;
        } finally {

            closeRespone(response);
        }
        return responeContent;
    }

    public static String sendGetFuture(String url, int connectionTimeoutInMs, int socketTimeoutInMs) throws Exception {
        return sendGetFuture(url, connectionTimeoutInMs, socketTimeoutInMs, null);
    }

    /**
     * OkHttpClient完成网路请求
     * <p>
     */
    public static String sendGetFuture(String url, int connectionTimeoutInMs,
                                       int socketTimeoutInMs, Map<String, String> headerMap) throws Exception {
        OkHttpClient okHttpClient = getInstanceByTime(connectionTimeoutInMs, socketTimeoutInMs);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        buildHeader(requestBuilder, headerMap);
        Response response = null;
        String responeContent;
        FutureTask<Response> future = null;
        long s = System.currentTimeMillis();
        Call okCall = null;
        try {
            Call finalOkCall = okHttpClient.newCall(requestBuilder.get().build());
            okCall = finalOkCall;
            Callable<Response> callable = () -> finalOkCall.execute();
            future = new FutureTask<Response>(callable);
            selfExecutor.execute(future);
            response = future.get(socketTimeoutInMs + connectionTimeoutInMs, TimeUnit.MILLISECONDS);
            if (response == null) {
                future.cancel(Boolean.TRUE);
            }
            responeContent = responeContent(response);
        } catch (Exception e) {
            cancelOkCall(okCall);
            cancelFuture(e, future);
            throw e;
        } finally {

            closeRespone(response);
        }
        return responeContent;
    }

    private static Request.Builder buildHeader(Request.Builder requestBuilder, Map<String, String> headerMap) {
        if (headerMap != null) {
            Headers.Builder builder = new Headers.Builder();
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            requestBuilder.headers(builder.build());
        }
        return requestBuilder;
    }

    /**
     * 构造返回结果
     */
    private static String responeContent(Response response) throws IOException {
        String responeContent = StringUtils.EMPTY;
        if (response != null && response.isSuccessful()) {
            responeContent = response.body().string();
        }

        //关闭返回留信息
        closeRespone(response);

        return responeContent;
    }

    /**
     * future cancle
     */
    private static void cancelFuture(Exception e, FutureTask<Response> future) {
        try {
            if (e instanceof TimeoutException
                    || e instanceof SocketTimeoutException
                    || e instanceof ExecutionException
                    || e instanceof InterruptedException) {

                if (future != null && !future.isCancelled()) {
                    future.cancel(Boolean.TRUE);
                }
            }
        } catch (Exception ex) {
        }
    }

    /**
     * response.body().close() 等同于 response.close()
     */
    private static void closeRespone(Response response) {
        try {
            if (response != null) {
                if (response.body() != null) {
                    response.body().close();
                } else {
                    response.close();
                }
            }
        } catch (Exception ex) {
        }
    }

    private static void cancelOkCall(Call okCall) {
        try {
            if (okCall != null) {
                okCall.cancel();
            }
        } catch (Exception ex) {
        }
    }

}

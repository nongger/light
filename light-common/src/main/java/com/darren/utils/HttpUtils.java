package com.darren.utils;

import com.google.gson.JsonObject;
import okhttp3.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by Darren on 2017/7/23.
 */


public class HttpUtils {

    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);
    private final static long READ_TIMEOUT = 3000;
    private final static long CONNECT_TIMEOUT = 3000;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType XML = MediaType.parse("text/xml; charset=UTF-8");


    private static OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .build();

    /**
     * xml请求关键在于设置请求头部mediaType
     *
     * @throws IOException
     */
    public static String xmlPost(String url, String sPostXml) throws IOException {
        String result = null;

        RequestBody body = RequestBody.create(XML, sPostXml);
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        Response response = client.newCall(request).execute();

        result = Objects.isNull(response) ? null : response.body().string();
        if (response != null && !response.isSuccessful())
            throw new IOException("OkHttpClient请求出错：" + response);

        log.info("OkHttpClient返回结果" + result);
        return result;
    }


    public static String get(String url, Map<String, String> sParaTemp, Integer connectTimeout, Integer readTimeout) {
        return get(url, sParaTemp, connectTimeout, readTimeout, null);
    }

    public static String get(String url, Map<String, String> sParaTemp,
                             Integer connectTimeout, Integer readTimeout, Map<String, String> headerMap) {
        Long now = System.currentTimeMillis();
        Headers headers = null;
        if (headerMap != null) {
            Headers.Builder builder = new Headers.Builder();
            for (Entry<String, String> entry : headerMap.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            headers = builder.build();
        }

        String query = getUrl(url, sParaTemp);
        Request request;
        if (headers == null) {
            request = new Request.Builder()
                    .url(query).build();
        } else {
            request = new Request.Builder().
                    url(query).headers(headers).
                    build();
        }
        String result = null;

        try {
            Response response = client.newCall(request).execute();
            result = Objects.isNull(response) ? null : response.body().string();
        } catch (IOException e) {
            log.error(String.format("noticHttpClient request IOException = {%s}", e.getMessage()));
        } catch (Exception ex) {
            log.error(String.format("noticHttpClient request Exception = {}", ex.getMessage()));
        } finally {
            Long cost = System.currentTimeMillis() - now;
            log.info(String.format("http request url[{}] result[{}] cost[{}]", query, result, cost));
        }
        return result;
    }

    public static String doGet(String url, Map<String, String> params,
                               Integer connectTimeout, Integer readTimeout) throws Exception {

        String finalUrl = getUrl(url, params);
        BufferedReader in = null;
        URLConnection connection = null;
        try {
            StringBuilder sb = new StringBuilder();
            URL realUrl = new URL(finalUrl);
            connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            log.warn(String.format("request failed. url[%s] exception[%s]", finalUrl, e.getMessage()));
            throw e;
        } finally {
            /*
            try{
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            */
        }
    }

    public static String doGet(String url, Map<String, String> params, String cookie,
                               Integer connectTimeout, Integer readTimeout) throws Exception {

        List<String> paramList = new ArrayList<>();
        for (Entry<String, String> entry : params.entrySet()) {
            paramList.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }

        String queryString = StringUtils.join(paramList, "&");

        String finalUrl;

        if (url.contains("?")) {
            finalUrl = String.format("%s&%s", url, queryString);
        } else {
            finalUrl = String.format("%s?%s", url, queryString);
        }
        BufferedReader in = null;
        URLConnection connection = null;
        try {
            StringBuilder sb = new StringBuilder();
            URL realUrl = new URL(finalUrl);
            connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Cookie", cookie);
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            String msg = String.format("request failed. url[%s] exception[%s]", finalUrl, e.getMessage());
            log.warn(msg);
            throw new Exception(msg);
        } finally {
            /*
            try{
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            */
        }
    }

    public static JsonObject doGetWithJsonBack(String url, Map<String, String> params,
                                               Integer connectTimeout, Integer readTimeout) throws RuntimeException {
        try {
            String jsonBack = doGet(url, params, connectTimeout, readTimeout);
            return JsonUtils.parseJson(jsonBack).getAsJsonObject();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("requst with json back fail. " + e.getMessage());
        }
    }

    public static JsonObject getWithJsonBack(String url, Map<String, String> params,
                                             Integer connectTimeout, Integer readTimeout, Map<String, String> headers)
            throws RuntimeException {
        String jsonBack = get(url, params, connectTimeout, readTimeout, headers);
        try {
            return JsonUtils.parseJson(jsonBack).getAsJsonObject();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("requst with json back fail. " + e.getMessage());
        }
    }

    private static String getUrl(String url, Map<String, String> params) {
        List<String> paramList = new ArrayList<>();
        if (params != null) {
            for (Entry<String, String> entry : params.entrySet()) {
                paramList.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
            }
        }

        String queryString = StringUtils.join(paramList, "&");

        String finalUrl;

        if (url.contains("?")) {
            finalUrl = String.format("%s&%s", url, queryString);
        } else {
            finalUrl = String.format("%s?%s", url, queryString);
        }
        return finalUrl;
    }


}

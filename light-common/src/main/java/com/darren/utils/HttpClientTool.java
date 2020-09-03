package com.darren.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.List;

/**
 * 网络请求工具类
 *
 * @author Darren
 * @date 2018/4/19
 */
public class HttpClientTool {

    protected transient Logger log = LoggerFactory.getLogger(HttpClientTool.class);

    // 连接超时时间，默认15秒
    private int socketTimeout = 15000;

    // 传输超时时间，默认15秒
    private int connectTimeout = 15000;

    private static final String CHARSET_UTF8 = "UTF-8";

    private static final String APPLICATION_JSON_CHARSET_UTF8 = "application/json;charset=utf-8";

    private static final String CONTENT_TYPE = "Content-Type";

    private static final String APPLICATION_FORM_CHARSET_UTF8 = "application/x-www-form-urlencoded;charset=utf-8";

    private static final String TEXT_XML = "text/xml";

    // 请求器的配置
    private RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
            .setConnectTimeout(connectTimeout).build();

    // HTTP请求器
    private CloseableHttpClient httpClient;

    //私有化
    private HttpClientTool() {
        httpClient = HttpClients.createDefault();
    }

    public static HttpClientTool create() {
        return new HttpClientTool();
    }

    public String sendPostXml(String url, String xml) throws Exception {

        String responeContent = StringUtils.EMPTY;
        // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity postEntity = new StringEntity(xml, CHARSET_UTF8);
            httpPost.addHeader(CONTENT_TYPE, TEXT_XML);
            httpPost.setEntity(postEntity);
            httpPost.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            responeContent = responeContent(response);
        } catch (Exception e) {
            log.error("http post error: [{}]", e.getMessage());
            throw e;
        } finally {
            httpClient.close();
        }
        return responeContent;
    }

    /**
     * httpPost 发送NameValuePair数据
     */
    public String sentPost(String url, List<NameValuePair> list) throws Exception {

        String responeContent = StringUtils.EMPTY;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, CHARSET_UTF8);
            httpPost.addHeader(CONTENT_TYPE, APPLICATION_FORM_CHARSET_UTF8);
            entity.setContentEncoding(new BasicHeader(CONTENT_TYPE, APPLICATION_FORM_CHARSET_UTF8));
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            responeContent = responeContent(response);
        } catch (Exception e) {
            log.error("httpPost error", e);
            throw e;
        } finally {
            httpClient.close();
        }
        return responeContent;
    }

    /**
     * 通过IO流设置证书
     * @param certStream
     * @param password
     * @throws Exception
     */
    public void setSSLContextByIOstream(InputStream certStream, String password) throws Exception {
        //if (StringUtils.isEmpty(certClassPath) || StringUtils.isEmpty(password))
        //    throw new Exception("路径或者密码为空");
        try {
            if (certStream == null)
                throw new Exception("无法获取证书流");
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(certStream, password.toCharArray());// 设置证书密码
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(SSLContextBuilder.create().loadKeyMaterial(keyStore, password.toCharArray()).build(),
                    new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            httpClient = HttpClients.custom().setDefaultConnectionConfig(ConnectionConfig.custom().setBufferSize(4128).build())
                    .setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            httpClient.close();
            throw e;
        } finally {
            if (null != certStream)
                certStream.close();
        }
    }

    //public void setSSLContext(String certClassPath, String password) throws Exception {
    //    if (StringUtils.isEmpty(certClassPath) || StringUtils.isEmpty(password))
    //        throw new Exception("路径或者密码为空");
    //    try (InputStream in = new ClassPathResource(certClassPath).getInputStream()) {
    //        if (in == null)
    //            throw new Exception("无法获取证书流");
    //        KeyStore keyStore = KeyStore.getInstance("PKCS12");
    //        keyStore.load(in, password.toCharArray());// 设置证书密码
    //        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(SSLContextBuilder.create().loadKeyMaterial(keyStore, password.toCharArray()).build(),
    //                new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
    //        httpClient = HttpClients.custom().setDefaultConnectionConfig(ConnectionConfig.custom().setBufferSize(4128).build())
    //                .setSSLSocketFactory(sslsf).build();
    //    } catch (Exception e) {
    //        httpClient.close();
    //        throw e;
    //    }
    //}

    /**
     * httpPost 发送json数据
     */
    public String sendPost(String url, String json) throws Exception {
        String responeContent = StringUtils.EMPTY;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            StringEntity entity = new StringEntity(json, CHARSET_UTF8);
            entity.setContentType(APPLICATION_JSON_CHARSET_UTF8);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            responeContent = responeContent(response);
        } catch (Exception e) {
            log.error("httpPost error", e);
            throw e;
        } finally {
            httpClient.close();
        }
        return responeContent;
    }


    /**
     * httpGet 发送json数据
     */
    public String sendGet(String url) throws Exception {
        return sendGet(url, CHARSET_UTF8);
    }

    public String sendGet(String url, String encodeType) throws Exception {
        String responeContent = StringUtils.EMPTY;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(httpGet);
            responeContent = responeContent(response, encodeType);
        } catch (Exception e) {
            log.error("httpPost error", e);
            throw e;
        } finally {
            httpClient.close();
        }
        return responeContent;
    }

    /**
     * 获取HttpResponse中内容
     *
     * @param response
     * @return
     * @throws UnsupportedOperationException
     * @throws IOException
     */
    private String responeContent(HttpResponse response, String contentType) throws UnsupportedOperationException, IOException {
        String responeContent = StringUtils.EMPTY;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entityReponse = response.getEntity();
            if (entityReponse != null) {
                responeContent = IOUtils.toString(entityReponse.getContent(), contentType);
            }
        }
        return responeContent;
    }


    private String responeContent(HttpResponse response) throws UnsupportedOperationException, IOException {
        return responeContent(response, CHARSET_UTF8);
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }
}

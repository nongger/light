package com.darren.fresh.javaNet;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * URL编程学习：
 * URL的基本结构由5部分组成：
 * <传输协议>://<主机名>:<端口号>/<文件名>
 * 例如: http://192.168.1.100:8080/helloworld/index.jsp
 *
 * @author Darren
 * @date 2018/4/22
 */
public class URLTest {

    @Test
    public void URLTest() {
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:8080/examples/hello.txt?a=3");
            System.out.println("通信协议：" + url.getProtocol());
            System.out.println(url.getHost());
            System.out.println(url.getPort());
            System.out.println(url.getFile());
            System.out.println(url.getRef());
            System.out.println(url.getQuery());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void openStream() {
        InputStream inputStream = null;
        try {
            URL url = new URL("http://localhost:8080/examples/hello.txt");
            inputStream = url.openStream();
            int len = 0;
            byte[] source = new byte[512];
            while ((len = inputStream.read(source)) != -1) {
                String result = new String(source, 0, len);
                System.out.println(result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * URLConnection测试
     * 下载文件
     */
    @Test
    public void UrlConnectionTest() {
        InputStream read = null;
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL("http://localhost:8080/examples/hello.txt");
            URLConnection urlConnection = url.openConnection();
            read = urlConnection.getInputStream();
            fileOutputStream = new FileOutputStream(new File("downFile.txt"));
            byte[] getb = new byte[512];
            int lens = 0;
            while ((lens = read.read(getb)) != -1) {
                fileOutputStream.write(getb, 0, lens);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fileOutputStream)
                    fileOutputStream.close();
                if (null != read)
                    read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}

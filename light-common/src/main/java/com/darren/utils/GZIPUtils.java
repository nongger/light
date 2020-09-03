package com.darren.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 字符串压缩
 * Created by Darren
 * on 2018/6/5.
 */
public class GZIPUtils {


    public static String compress(String str) throws IOException {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        String outStr = new String(Base64.encodeBase64(out.toByteArray()));
        return outStr;
    }

    public static String uncompress(String str) throws IOException {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(Base64.decodeBase64(str)));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(gis, out);
        return new String(out.toByteArray());
    }

    public static void main(String[] args) throws Exception {
        String tmp = "13262735313";

        //StringBuilder sb = new StringBuilder();
        //for (int i = 0; i < 1; i++) {
        //    sb.append(tmp);
        //}
        //
        //String str = sb.toString();
        //tmp = "341623199010067696";
          tmp = "12345678908765432X";
          tmp = "1234567890876543219";
        String str1 = GZIPUtils.compress(tmp);

        String result = GZIPUtils.uncompress(str1);

        System.out.println("原长度：" + tmp.length());
        System.out.println("压缩后字符串：" + str1);
        System.out.println("压缩后字符串长度：" + str1.length());
        System.out.println(result.equals(tmp));
        System.out.println("解压缩后字符串：" + result);
    }

}

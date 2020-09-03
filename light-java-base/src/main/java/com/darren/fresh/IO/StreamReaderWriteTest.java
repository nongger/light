package com.darren.fresh.IO;

import org.junit.Test;

import java.io.*;

/**
 * 转换流
 *
 * @author Darren
 * @date 2018/5/8
 */
public class StreamReaderWriteTest {

    final String CHARSET_GBK = "GBK";
    final String CHARSET_UTF8 = "UTF-8";

    //使用BufferedInputStream和BufferedOutputStream实现非文本文件的复制
    @Test
    public void bufferedInputOutputStreamTest() {
        String src = "src/main/resources/hello.txt";
        String target = "src/main/resources/hello1.txt";

        this.copyFileWithBuffer(src, target);

    }

    private void copyFileWithBuffer(String src, String target) {
        File from = new File(src);
        File to = new File(target);

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(from);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, CHARSET_UTF8);
            bufferedReader = new BufferedReader(inputStreamReader);

            FileOutputStream fileOutputStream = new FileOutputStream(to);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, CHARSET_UTF8);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                bufferedWriter.write(string);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void systemTest() {
        BufferedReader bufferedReader = null;
        try {
            InputStream in = System.in;
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            bufferedReader = new BufferedReader(inputStreamReader);
            String input = "";
            System.out.println("请输入：");
            while ((input = bufferedReader.readLine()) != null) {
                //input = bufferedReader.readLine();
                if (input.equalsIgnoreCase("e") || input.equalsIgnoreCase("exit")) {
                    break;
                }
                System.out.println(input.toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

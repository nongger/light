package com.darren.fresh.IO;

import org.junit.Test;

import java.io.*;

/**
 * 抽象基类			节点流（文件流）         缓冲流（处理流的一种,可以提升文件操作的效率）
 * InputStream		FileInputStream			BufferedInputStream
 * OutputStream		FileOutputStream		BufferedOutputStream    (flush())
 * Reader			FileReader				BufferedReader          (readLine())
 * Writer			FileWriter				BufferedWriter          (flush())
 *
 * @author Darren
 * @date 2018/5/8 21:04
 */
public class BufferedInputOutputStreamTest {

    @Test
    public void bufferedInputStreamTest() {
        long befoer = System.currentTimeMillis();
        String src = "src/main/resources/cute.jpg";
        String target = "D:\\config\\hellotarget101.jpg";
        copyFile(src, target);
        System.out.println(System.currentTimeMillis() - befoer);
    }

    @Test
    public void bufferedReaderWriterTest() {
        long befoer = System.currentTimeMillis();
        String src = "src/main/resources/hello.txt";
        String target = "D:\\config\\hellotarget1.txt";
        copyFileWithReaderWriter(src, target);
        System.out.println("文件复制耗时：" + (System.currentTimeMillis() - befoer) + "ms");
    }

    private void copyFileWithReaderWriter(String src, String target) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File fromFile = new File(src);
            File copyFile = new File(target);
            FileReader fileReader = new FileReader(fromFile);
            FileWriter fileWriter = new FileWriter(copyFile);
            bufferedReader = new BufferedReader(fileReader);
            bufferedWriter = new BufferedWriter(fileWriter);
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                bufferedWriter.write(string + "\n");
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
            }
        }


    }

    /**
     * BufferedInputStream BufferedOutputStream 属于处理流，低层采用数组，非阻塞读取，处理速度较快
     * 对于非文本文件一般采用字节流进行处理
     *
     * @param src
     * @param target
     */
    public void copyFile(String src, String target) {
        // 1.创建一个File类的对象。
        File fromFile = new File(src);
        File copyFile = new File(target);
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            // 2.创建相应的节点流：FileInputStream、FileOutputStream
            fileInputStream = new FileInputStream(fromFile);
            fileOutputStream = new FileOutputStream(copyFile);
            // 3.将创建的节点流的对象作为形参传递给缓冲流的构造器中
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            byte[] data = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(data)) != -1) {
                bufferedOutputStream.write(data, 0, len);
                bufferedOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedOutputStream != null)
                    bufferedOutputStream.close();
                if (bufferedInputStream != null)
                    bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

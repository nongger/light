package com.darren.fresh.IO;

import org.junit.Test;

import java.io.*;
import java.time.Duration;
import java.time.Instant;

/**
 * 1.流的分类：
 * 按照数据流向的不同：输入流  输出流
 * 按照处理数据的单位的不同：字节流  字符流（处理的文本文件）
 * 按照角色的不同：节点流（直接作用于文件的）  处理流
 * <p>
 * 2.IO的体系
 * 抽象基类			节点流（文件流）         缓冲流（处理流的一种）
 * InputStream		FileInputStream			BufferedInputStream
 * OutputStream		FileOutputStream		BufferedOutputStream
 * Reader			FileReader				BufferedReader
 * Writer			FileWriter				BufferedWriter
 *
 * @author Darren
 * @date 2018/5/7 21:11
 */
public class FileInputOutputStreamTest {

    /**
     * 从硬盘存在的一个文件中，读取其内容到程序中。使用FileInputStream
     * 要读取的文件一定要存在。否则抛FileNotFoundException
     */
    @Test
    public void fileInputStreamTest() {
        FileInputStream fileInputStream = null;
        try {
            // 1.创建一个File类的对象。
            File file = new File("src/main/resources/hello.txt");
            // 2.创建一个FileInputStream类的对象
            fileInputStream = new FileInputStream(file);
            /**
             * 3.调用FileInputStream的方法，实现file文件的读取。
             * read():读取文件的一个字节。当执行到文件结尾时，返回-1
             */
            int read;
            while ((read = fileInputStream.read()) != -1) {
                System.out.print((char) read);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 4.关闭相应的流
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
            }
        }
    }

    @Test
    public void fileInputStreamTestUP() {
        FileInputStream fileInputStream = null;
        try {
            // 1.创建一个File类的对象。
            File file = new File("src/main/resources/hello.txt");
            // 2.创建一个FileInputStream类的对象
            fileInputStream = new FileInputStream(file);

            byte[] data = new byte[5];//每次读取的最大长度
            int len;
            while ((len = fileInputStream.read(data)) != -1) {//每次读取的实际长度
                String result = new String(data, 0, len);//使用len不可使用data.length数组是引用类型，防止最后读取位数不足，脏数据影响
                System.out.print(result);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 4.关闭相应的流
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
            }
        }
    }

    @Test
    public void fileOutputTest() {
        File copyFile = new File("D:\\darren\\io\\hellocopy.txt");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(copyFile);
            fileOutputStream.write("I am the somebody!".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void fileCopyTest() {
        Instant before = Instant.now();
        String src = "src/main/resources/cute.jpg";
        String target = "D:\\darren\\io\\hellotarget.jpg";

        copyFileUtils(src, target);
        Instant after = Instant.now();
        System.out.println("文件复制结束，耗时："+Duration.between(before, after).toMillis()+"ms");
    }

    /**
     * 文件复制工具
     * @param src
     * @param target
     */
    public void copyFileUtils(String src, String target) {
        // 1.创建一个File类的对象。
        File fromFile = new File(src);
        File copyFile = new File(target);
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            // 2.创建一个FileInputStream类的对象
            fileInputStream = new FileInputStream(fromFile);
            fileOutputStream = new FileOutputStream(copyFile);

            byte[] data = new byte[1024];//每次读取的最大长度
            int len;
            while ((len = fileInputStream.read(data)) != -1) {//每次读取的实际长度
//                String result = new String(data, 0, len);//使用len不可使用data.length数组是引用类型，防止最后读取位数不足，脏数据影响
//                fileOutputStream.write(result.getBytes());
                fileOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

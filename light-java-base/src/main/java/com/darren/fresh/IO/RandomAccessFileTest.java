package com.darren.fresh.IO;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * RandomAccessFile:支持随机访问
 * 1.既可以充当一个输入流，有可以充当一个输出流
 * 2.支持从文件的开头读取、写入
 * 3.支持从任意位置的读取、写入（插入）
 *
 * @author Darren
 * @date 2018/5/14
 */
public class RandomAccessFileTest {

    @Test
    public void testInsert() {
        String source = "src/main/resources/hello.txt";
        String msg = "helloWorld";
        insertIntoFile(source, 5, msg);
    }

    /**
     * 更通用的文件插入操作
     *
     * @param source
     * @param index
     * @param msg
     */
    public void insertIntoFile(String source, int index, String msg) {
        RandomAccessFile rw = null;
        try {
            rw = new RandomAccessFile(new File(source), "rw");
            rw.seek(index);
            System.out.println("从位置：" + rw.getFilePointer() + "开始插入");//获取当前指针位置
            byte[] data = new byte[512];
            int len;
            StringBuffer stringBuffer = new StringBuffer();
            while ((len = rw.read(data)) != -1) {
                stringBuffer.append(new String(data, 0, len));
            }
            System.out.println("当前指针位置：" + rw.getFilePointer());
            rw.seek(index);
            rw.write(msg.getBytes());
            rw.write(stringBuffer.toString().getBytes());
            System.out.println("文件插入完成！");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rw != null)
                    rw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //实现插入的效果：在d字符后面插入“xy”,适用于文件为单行的情况，不通用
    @Test
    public void insert() {
        File file = new File("src/main/resources/hello.txt");

        RandomAccessFile rw = null;
        try {
            rw = new RandomAccessFile(file, "rw");
            rw.seek(3);
            System.out.println(rw.getFilePointer());//获取当前指针位置
            String line = rw.readLine();
            System.out.println(rw.getFilePointer());
            rw.seek(3);
            rw.write("xy".getBytes());
            rw.write(line.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rw != null)
                    rw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //实现的实际上是覆盖的效果
    @Test
    public void replace() {
        File file = new File("src/main/resources/hello.txt");

        RandomAccessFile rw = null;
        try {
            rw = new RandomAccessFile(file, "rw");
            rw.seek(3);
            rw.write("xy".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rw != null)
                    rw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void copyTest() {
        File file = new File("src/main/resources/hello.txt");
        File targetFile = new File("src/main/resources/helloc.txt");
        RandomAccessFile accessFile = null;
        RandomAccessFile writeFile = null;
        try {
            accessFile = new RandomAccessFile(file, "r");
            writeFile = new RandomAccessFile(targetFile, "rw");

            byte[] data = new byte[128];
            int len;
            while ((len = accessFile.read(data)) != -1) {
                writeFile.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (accessFile != null)
                    accessFile.close();
                if (writeFile != null)
                    writeFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

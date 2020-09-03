package com.darren.fresh.IO;

import com.darren.utils.DateTools;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

/**
 * @author Darren
 * @date 2018/5/11
 */
public class OtherStreamTest {

    @Test
    public void classPath(){
        try {
            // 类路径下文件加载方式
            InputStream fpfx = new ClassPathResource("hello.txt").getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 数据流：用来处理基本数据类型、String、字节数组的数据:DataInputStream DataOutputStream
     */
    @Test
    public void dataInputOutputStream() {
        String src = "src/main/resources/data.txt";
        File file = new File(src);
        DataOutputStream dataOutputStream = null;
        try {
            dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            dataOutputStream.writeBoolean(true);
            dataOutputStream.writeUTF("I embrace the world");
            dataOutputStream.writeLong(DateTools.getCurrentMsecTimestamp());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataOutputStream != null)
                    dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //数据读取
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(new FileInputStream(file));
            boolean readBoolean = dataInputStream.readBoolean();
            String readUTF = dataInputStream.readUTF();
            long readLong = dataInputStream.readLong();
            System.out.println(readBoolean + " " + readUTF + " " + readLong);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataInputStream != null)
                    dataInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    // 打印流：字节流：PrintStream 字符流：PrintWriter
    @Test
    public void printStreamTest() {
        String src = "src/main/resources/hellokitty.txt";

        PrintStream printStream = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(src));

            // 创建打印输出流,设置为自动刷新模式(写入换行符或字节 '\n' 时都会刷新输出缓冲区)
            printStream = new PrintStream(fileOutputStream, true);
            System.setOut(printStream);// 把标准输出流(控制台输出)改成文件

            for (int i = 0; i <= 255; i++) { // 输出ASCII字符
                System.out.print((char) i);
                if (i % 50 == 0) { // 每50个数据一行
                    System.out.println(); // 换行
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (printStream != null)
                printStream.close();
        }
    }

}

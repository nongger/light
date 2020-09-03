package com.darren.fresh.IO;

import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 使用FileReader、FileWriter 可以实现文本文件的复制。
 * 对于非文本文件（视频文件、音频文件、图片），只能使用字节流！
 *
 * @author Darren
 * @date 2018/5/8
 */
public class FileReaderWriterTest {
    @Test
    public void fileReader() {
        FileReader fileReader = null;
        try {
            // 1.创建一个File类的对象。
            File file = new File("src/main/resources/hello.txt");
            fileReader = new FileReader(file);

            char[] data = new char[512];
            int len;
            while ((len = fileReader.read(data)) != -1) {
                String text = new String(data, 0, len);
                System.out.println(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null)
                    fileReader.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 实现文件的复制
     */
    @Test
    public void fileReaderAndWriter() {
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        try {
            // 1.创建一个File类的对象。
            File fromfile = new File("src/main/resources/hello.txt");
            File targetfile = new File("src/main/resources/hello1.txt");
            //1.输入流对应的文件fromfile一定要存在，否则抛异常。输出流对应的文件targetfile可以不存在，执行过程中会自动创建
            fileReader = new FileReader(fromfile);
            fileWriter = new FileWriter(targetfile);

            char[] data = new char[512];
            int len;
            while ((len = fileReader.read(data)) != -1) {
                fileWriter.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
                if (fileReader != null)
                    fileReader.close();
            } catch (IOException e) {
            }
        }
    }

}

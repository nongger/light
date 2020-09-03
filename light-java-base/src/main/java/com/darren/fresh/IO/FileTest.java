package com.darren.fresh.IO;

import com.darren.utils.DateTools;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * java IO流之File
 * java.io.File类
 * 1.凡是与输入、输出相关的类、接口等都定义在java.io包下
 * 2.File是一个类，可以有构造器创建其对象。此对象对应着一个文件（.txt .avi .doc .ppt .mp3 .jpg）或文件目录
 * 3.File类对象是与平台无关的。
 * 4.File中的方法，仅涉及到如何创建、删除、重命名等等。只要涉及文件内容的，File是无能为力的，必须由io流来完成。
 * 5.File类的对象常作为io流的具体类的构造器的形参。
 *
 * @author Darren
 * @date 2018/5/6
 */
public class FileTest {

    /**
     * createNewFile()
     * delete()
     * mkDir():创建一个文件目录。只有在上层文件目录存在的情况下，才能返回true
     * mkDirs():创建一个文件目录。若上层文件目录不存在，一并创建
     * list()
     * listFiles()
     */
    @Test
    public void fileOrperate() {
        File absoultFile = new File("D:\\darren\\io\\helloworld.txt");
        File fileDir = new File("D:\\darren\\io\\io2");

        try {
            if (absoultFile.exists()) {
                System.out.println("删除文件 ：" + absoultFile.delete());
            } else {
                System.out.println("创建文件： " + absoultFile.createNewFile());
            }

            System.out.println("创建文件夹" + fileDir.mkdir());//创建一个文件目录。只有在上层文件目录存在的情况下，才能返回true
            System.out.println("创建上层文件夹不存在的文件夹：" + fileDir.mkdirs());//创建一个文件目录。若上层文件目录不存在，一并创建

            File fileDir1 = new File("D:\\darren\\io");
            String[] list = fileDir1.list();
            Arrays.stream(list).forEach(System.out::println);

            System.out.println("============");
            File[] files = fileDir1.listFiles();
            Arrays.stream(files).map(File::getName)
                    .forEach(System.out::println);

            List<File> collect = Arrays.stream(files)
                    .filter(file -> "back.txt".equals(file.getName()))
                    .collect(Collectors.toList());
            System.out.println("过滤一个文件：" + collect.get(0).getName());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * exists()
     * canWrite()
     * canRead()
     * isFile()
     * isDirectory()
     * lastModified()
     * length()
     */
    @Test
    public void fileBaseTest() {
        File file = new File("src/main/resources/hello.txt");
        System.out.println(file.getName());

        System.out.println("文件是否存在：" + file.exists());
        System.out.println("文件是否可读：" + file.canRead());
        System.out.println("文件是否可写：" + file.canWrite());
        System.out.println("file是否文件：" + file.isFile());
        System.out.println("file是否目录：" + file.isDirectory());
        System.out.println("文件最后修改时间：" + DateTools.dateToString(new Date(file.lastModified())));//最后修改时间
        System.out.println("文件长度：" + file.length());

        System.out.println("==========");
        File fileDir = new File("D:\\darren\\io\\io1");

        System.out.println("文件是否存在：" + fileDir.exists());
        System.out.println("文件是否可读：" + fileDir.canRead());
        System.out.println("文件是否可写：" + fileDir.canWrite());
        System.out.println("file是否文件：" + fileDir.isFile());
        System.out.println("file是否目录：" + fileDir.isDirectory());
        System.out.println("文件最后修改时间：" + DateTools.dateToString(new Date(fileDir.lastModified())));//最后修改时间
        System.out.println("文件长度：" + fileDir.length());
    }

    /**
     * 路径：
     * 绝对路径：包括盘符在内的完整的文件路径
     * 相对路径：在当前文件目录下的文件的路径
     * <p>
     * getName()
     * getPath()
     * getAbsoluteFile()
     * getAbsolutePath()
     * getParent()
     * renameTo(File newName)
     */
    @Test
    public void fileTest() {
        File file = new File("src/main/resources/hello.txt");
        File fileDir = new File("D:\\darren\\io\\io1");

        System.out.println(file.getName());
        System.out.println(file.getPath());
        System.out.println(file.getAbsoluteFile());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getParent());

        System.out.println("==============");

        System.out.println(fileDir.getName());
        System.out.println(fileDir.getPath());
        System.out.println(fileDir.getAbsoluteFile());
        System.out.println(fileDir.getAbsolutePath());
        System.out.println(fileDir.getParent());

    }

}

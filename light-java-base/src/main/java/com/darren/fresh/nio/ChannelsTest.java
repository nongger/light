package com.darren.fresh.nio;

import com.darren.utils.CommonLog;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.SortedMap;

/**
 * NIO 通道练习
 * 一、通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 * <p>
 * 二、通道的主要实现类
 * java.nio.channels.Channel 接口：
 * |--FileChannel
 * |--SocketChannel
 * |--ServerSocketChannel
 * |--DatagramChannel
 * <p>
 * 三、获取通道
 * 1. Java 针对支持通道的类提供了 getChannel() 方法
 * 本地 IO：
 * FileInputStream/FileOutputStream
 * RandomAccessFile
 * <p>
 * 网络IO：
 * Socket
 * ServerSocket
 * DatagramSocket
 * <p>
 * 2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 * <p>
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 * <p>
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 * <p>
 * 六、字符集：Charset
 * 编码：字符串 -> 字节数组
 * 解码：字节数组  -> 字符串
 *
 * @author Darren
 * @date 2018/7/16 9:16
 */
public class ChannelsTest {

    @Test
    public void charsetCoderTest() throws CharacterCodingException {
        Charset gbkSet = Charset.forName("GBK");
        CharsetEncoder charsetEncoder = gbkSet.newEncoder();
        CharsetDecoder charsetDecoder = gbkSet.newDecoder();
        CharBuffer charBuffer = CharBuffer.allocate(128);
        charBuffer.put("通道（Channel）：用于源节点与目标节点的连接。");

        charBuffer.flip();
        ByteBuffer encode = charsetEncoder.encode(charBuffer);
        for (int i = 0; i < encode.limit(); i++) {
            System.out.println(encode.get());
        }
        System.out.println(encode.position());
        encode.flip();
        CharBuffer decodeStr = charsetDecoder.decode(encode);
        System.out.println(decodeStr.toString());

        encode.flip();
        Charset utf8charset = Charset.forName("UTF-8");
        CharsetDecoder utfDecoder = utf8charset.newDecoder();
        CharBuffer decodeByUTF = utf8charset.decode(encode);
        System.out.println(decodeByUTF.toString());

    }

    @Test
    public void charsetTest() {
        SortedMap<String, Charset> charsetMap = Charset.availableCharsets();
        for (Map.Entry entry : charsetMap.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    @Test
    public void scatterAndGather() {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            RandomAccessFile source = new RandomAccessFile("src/main/resources/hello.txt", "rw");
            inChannel = source.getChannel();

            ByteBuffer byteBuffer1 = ByteBuffer.allocate(128);
            ByteBuffer byteBuffer2 = ByteBuffer.allocate(256);
            ByteBuffer[] bufs = {byteBuffer1, byteBuffer2};

            inChannel.read(bufs);
            for (ByteBuffer bs : bufs) {
                bs.flip();
            }
            System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
            System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));
            System.out.println(bufs[0].position());

            RandomAccessFile target = new RandomAccessFile("src/main/resources/hellocopy.txt", "rw");
            outChannel = target.getChannel();

            outChannel.write(bufs);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outChannel.close();
                inChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //通道之间的数据传输(直接缓冲区)
    @Test
    public void transferTest() {
        Instant before = Instant.now();
        FileChannel readFileChannel = null;
        FileChannel WriteFileChannel = null;
        try {
            readFileChannel = FileChannel.open(Paths.get("src/main/resources/cute.jpg"),
                    StandardOpenOption.READ);
            // CREATE 不存在创建，存在覆盖；CREATE_NEW存在则报错
            WriteFileChannel = FileChannel.open(Paths.get("src/main/resources/", Instant.now().toEpochMilli() + "31.jpg"),
                    StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

            readFileChannel.transferTo(0, readFileChannel.size(), WriteFileChannel);
            WriteFileChannel.transferFrom(readFileChannel, 0, readFileChannel.size());

        } catch (IOException e) {
            CommonLog.error("文件操作出现错误：{}", e.getMessage());
        } finally {
            CommonLog.info("复制文件结束");
            try {
                readFileChannel.close();
                WriteFileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Instant after = Instant.now();
        System.out.println(Duration.between(before, after).toMillis());
    }

    // 直接缓冲区复制文件（内存映射文件）
    @Test
    public void copyWithDirecte() {
        Instant before = Instant.now();
        FileChannel readFileChannel = null;
        FileChannel WriteFileChannel = null;
        try {
            readFileChannel = FileChannel.open(Paths.get("src/main/resources/cute.jpg"),
                    StandardOpenOption.READ);
            // CREATE 不存在创建，存在覆盖；CREATE_NEW存在则报错
            WriteFileChannel = FileChannel.open(Paths.get("src/main/resources/", Instant.now().toEpochMilli() + ".jpg"),
                    StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

            // 内存映射
            MappedByteBuffer inMap = readFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, readFileChannel.size());
            MappedByteBuffer outMap = WriteFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, readFileChannel.size());

            // 直接对缓冲区进行读写
            byte[] bytes = new byte[inMap.limit()];
            inMap.get(bytes);
            outMap.put(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CommonLog.info("复制文件结束");
            try {
                readFileChannel.close();
                WriteFileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Instant after = Instant.now();
        System.out.println(Duration.between(before, after).toMillis());
    }


    // 使用通道完成文件复制（非直接缓冲区）
    @Test
    public void copyFileWithChannelTest() {
        long before = System.currentTimeMillis();

        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel inputStreamChannel = null;
        FileChannel outputStreamChannel = null;
        try {
            fileInputStream = new FileInputStream("src/main/resources/cute.jpg");
            fileOutputStream = new FileOutputStream("src/main/resources/" + Instant.now().toEpochMilli() + ".jpg");

            inputStreamChannel = fileInputStream.getChannel();
            outputStreamChannel = fileOutputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (inputStreamChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();// 切换读取模式
                outputStreamChannel.write(byteBuffer);
                byteBuffer.clear();// 清空缓冲区
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStreamChannel.close();
                outputStreamChannel.close();
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println(System.currentTimeMillis() - before);
    }


}

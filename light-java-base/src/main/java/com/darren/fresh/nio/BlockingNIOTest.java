package com.darren.fresh.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

/**
 * 一、使用 NIO 完成网络通信的三个核心：
 *
 * 1. 通道（Channel）：负责连接
 *
 * 	   java.nio.channels.Channel 接口：
 * 			|--SelectableChannel
 * 				|--SocketChannel
 * 				|--ServerSocketChannel
 * 				|--DatagramChannel
 *
 * 				|--Pipe.SinkChannel
 * 				|--Pipe.SourceChannel
 *
 * 2. 缓冲区（Buffer）：负责数据的存取
 *
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 * @author Darren
 * @date 2018/7/21 23:00
 */
public class BlockingNIOTest {
    @Test
    public void client() {
        SocketChannel socketChannel = null;
        FileChannel fileChannel = null;
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
            fileChannel = FileChannel.open(Paths.get("src/main/resources/cute.jpg"), StandardOpenOption.READ);

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (fileChannel.read(buffer) != -1) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }

            socketChannel.shutdownOutput();
            // 接收反馈
            while (socketChannel.read(buffer)!=-1){
                buffer.flip();
                System.out.println(new String(buffer.array(),0,buffer.limit()));
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileChannel.close();
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void server() {
        ServerSocketChannel serverSocketChannel = null;
        FileChannel fileChannel = null;
        SocketChannel accept = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();

            fileChannel = FileChannel.open(Paths.get("src/main/resources/", Instant.now().toEpochMilli() + ".jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);

            serverSocketChannel.bind(new InetSocketAddress(9898));
            accept = serverSocketChannel.accept();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (accept.read(buffer) != -1) {
                buffer.flip();
                fileChannel.write(buffer);
                buffer.clear();
            }

            accept.shutdownInput();
            // 反馈接收状态
            buffer.put("文件接收成功！".getBytes());
            buffer.flip();
            accept.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                accept.close();
                fileChannel.close();
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

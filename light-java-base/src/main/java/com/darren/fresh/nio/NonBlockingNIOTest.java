package com.darren.fresh.nio;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * @author Darren
 * @date 2018/7/22 23:03
 */
public class NonBlockingNIOTest {
    @Test
    public void client() {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("serverTime", LocalDateTime.now());

            // 切换非阻塞模式
            socketChannel.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            buffer.put(jsonObject.toString().getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void server() {
        ServerSocketChannel serverSocketChannel = null;
        try {
            //1. 获取通道
            serverSocketChannel = ServerSocketChannel.open();

            //2. 切换非阻塞模式
            serverSocketChannel.configureBlocking(false);

            //3. 绑定连接
            serverSocketChannel.bind(new InetSocketAddress(9898));

            //4. 获取选择器
            Selector selector = Selector.open();
            //5. 将通道注册到选择器上, 并且指定“监听接收事件”
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            //6. 轮询式的获取选择器上已经“准备就绪”的事件
            while (selector.select() > 0) {
                //7. 获取当前选择器中所有注册的“选择键(已就绪的监听事件)”
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    //8. 获取准备“就绪”的是事件
                    SelectionKey next = iterator.next();
                    //9. 判断具体是什么事件准备就绪
                    if (next.isAcceptable()) {
                        SocketChannel accept = serverSocketChannel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);
                    } else if (next.isReadable()) {
                        SocketChannel channel = (SocketChannel) next.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        while (channel.read(buffer) != -1) {
                            buffer.flip();
                            System.out.println(new String(buffer.array(), 0, buffer.limit()));
                            buffer.clear();
                        }

                    }
                    //15. 取消选择键 SelectionKey
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

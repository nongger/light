package com.darren.fresh.nio;

import com.darren.utils.DateTools;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * @author Darren
 * @date 2018/7/23 22:53
 */
public class DatagramChannelTest {
    @Test
    public void send() {
        DatagramChannel datagramChannel = null;
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //Scanner scanner = new Scanner(System.in);
            //while (scanner.hasNext()) {
            //    String str = scanner.next();
            //}
            buffer.put((DateTools.getCurrentDateYYYYMMDDHHMMSSsss() + "\n" + "哈哈哈").getBytes());
            buffer.flip();
            datagramChannel.send(buffer, new InetSocketAddress("127.0.0.1", 9898));
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                datagramChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void accept() {

        DatagramChannel get = null;
        try {
            get = DatagramChannel.open();
            get.configureBlocking(false);

            get.bind(new InetSocketAddress(9898));
            Selector selector = Selector.open();
            get.register(selector, SelectionKey.OP_READ);

            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    if (next.isReadable()) {
                        ByteBuffer receiveBuf = ByteBuffer.allocate(512);
                        get.receive(receiveBuf);
                        receiveBuf.flip();
                        System.out.println(new String(receiveBuf.array(), 0, receiveBuf.limit()));
                        receiveBuf.clear();
                    }
                }
                iterator.remove();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                get.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

package com.darren.fresh.javaNet;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP传输协议测试
 * @author Darren
 * @date 2018/4/22
 */
public class UDPTest {

    @Test
    public void send() {
        DatagramSocket sendSocket = null;
        try {

            sendSocket = new DatagramSocket();
            byte[] msg = "数据包传输".getBytes();
            DatagramPacket packet = new DatagramPacket(msg, 0, msg.length, InetAddress.getByName("127.0.0.1"), 9494);
            sendSocket.send(packet);

        } catch (IOException e) {

        } finally {
            if (sendSocket != null)
                sendSocket.close();
        }
    }

    @Test
    public void recever() {
        DatagramSocket recever = null;
        try {
            recever = new DatagramSocket(9494);
            byte[] data = new byte[1024];
            DatagramPacket getDate = new DatagramPacket(data, 0, data.length);
            recever.receive(getDate);

            String msg = new String(getDate.getData(), 0, getDate.getLength());
            System.out.println(msg);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            recever.close();
        }
    }


}

package com.darren.fresh.javaNet;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket练习，实现客户端服务端的交互
 *
 * @author Darren
 * @date 2018/4/22
 */
public class TCPExecrse {

    @Test
    public void client() {
        Socket client = null;
        OutputStream outputStream = null;
        try {
            client = new Socket(InetAddress.getByName("127.0.0.1"), 9090);
            outputStream = client.getOutputStream();
            byte[] send = "I'm the mesage, hia hia hia".getBytes();
            outputStream.write(send, 0, send.length);
            client.shutdownOutput();//表示发送完成

            InputStream anser = client.getInputStream();
            byte[] anserByte = new byte[512];
            int len = 0;

            while ((len = anser.read(anserByte)) != -1) {
                String anserStr = new String(anserByte, 0, len);
                System.out.println("收到服务端回复：" + anserStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != outputStream)
                    outputStream.close();
                if (null != client)
                    client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Test
    public void ToUpperStringServer() {
        ServerSocket serverSocket = null;
        Socket accept = null;
        InputStream inputStream = null;
        OutputStream reply = null;
        try {
            serverSocket = new ServerSocket(9090);
            accept = serverSocket.accept();
            inputStream = accept.getInputStream();
            byte[] accMsg = new byte[5];
            int length = 0;
            String receive = "";//可以接收完整信息，而不局限与字符数组长度
            while ((length = inputStream.read(accMsg)) != -1) { //读取到流的末端
                String temp = new String(accMsg, 0, length);
                receive += temp;
                System.out.println(temp);
            }
            System.out.println("收到客户端消息：" + receive);


            reply = accept.getOutputStream();
            reply.write(receive.toUpperCase().getBytes());


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reply)
                    reply.close();
                if (null != inputStream)
                    inputStream.close();
                if (null != accept)
                    accept.close();
                if (null != serverSocket)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}

package com.darren.fresh.javaNet;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP编程例二：客户端给服务端发送信息。服务端输出此信息到控制台上，并返回客户端
 * 网络编程实际上就是Socket的编程
 *
 * @author Darren
 * @date 2018/4/19
 */
public class TCPDemoTest {

    @Test
    public void client() {
        Socket client = null;
        OutputStream sendMassage = null;
        InputStream back = null;
        try {
            // 1.创建一个Socket的对象，通过构造器指明服务端的IP地址，以及其接收程序的端口号
            client = new Socket(InetAddress.getByName("127.0.0.1"), 9494);
            // 2.getOutputStream()：发送数据，方法返回OutputStream的对象
            sendMassage = client.getOutputStream();
            // 3.具体的输出过程
            sendMassage.write("正在通信请勿关闭……".getBytes());
            //shutdownOutput():执行此方法，显式的告诉服务端发送完毕！
            client.shutdownOutput();

            back = client.getInputStream();
            // 4.对获取的输入流进行的操作
            byte[] result = new byte[30];
            int len = 0;
            while ((len = back.read(result)) != -1) {
                String retstr = new String(result, 0, len);
                System.out.println(retstr);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4.关闭相应的流和Socket对象
            try {
                if (sendMassage != null) {
                    sendMassage.close();
                }
                if (client != null) {
                    client.close();
                }
                if (back != null)
                    back.close();
            } catch (IOException e) {
            }
        }
    }

    @Test
    public void server() {
        ServerSocket serverSocket = null;
        Socket accept = null;
        InputStream acceptInputStream = null;
        OutputStream replay = null;
        try {
            // 1.创建一个ServerSocket的对象，通过构造器指明自身的端口号（ip无需指定，服务一开启自动声明IP）
            serverSocket = new ServerSocket(9494);
            // 2.调用其accept()方法，返回一个Socket的对象
            accept = serverSocket.accept();
            // 3.调用Socket对象的getInputStream()获取一个从客户端发送过来的输入流
            acceptInputStream = accept.getInputStream();
            // 4.对获取的输入流进行的操作
            byte[] result = new byte[30];
            int len = 0;
            while ((len = acceptInputStream.read(result)) != -1) {
                String retstr = new String(result, 0, len);
                System.out.println(retstr);
            }
            System.out.println("信息来自：" + accept.getInetAddress().getHostName());

            replay = accept.getOutputStream();
            replay.write("已收到信息……".getBytes());

            accept.shutdownInput();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 5.关闭相应的流以及Socket、ServerSocket的对象
            try {
                if (null != acceptInputStream)
                    acceptInputStream.close();
                if (null != accept)
                    accept.close();
                if (null != serverSocket)
                    serverSocket.close();
                if (null != replay)
                    replay.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

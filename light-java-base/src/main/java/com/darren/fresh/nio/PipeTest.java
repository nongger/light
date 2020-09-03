package com.darren.fresh.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author Darren
 * @date 2018/7/24 8:53
 */
public class PipeTest {
    private Pipe pipe;

    public PipeTest(Pipe pipe) {
        this.pipe = pipe;
    }

    public static void main(String[] args) throws IOException {
        // 两个线程分别对管道内的数据进行读写操作
        PipeTest pipeTest = new PipeTest(Pipe.open());
        new Thread(pipeTest::sinkTest).start();
        new Thread(pipeTest::sourceTest).start();
    }


    public void sinkTest() {
        Pipe.SinkChannel sink = null;
        try {
            //pipe = Pipe.open();
            ByteBuffer buffer = ByteBuffer.allocate(512);
            buffer.put("通过管道传输".getBytes());
            buffer.flip();
            sink = pipe.sink();
            sink.write(buffer);
            System.out.println("发送完毕");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sink.close();
            } catch (IOException e) {
            }
        }
    }

    public void sourceTest() {
        Pipe.SourceChannel source = null;
        try {
            ByteBuffer buffer = ByteBuffer.allocate(512);
            source = pipe.source();
            source.read(buffer);
            buffer.flip();
            System.out.println("接收数据完毕！");
            System.out.println(new String(buffer.array(), 0, buffer.limit()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                source.close();
            } catch (IOException e) {
            }
        }
    }


}

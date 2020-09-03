package com.darren.fresh.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2018/7/15 16:31
 * Desc   : NIO 使用 --缓冲区（Buffer）
 * 一、缓冲区（Buffer）：在 Java NIO 中负责数据的存取。缓冲区就是数组。用于存储不同数据类型的数据
 * <p>
 * 根据数据类型不同（boolean 除外），提供了相应类型的缓冲区：
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * <p>
 * 上述缓冲区的管理方式几乎一致，通过 allocate() 获取缓冲区
 * <p>
 * 二、缓冲区存取数据的两个核心方法：
 * put() : 存入数据到缓冲区中
 * get() : 获取缓冲区中的数据
 * <p>
 * 三、缓冲区中的四个核心属性：
 * capacity : 容量，表示缓冲区中最大存储数据的容量。一旦声明不能改变。
 * limit : 界限，表示缓冲区中可以操作数据的大小。（limit 后数据不能进行读写）
 * position : 位置，表示缓冲区中正在操作数据的位置。
 * mark : 标记，表示记录当前 position 的位置。可以通过 reset() 恢复到 mark 的位置
 * <p>
 * 0 <= mark <= position <= limit <= capacity
 * <p>
 * 四、直接缓冲区与非直接缓冲区：
 * 非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
 * 直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 */
public class BufferTest {


    @Test
    public void allocateTest() {
        String str = "hello world";

        // 分配缓冲区大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("-----------after allocate------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        // 缓冲区写入
        byteBuffer.put(str.getBytes());
        System.out.println("-----------after put------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        // 切换到读取模式
        byteBuffer.flip();
        System.out.println("-----------after flip------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        // 读取缓冲区
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println("-----------after get------------");
        System.out.println(new String(bytes, 0, bytes.length));
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        // 可重复读
        byteBuffer.rewind();
        System.out.println("-----------after rewind------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        // 清空缓冲区，数据仍然存在,不过出于被遗忘状态
        byteBuffer.clear();
        System.out.println("-----------after clear------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println((char) byteBuffer.get());

    }

    @Test
    public void markTest() {
        String str = "hello world";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.put(str.getBytes());
        byteBuffer.flip();

        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes, 0, 2);
        System.out.println(new String(bytes));
        System.out.println(byteBuffer.position());

        // 标记
        byteBuffer.mark();
        System.out.println(byteBuffer.position());
        // 读取操作后位置发生改变
        byteBuffer.get(bytes, 2, 2);
        System.out.println(new String(bytes));
        System.out.println(byteBuffer.position());

        // 恢复到remark的位置
        byteBuffer.reset();
        System.out.println(byteBuffer.position());

        // 判断缓冲区是否还有剩余数据
        if (byteBuffer.hasRemaining()) {
            // 缓冲区剩余的可以操作的数量
            System.out.println(byteBuffer.remaining());
        }

        // 创建直接缓冲区，建立在物理内存中。可以提高效率
        ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(1024);
        System.out.println(byteBuffer1.isDirect());
    }

}

package com.darren.interview.collection;

import org.junit.Test;

import java.util.StringJoiner;

/**
 * @author Darren
 * @date 2019/3/26 23:17
 * 字符串拼接效率对比：StringBuilder < StringBuffer < concat < + < StringUtils.join
 * 直接使用StringBuilder的方式是效率最高的。因为StringBuilder天生就是设计来定义可变字符串和字符串的变化操作的。
 * <p>
 * 但是，还要强调的是：
 * 1、如果不是在循环体中进行字符串拼接的话，直接使用+就好了。
 * 2、如果在并发场景中进行字符串拼接的话，要使用StringBuffer来代替StringBuilder。
 * <p>
 * StringJoiner其实是通过StringBuilder实现的，所以他的性能和StringBuilder差不多，他也是非线程安全的。
 * 如果日常开发中中，需要进行字符串拼接，如何选择？
 * 1、如果只是简单的字符串拼接，考虑直接使用"+"即可。
 * 2、如果是在for循环中进行字符串拼接，考虑使用StringBuilder和StringBuffer。
 * 3、如果是通过一个集合（如List）进行字符串拼接，则考虑使用StringJoiner。
 * 4、如果是对一组数据进行拼接，则可以考虑将其转换成Stream，并使用StringJoiner处理。
 */
public class StringTest {


    @Test
    public void stringConcat() {
        //Object

        // stringBuilder线程不安全，效率高,内部数组不一定是满的，如果添加的长度超过容量会进行扩容
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("已使用长度：" + stringBuilder.length() + " 容量：" + stringBuilder.capacity());
        // 演示扩容机制 int newCapacity = (value.length << 1) + 2; 扩大为原长度的2倍 + 2
        stringBuilder.append("world").append("world").append("world").append("world");
        stringBuilder.reverse();
        System.out.println("已使用长度：" + stringBuilder.length() + " 容量：" + stringBuilder.capacity());
        System.out.println(stringBuilder.toString());
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        System.out.println(stringBuilder.toString());


        // StringBuffer线程安全，方法加了同步锁
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("hello");
        System.out.println(stringBuffer.toString());

        String string = "hello ";
        string = string.concat("python");
        string = String.join(",", "ehcache", string);
        System.out.println(string);

        // 使用idea查看编译后的文件：(new StringBuilder()).append(string).append(" http jvm Thread ").append(concurrency).append(s1).toString()
        // 如果是在for循环中，每次都是new了一个StringBuilder，然后再把String转成StringBuilder，再进行append。
        String concurrency = "concurrency ";
        String s1 = "micro service";
        String s = string + " http " + "jvm " + "Thread " + concurrency + s1;
        //System.out.println(s);


        // StringJoiner是java1.8提供的新功能，方便进行字符串的拼接
        StringJoiner joiner = new StringJoiner(",");
        //
        //try {
        //    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //    System.out.println(bufferedReader.readLine());
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    @Test
    public void lenth() {
        String mtr = "secondPerfData";
        System.out.println(mtr.length() * 70000);
    }

    /**
     * 字符串intern方法，
     * 如果字符串池已经包含此String对象的字符串，则返回池中的字符串。 否则，将此String对象添加到池中，并返回对此String对象的引用。
     */
    @Test
    public void internTest() {
        String stringIntern = new StringBuilder("hello").append("world").toString();
        System.out.println(stringIntern);
        System.out.println(stringIntern.intern());
        System.out.println(stringIntern == stringIntern.intern());

        /**
         * "java"字符串的特殊性是由于jvm加载System时会调用initializeSystemClass()来进行初始化
         * 方法中会调用sun.misc.Version.init()方法
         * Version类中有基础常量包含了private static final String launcher_name = "java";
         *
         */
        String javaString = new StringBuilder("ja").append("va").toString();
        System.out.println(javaString);
        System.out.println(javaString.intern());
        System.out.println(javaString == javaString.intern());
    }


}

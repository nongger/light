package com.darren.designPattern;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2018/8/1 18:28
 * Desc   : 破坏单例模式
 * 通过反射和序列化可以破坏单例的单例性
 */
public class BrokenSingleton {

    /**
     * 授权的客户端可以通过反射来调用私有构造方法，借助于AccessibleObject.setAccessible方法即可做到 。
     * 如果需要防范这种攻击，请修改构造函数，使其在被要求创建第二个实例时抛出异常。
     *
     * @throws Exception
     */
    @Test
    public void brokeOne() throws Exception {
        SingletonWithfield instance1 = SingletonWithfield.getINSTANCE();
        SingletonWithfield instance2 = SingletonWithfield.getINSTANCE();
        System.out.println(instance1.equals(instance2));
        Class<SingletonWithfield> singletonWithfieldClass = SingletonWithfield.class;
        Constructor<SingletonWithfield> declaredConstructor = singletonWithfieldClass.getDeclaredConstructor();
        // 通过反射改变构造方法的可访问性
        declaredConstructor.setAccessible(true);
        // 构造出新实例
        SingletonWithfield newInstance = declaredConstructor.newInstance();
        System.out.println(instance1.equals(newInstance));
    }

    @Test
    public void brokeTwo() throws Exception {
        SingletonWithfieldSafe instance1 = SingletonWithfieldSafe.getINSTANCE();
        SingletonWithfieldSafe instance2 = SingletonWithfieldSafe.getINSTANCE();
        System.out.println(instance1.equals(instance2));
        Class<SingletonWithfieldSafe> singleton = SingletonWithfieldSafe.class;
        Constructor<SingletonWithfieldSafe> declaredConstructor = singleton.getDeclaredConstructor();
        // 通过反射改变构造方法的可访问性
        declaredConstructor.setAccessible(true);
        // 阻止构造出新实例
        SingletonWithfieldSafe newInstance = declaredConstructor.newInstance();
        System.out.println("" + instance1.equals(newInstance));
    }

    /**
     * 通过对序列化后的单例对象 进行反序列化得到的对象是一个新的对象，这就破坏了单例 的单例性。
     */
    @Test
    public void serializationTest() {
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            SingletonWithfieldSafe instance1 = SingletonWithfieldSafe.getINSTANCE();
            FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/obj.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(instance1);
            objectOutputStream.flush();

            SingletonWithfieldSafe instance2 = null;
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/obj.txt");
            objectInputStream = new ObjectInputStream(fileInputStream);
            instance2 = (SingletonWithfieldSafe) objectInputStream.readObject();

            System.out.println(instance1.equals(instance2));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                objectOutputStream.close();
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

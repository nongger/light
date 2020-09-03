package com.darren.fresh.IO;

import org.junit.Test;

import java.io.*;


/**
 * 要实现序列化的类： 1.要求此类是可序列化的：实现Serializable接口
 * 2.要求类的属性同样的要实现Serializable接口
 * 3.提供一个版本号：private static final long serialVersionUID
 * 4.使用static或transient修饰的属性，不可实现序列化
 *
 * @author Darren
 * @date 2018/5/13
 */
public class ObjectInputOutputStream {

    /**
     * 对象的反序列化过程：将硬盘中的文件通过ObjectInputStream转换为相应的对象
     */
    @Test
    public void objectInputStreamTest() {
        File objectFile = new File("D:\\darren\\io\\helloObject.txt");
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(objectFile));

            Object obj1 = objectInputStream.readObject();
            Person person = (Person) obj1;
            System.out.println(person);

            Object obj2 = objectInputStream.readObject();
            Person person1 = (Person) obj2;
            System.out.println(person1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null)
                    objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对象的序列化过程：将内存中的对象通过ObjectOutputStream转换为二进制流，存储在硬盘文件中
     */
    @Test
    public void objectOutputStreamTest() {

        File objectFile = new File("D:\\darren\\io\\helloObject.txt");
        Person person = new Person("car", 20);
        Person person1 = new Person("darren", 22);

        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(objectFile));
            objectOutputStream.writeObject(person);
            objectOutputStream.flush();
            objectOutputStream.writeObject(person1);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null)
                    objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}


class Person implements Serializable {

    String name;
    Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
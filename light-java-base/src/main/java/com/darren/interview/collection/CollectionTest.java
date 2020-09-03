package com.darren.interview.collection;

import com.darren.model.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  存储方式：1.顺序结构 2.链式存储
 *
 * 1.数据的存储的“容器”：①数组 int[] arr = new int[10]②集合
 * 2.Collection:用来存储一个一个的数据
 * 		|-----Set:存储无序的、不可重复的数据--相当于高中的"集合"--“哈希算法”
 * 			|----HashSet:主要的实现类
 * 				|----LinkedHashSet:对于频繁的遍历，效率高
 * 			|----TreeSet:可以按照添加的元素的指定属性进行排序遍历（自然排序Comparable（compareTo（Object obj））&定制排序Comparator（compare(Obejct obj1,Object obj2)））
 * 		|-----List:存储有序的、可以重复的数据--相当于"动态"数组
 * 			|----ArrayList:主要实现类，线程不安全的
 * 			|----LinkedList:对于频繁的插入、删除操作，效率高于ArrayList
 * 			|----Vector:古老的实现类，线程安全的
 *
 *   Map:用来存储一对一对的数据(key-value)---相当于y = f(x). y = x + 1;(x1,y1)(x2,y2)
 *   		|----HashMap
 *   			|----LinkedHashMap
 *   		|----TreeMap
 *   		|----Hashtable
 *   			|----Properties
 * @author Darren
 * @date 2019/3/6 23:41
 */
public class CollectionTest {

    private static final String customerOrderFormat = "c:%s_i:%s";

    public static void main(String[] args) {
        //原则：添加自定义类的对象到Set中时，需要自定义对象所在的类重写：equals()且hashCode();
        //List
        // Collection 集合的上层接口 --set --list
        // Collections 集合的工具类
        List emptyList = Collections.EMPTY_LIST;

        Hashtable<String, String> h = new Hashtable<>();

        HashMap<String, String> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        map.put("k4", "v4");

        System.out.println(map.computeIfAbsent("k3", k -> "k5"));
        System.out.println(map.get("k3"));

        System.out.println(map.get("k1"));
        System.out.println(map.remove("k1"));
        System.out.println(map.get("k1"));
        System.out.println(map.remove("k1"));

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("u1", "zhangsan");

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("name", "darren");


        String cuid = String.format(customerOrderFormat, 12111, 454534231);
        System.out.println(cuid);

        // RandomAccess支持随机访问
        // int newCapacity = oldCapacity + (oldCapacity >> 1); 初始容量10
        ArrayList<String> arrayList = new ArrayList<>();

    }

    // list的clone方法是浅拷贝，新建了一个list但是元素的指向还是旧list的元素，但是旧list新增和删除不会影响新list
    @Test
    public void concurrentModifyLinkedList() {
        LinkedList<User> secondsData = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            secondsData.add(new User(i, "hell" + i, i * 10));
        }
        LinkedList<User> clone = (LinkedList<User>) secondsData.clone();
        secondsData.add(new User(100,"yuran",25));

        List<User> ret = new ArrayList<>();
        ret.addAll(clone);
        clone.add(new User(100,"yur",23));
        System.out.println(StringUtils.join(clone));
        secondsData.add(new User(100,"yur",25));
        secondsData.get(8).setAge(28);
        System.out.println(StringUtils.join(secondsData));


    }
}

package com.darren.interview.algorithm;

import java.util.HashMap;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2019/5/10 14:18
 * Desc   : 人肉实现一个LRU算法
 * 需要注意的是，这段不是线程安全的，要想做到线程安全，需要加上synchronized修饰符。
 * <p>
 * 哈希表是由若干个Key-Value所组成。在“逻辑”上，这些Key-Value是无所谓排列顺序的。
 * <p>
 * 在哈希链表当中，这些Key-Value不再是彼此无关的存在，而是被一个链条串了起来。每一个Key-Value都具有它的前驱Key-Value、后继Key-Value，就像双向链表中的节点一样。
 * 这样一来，原本无序的哈希表拥有了固定的排列顺序。
 * <p>
 * 构建思想：利用哈希表的有序性，可以把key-value按照时间排序
 * <p>
 * 让我们以用户信息的需求为例，来演示一下LRU算法的基本思路：
 * 1.假设我们使用哈希链表来缓存用户信息，目前缓存了4个用户，这4个用户是按照时间顺序依次从链表右端插入的。
 * 2.此时，业务方访问用户5，由于哈希链表中没有用户5的数据，我们从数据库中读取出来，插入到缓存当中。这时候，链表中最右端是最新访问到的用户5，最左端是最近最少访问的用户1。
 * 3.接下来，业务方访问用户2，哈希链表中存在用户2的数据，我们怎么做呢？我们把用户2从它的前驱节点和后继节点之间移除，重新插入到链表最右端。这时候，链表中最右端变成了最新访问到的用户2，最左端仍然是最近最少访问的用户1。
 * 4.接下来，业务方请求修改用户4的信息。同样道理，我们把用户4从原来的位置移动到链表最右侧，并把用户信息的值更新。这时候，链表中最右端是最新访问到的用户4，最左端仍然是最近最少访问的用户1。
 * 5.后来业务方换口味了，访问用户6，用户6在缓存里没有，需要插入到哈希链表。假设这时候缓存容量已经达到上限，必须先删除最近最少访问的数据，那么位于哈希链表最左端的用户1就会被删除掉，然后再把用户6插入到最右端。
 * 以上，就是LRU算法的基本思路。
 */
public class AllKeysLRU {

    private Node head;
    private Node end;
    // 缓存存储上限，用于模拟容量上限
    private int limit;
    private HashMap<String, Node> hashMap;


    public AllKeysLRU(int limit) {
        this.limit = limit;
        hashMap = new HashMap<String, Node>();
    }

    public String get(String key) {

        Node node = hashMap.get(key);
        if (node == null) {
            return null;
        }

        // 刷新最近使用的节点
        refreshNode(node);

        return node.value;
    }

    public void put(String key, String value) {
        Node node = hashMap.get(key);
        if (node == null) {
            //如果key不存在，插入key-value
            if (hashMap.size() >= limit) {
                String oldKey = removeNode(head);
                hashMap.remove(oldKey);
            }

            node = new Node(key, value);
            addNode(node);
            hashMap.put(key, node);
        } else {
            //如果key存在，刷新key-value
            node.value = value;
            refreshNode(node);
        }
    }

    public void remove(String key) {

        Node node = hashMap.get(key);
        removeNode(node);
        hashMap.remove(key);
    }


    /**
     * 刷新被访问的节点位置
     *
     * @param node 被访问的节点
     */
    private void refreshNode(Node node) {
        //如果访问的是尾节点，无需移动节点
        if (node == end) {
            return;
        }
        //移除节点
        removeNode(node);

        //重新插入节点
        addNode(node);
    }


    /**
     * 删除节点
     *
     * @param node 要删除的节点
     */
    private String removeNode(Node node) {

        if (node == end) {
            //移除尾节点
            end = end.pre;
            end.next = null;
        } else if (node == head) {
            //移除头节点，使节点不可达容易被GC回收
            head = head.next;
            head.pre = null;
        } else {
            //移除中间节点
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }

        return node.key;
    }

    /**
     * 尾部插入节点
     *
     * @param node 要插入的节点
     */
    private void addNode(Node node) {
        if (end != null) {
            end.next = node;
            node.pre = end;
            node.next = null;
        }

        end = node;
        if (head == null) {
            head = node;
        }
    }


    static class Node {
        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public Node pre;
        public Node next;
        public String key;
        public String value;
    }


    public static void main(String[] args) {

        AllKeysLRU lruCache = new AllKeysLRU(5);

        lruCache.put("001", "用户1信息");
        lruCache.put("002", "用户2信息");
        lruCache.put("003", "用户3信息");
        lruCache.put("004", "用户4信息");
        lruCache.put("005", "用户5信息");
        lruCache.get("002");
        lruCache.put("004", "用户4信息更新");
        lruCache.put("006", "用户6信息");
        System.out.println(lruCache.get("001"));
        System.out.println(lruCache.get("006"));
    }
}

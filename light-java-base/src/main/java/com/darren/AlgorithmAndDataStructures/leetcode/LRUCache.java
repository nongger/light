package com.darren.AlgorithmAndDataStructures.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: light
 * Time   : 2021-04-05 18:28
 * Author : liujingwei05
 * Version: v1.0
 * Desc   : 146. LRU 缓存机制
 * <p>
 * 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制 。
 * 实现 LRUCache 类：
 * <p>
 * LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
 * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 * void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
 *  
 * <p>
 * 进阶：你是否可以在 O(1) 时间复杂度内完成这两种操作？
 */
public class LRUCache {
    private int capacity;
    private Map<Integer, Node<Integer, Integer>> map;
    private DoubleLinkedList<Integer, Integer> doubleLinkedList;


    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
        doubleLinkedList = new DoubleLinkedList<>();
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node<Integer, Integer> oldNode = map.get(key);
        doubleLinkedList.revision(oldNode);
        return oldNode.value;
    }

    public void put(int key, int value) {
        Node<Integer, Integer> exist = map.get(key);
        // 如果是新key
        if (exist == null) {
            // 如果容量不够移除最近最少使用
            if (capacity == map.size()) {
                Node<Integer, Integer> lastRecentNode = doubleLinkedList.getLastRecentNode();
                map.remove(lastRecentNode.key);
                doubleLinkedList.remove(lastRecentNode);
            }
            Node<Integer, Integer> newNode = new Node<>(key, value);
            map.put(key, newNode);
            doubleLinkedList.put(newNode);
        } else {
            // 如果是旧key，刷新原值序列
            exist.value = value;
//            map.put(key, exist);

            doubleLinkedList.revision(exist);
        }
    }

    /**
     * 存储数据的包装类
     *
     * @param <K>
     * @param <V>
     */
    class Node<K, V> {
        K key;
        V value;
        Node<K, V> pre;
        Node<K, V> next;

        public Node() {
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 维护LRU顺序
     *
     * @param <K>
     * @param <V>
     */
    class DoubleLinkedList<K, V> {
        Node<K, V> head;
        Node<K, V> tail;

        public DoubleLinkedList() {
            head = new Node<>();
            tail = new Node<>();
            head.next = tail;
            tail.pre = head;
        }

        /**
         * 添加元素
         *
         * @param node
         */
        public void put(Node<K, V> node) {
            node.next = head.next;
            node.pre = head.next.pre;
            head.next.pre = node;
            head.next = node;
        }

        /**
         * 移除一个节点
         *
         * @param node
         */
        public void remove(Node<K, V> node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;
            node.pre = null;
            node.next = null;
        }

        /**
         * 重新维护序列
         * 如果已经是第一个节点则无需再调整
         * 如果不是第一个节点则将改节点摘除并放到第一个节点（晋升）
         *
         * @param node
         */
        public void revision(Node<K, V> node) {
            if (node == head.next) {
                return;
            }
            remove(node);
            put(node);
        }

        /**
         * 获取最近最少使用的节点
         *
         * @return
         */
        public Node<K, V> getLastRecentNode() {
            return tail.pre;
        }
    }

    /**
     * 输入
     * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
     * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
     * 输出
     * [null, null, null, 1, null, -1, null, -1, 3, 4]
     * <p>
     * 解释
     * LRUCache lRUCache = new LRUCache(2);
     * lRUCache.put(1, 1); // 缓存是 {1=1}
     * lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
     * lRUCache.get(1);    // 返回 1
     * lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
     * lRUCache.get(2);    // 返回 -1 (未找到)
     * lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
     * lRUCache.get(1);    // 返回 -1 (未找到)
     * lRUCache.get(3);    // 返回 3
     * lRUCache.get(4);    // 返回 4
     *
     * @param args
     */
    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        System.out.println(lRUCache.map.keySet());
        lRUCache.get(1);    // 返回 1
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        System.out.println(lRUCache.map.keySet());
        lRUCache.get(2);    // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        System.out.println(lRUCache.map.keySet());
        lRUCache.get(1);    // 返回 -1 (未找到)
        lRUCache.get(3);    // 返回 3
        lRUCache.get(4);    // 返回 4
        System.out.println(lRUCache.map.keySet());

    }


}

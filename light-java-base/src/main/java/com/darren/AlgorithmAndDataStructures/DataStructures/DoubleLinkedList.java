package com.darren.AlgorithmAndDataStructures.DataStructures;

import org.junit.Test;

/**
 * Project: light
 * Time   : 2020-10-18 18:26
 * Desc   :  *
 */
public class DoubleLinkedList {

    DoubleListNode head = new DoubleListNode(0);

    public void add(DoubleListNode node) {
        DoubleListNode current = head;
        // 找到最后一个节点
        while (current.next != null) {
            current = current.next;
        }
        // 形成双向链表
        current.next = node;
        node.pre = current;
    }

    public boolean delete(int val) {
        DoubleListNode current = head.next;
        // 找到匹配节点
        boolean flag = false;
        while (current != null) {
            if (val == current.val) {
                flag = true;
                break;
            }
            current = current.next;
        }

        if (flag) {
            // 将自己从链表摘除
            current.pre.next = current.next;
            if (current.next != null) {
                current.next.pre = current.pre;
            }
        } else {
            System.out.println("未找到匹配的节点" + val);
        }
        return flag;
    }

    public DoubleListNode getHead() {
        return head;
    }

    public void showAll() {
        DoubleListNode current = head.next;
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }

    }

    public class DoubleListNode {
        int val;
        DoubleListNode next;
        DoubleListNode pre;

        DoubleListNode() {
        }

        DoubleListNode(int val) {
            this.val = val;
        }

        DoubleListNode(int val, DoubleListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "DoubleListNode{" +
                    "val=" + val +
                    '}';
        }
    }

    @Test
    public void test() {
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(new DoubleListNode(1));
        doubleLinkedList.add(new DoubleListNode(2));
        doubleLinkedList.showAll();

        System.out.println("-----删除后的链表----");
        doubleLinkedList.delete(1);
        doubleLinkedList.showAll();
    }
}

package com.darren.AlgorithmAndDataStructures.DataStructures;

import org.junit.Test;

/**
 * Project: light
 * Time   : 2020-10-18 18:26
 * Desc   :  *
 */
public class DoubleLinkedList {

    ListNode head = new ListNode(0);

    public void add(ListNode node) {
        ListNode current = head;
        // 找到最后一个节点
        while (current.next != null) {
            current = current.next;
        }
        // 形成双向链表
        current.next = node;
        node.pre = current;
    }

    public boolean delete(int val) {
        ListNode current = head.next;
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

    public ListNode getHead() {
        return head;
    }

    public void showAll() {
        ListNode current = head.next;
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }

    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode pre;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    '}';
        }
    }

    @Test
    public void test() {
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(new ListNode(1));
        doubleLinkedList.add(new ListNode(2));
        doubleLinkedList.showAll();

        System.out.println("-----删除后的链表----");
        doubleLinkedList.delete(1);
        doubleLinkedList.showAll();
    }
}

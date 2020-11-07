package com.darren.AlgorithmAndDataStructures.DataStructures;

import com.darren.AlgorithmAndDataStructures.leetcode.model.ListNode;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Project: light
 * Time   : 2020-11-01 16:00
 * Desc   : 单向环形链表（约瑟夫环）
 */
public class WheelLinkedList {
    private ListNode first;
    private ListNode tail;
    private volatile AtomicInteger size = new AtomicInteger(0);


    public boolean add(int val) {
        ListNode element = new ListNode(val);
        if (first == null) {
            first = element;
            first.next = first;
            tail = first;
        } else {
            element.next = tail.next;
            tail.next = element;
            tail = element;
        }
        size.incrementAndGet();
        return true;
    }

    public void create(int num) {

        ListNode cur = new ListNode();
        for (int i = 1; i <= num; i++) {
            if (i == 1) {
                first = new ListNode(i);
                first.next = first;
                cur = first;
            } else {
                cur.next = new ListNode(i);
                cur = cur.next;
                cur.next = first;
            }
        }
    }

    public void showAll() {
        if (first == null) {
            System.out.println("链表为空");
            return;
        }
        ListNode cur = first;
        while (true) {
            System.out.println(cur);
            if (cur.next == first) {
                break;
            }
            cur = cur.next;
        }
    }

    /**
     * 小孩出圈问题（约瑟夫问题）
     */
    public void josepfu(int startNo, int countNum, int total) {
        if (first == null || startNo < 1 || startNo > total) {
            System.out.println("参数有误");
            return;
        }
        // 首先将helper指向尾结点
        ListNode helper = first;
        while (helper.next != first) {
            helper = helper.next;
        }
        // 先将helper和frist同步移动到初始位置startNo
        for (int i = 0; i < startNo - 1; i++) {
            helper = helper.next;
            first = first.next;
        }

        // 遍历将helper和frist同步移动countNum-1
        while (true) {

            if (helper == first) {
                System.out.println("最后一个出队" + first);
                break;
            }
            for (int i = 0; i < countNum - 1; i++) {
                helper = helper.next;
                first = first.next;
            }
            System.out.println("出队：" + first);
            first = first.next;
            helper.next = first;
        }


    }

    @Test
    public void testWheelLink() {
        WheelLinkedList wheelLinkedList = new WheelLinkedList();
        wheelLinkedList.create(5);
        wheelLinkedList.showAll();
        System.out.println("===========");
        wheelLinkedList.josepfu(2, 3, 5);

    }

}

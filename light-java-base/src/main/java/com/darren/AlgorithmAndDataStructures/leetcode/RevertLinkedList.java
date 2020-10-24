package com.darren.AlgorithmAndDataStructures.leetcode;

import com.darren.AlgorithmAndDataStructures.leetcode.model.ListNode;
import org.junit.Test;

/**
 * Project: light
 * Time   : 2020-10-22 21:28
 * Desc   :  反转一个单链表。
 * <p>
 * 示例:
 * <p>
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 */
public class RevertLinkedList {

    @Test
    public void testReverseList() {
        ListNode head = new ListNode(1)
                .setNext(new ListNode(2)
                        .setNext(new ListNode(3)
                                .setNext(new ListNode(4)
                                        .setNext(new ListNode(5))))
                );
        printAll(head);

        ListNode listNode = reverseList(head);
        printAll(listNode);
        printAll(head);


    }

    public ListNode reverseList(ListNode head) {
        ListNode revertHead = new ListNode();
        ListNode next = head;// 不改变原链表

        while (next != null) {
            // 保留下一个节点
            next = head.next;
            // 摘除头结点，加入新链表
            head.next = revertHead.next;
            revertHead.next = head;
            // head节点后移
            head = next;
        }
        head = revertHead.next;

        return head;

    }

    private void printAll(ListNode head) {
        ListNode temp = head;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.next;
        }
    }

}

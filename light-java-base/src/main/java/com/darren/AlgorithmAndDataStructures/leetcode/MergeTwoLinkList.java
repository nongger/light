package com.darren.AlgorithmAndDataStructures.leetcode;

import org.junit.Test;

/**
 * Project: light
 * Time   : 2020-10-18 00:02
 * Desc   : 合并两个有序链表
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
 * <p>
 * 示例：
 * <p>
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 */
public class MergeTwoLinkList {
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    @Test
    public void testMergeTwoLists() {
        ListNode head1 = new ListNode(1);

    }

    /**
     * 使用递归的方式
     * <p>
     * 首先了解一下递归的含义：
     * 递归函数必须要有终止条件，否则会出错；
     * 递归函数先不断调用自身，直到遇到终止条件后进行回溯，最终返回答案。
     * <p>
     * 本例中：
     * 终止条件：当两个链表都为空时，表示我们对链表已合并完成。
     * 如何递归：我们判断 l1 和 l2 头结点哪个更小，然后较小结点的 next 指针指向其余结点的合并结果。（调用递归）
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        // 处理中止条件,总有一个链表会先被遍历完
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        if (l1.val <= l2.val) {
            l1.next = mergeTwoLists1(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists1(l1, l2.next);
            return l2;
        }

    }

    /**
     * 使用迭代链表的方式
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode newListNode = new ListNode();
        ListNode current = newListNode;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        current.next = l1 == null ? l2 : l1;

        return newListNode.next;
    }

}

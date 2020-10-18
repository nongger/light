package com.darren.AlgorithmAndDataStructures.DataStructures;

import org.junit.Test;

import java.util.Stack;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-10-08 22:11
 * Desc   : 实现一个简单的链表结构
 * 实现链表的基本操作
 */
public class SingleLinkedList {
    // 首节点不具有实际意义，不存储数据
    private SimpleNode head = new SimpleNode(0, "empty");

    /**
     * 向尾结点添加元素
     *
     * @param node
     */
    public void addLink(SimpleNode node) {
        SimpleNode temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        temp.next = node;
    }

    /**
     * 按编号顺序添加，如果存在相同的编号添加失败
     *
     * @param node
     */
    public void addByNo(SimpleNode node) {
        boolean flag = false;
        SimpleNode temp = head;
        while (true) {
            if (temp.next == null || temp.next.No > node.No) {
                break;
            }
            if (temp.next.No == node.No) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            System.out.printf("已存在编号为：%d 的元素\n", node.No);
        } else {
            node.next = temp.next;
            temp.next = node;
        }
    }

    public void updateByNo(SimpleNode update) {
        SimpleNode temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.No == update.No) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            // 找到元素修改内容
            temp.name = update.name;
        } else {
            System.out.printf("未找到编号为%d的元素，无法修改！\n", update.No);
        }
    }

    public void delNode(int no) {
        // 需要找到待删除节点的前一个节点
        SimpleNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.No == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.next = temp.next.next;
        } else {
            System.out.printf("未找到编号为%d的元素，删除失败！\n", no);
        }

    }

    public void showAll() {
        if (head.next == null) {
            System.out.println("链表为空！");
            return;
        }
        SimpleNode temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }

    }

    public SimpleNode getHead() {
        return head;
    }

    static class SimpleNode {
        public int No;
        public String name;
        public SimpleNode next;

        public SimpleNode(int no, String name) {
            No = no;
            this.name = name;
        }

        @Override
        public String toString() {
            return "SimpleNode{" +
                    "No=" + No +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        SingleLinkedList linkedList = new SingleLinkedList();
//        linkedList.addLink(new SimpleNode(1, "eric"));
//        linkedList.addLink(new SimpleNode(2, "darren"));

        linkedList.addByNo(new SimpleNode(2, "darren"));
        linkedList.addByNo(new SimpleNode(1, "eric"));
        linkedList.addByNo(new SimpleNode(4, "caroline"));
        linkedList.addByNo(new SimpleNode(3, "non"));
        System.out.println("--插入后的链表内容--");
        linkedList.showAll();

//        linkedList.updateByNo(new SimpleNode(4, "caroline+eric"));
//        System.out.println("--修改后的链表内容--");
//        linkedList.showAll();
//
//        linkedList.delNode(4);
//        linkedList.delNode(2);
//        linkedList.delNode(1);
//        System.out.println("--删除后的链表内容--");
//        linkedList.showAll();

//        System.out.println("链表长度：" + getLength(linkedList.getHead()));
//        System.out.println("查找结果：" + searchBottomK(linkedList.getHead(), 4));
//        System.out.println("--反转后的链表内容--");
//        reverseLink(linkedList.getHead());
//        linkedList.showAll();

        System.out.println("--逆序打印--");
        printReverse(linkedList.getHead());

    }

    /**
     * 单链表中有效节点的个数（不统计头结点）
     * 思路：
     * 从头结点遍历一遍链表
     */
    public static int getLength(SimpleNode head) {
        if (head.next == null) {
            return 0;
        }
        int length = 0;
        SimpleNode cur = head.next;
        while (cur != null) {
            length++;
            cur = cur.next;
        }
        return length;

    }

    /**
     * 查找单链表中的倒数第k个结点 【新浪面试题】
     * 入参：链表头结点，倒数第k个
     */
    public static SimpleNode searchBottomK(SimpleNode head, int k) {
        if (head.next == null) {
            return null;
        }
        // 先获取链表总大小
        int length = getLength(head);
        // 索引合法检查
        if (k <= 0 || k > length) {
            return null;
        }

        SimpleNode cur = head.next;
        for (int i = 0; i < length - k; i++) {
            cur = cur.next;
        }

        return cur;
    }

    /**
     * 单链表的反转【腾讯面试题】
     * 1. 新建一个头结点reverseHead
     * 2. 从头到尾遍历原来的链表，每遍历一个节点将其从链表摘除，并放到新链表reverseHead的最前端
     * 3. 将原链表头指向新链表
     */
    public static void reverseLink(SimpleNode head) {
        if (head.next == null || head.next.next == null) {
            System.out.println("链表为空或只有一个元素，不需要反转");
            return;
        }
        SimpleNode reverseHead = new SimpleNode(0, "reverse");
        SimpleNode cur = head.next;
        SimpleNode next = null;
        while (cur != null) {
            next = cur.next;// 暂存当前节点的下一个节点
            head.next = next;// 将当前节点从原链表摘除
            cur.next = reverseHead.next;// 当前节点的下一个节点指向新链表的第一个元素
            reverseHead.next = cur;// 新链表的头结点的next指向当前节点（就是把当前节点放到新链表的第一个位置）
            cur = next;
        }
        // 头结点连接到新链表
        head.next = reverseHead.next;

    }

    /**
     * 从尾到头打印单链表 【百度，要求方式1：反向遍历 。 方式2：Stack栈】
     */
    public static void printReverse(SimpleNode head) {
        if (head.next == null) {
            return;
        }

        SimpleNode cur = head.next;
        Stack<SimpleNode> stack = new Stack<>();
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }

    }

    /**
     * 合并两个有序的单链表，合并之后的链表依然有序
     * 头结点不存储数据
     * <p>
     * leetcode有此算法题，采用遍历的方式解决
     */
    public SimpleNode mergeByOrder(SimpleNode head1, SimpleNode head2) {
        SimpleNode mergeHead = new SimpleNode(0, "");
        SimpleNode cur = mergeHead;

        // 两个链表都为空或遍历完成，跳出循环
        while (head1.next != null && head2.next != null) {
            if (head1.next.No <= head2.next.No) {
                // 记录旧节点
                cur.next = head1.next;
                // 从旧链表摘除
                head1.next = head1.next.next;

            } else {
                // 记录旧节点
                cur.next = head2.next;
                // 从旧链表摘除
                head2.next = head2.next.next;
            }
            // 临时节点后移
            cur = cur.next;
        }
        cur.next = head1.next == null ? head2.next : head1.next;

        return mergeHead;
    }

    /**
     * 合并两个有序的单链表，合并之后的链表依然有序
     * 头结点不存储数据
     * <p>
     * leetcode有此算法题，采用递归的方式解决
     * <p>
     * 中止条件：某个链表先遍历完成
     * 某次输出：最小的一个节点
     * 递归思路：将每次返回的最小值的下一个节点指向下一次的调用输出，当前的最小节点前向遍历作为下一次递归调用的输入
     */
    public SimpleNode mergeByOrderWithRecursion(SimpleNode head1, SimpleNode head2) {
        if (head1.next == null) {
            return head2.next;
        }
        if (head2.next == null) {
            return head1.next;
        }

        if (head1.next.No <= head2.next.No) {
            head1.next.next = mergeByOrderWithRecursion(head1.next, head2);
            return head1.next;
        } else {
            head2.next.next = mergeByOrderWithRecursion(head1, head2.next);
            return head2.next;
        }

    }


    @Test
    public void testMergeByOrder() {
        SingleLinkedList linkedList = new SingleLinkedList();
        linkedList.addLink(new SimpleNode(1, "eric"));
        linkedList.addLink(new SimpleNode(2, "darren"));
        linkedList.addLink(new SimpleNode(3, "non"));
        linkedList.addLink(new SimpleNode(4, "caroline"));
        System.out.println("--插入后的链表1内容--");
        linkedList.showAll();
        SingleLinkedList linkedList2 = new SingleLinkedList();
        linkedList2.addLink(new SimpleNode(1, "eric"));
        linkedList2.addLink(new SimpleNode(2, "darren"));
        linkedList2.addLink(new SimpleNode(5, "non"));
        linkedList2.addLink(new SimpleNode(8, "caroline"));
        System.out.println("--插入后的链表2内容--");
        linkedList2.showAll();
        SimpleNode simpleNode = mergeByOrderWithRecursion(linkedList.getHead(), linkedList2.getHead());
        System.out.println("--merge后的链表内容--");
        while (simpleNode.next != null) {
            System.out.println(simpleNode.next);
            simpleNode = simpleNode.next;
        }
    }

}


package com.darren.AlgorithmAndDataStructures.DataStructures;

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
        System.out.println("--插入后的链表内容--");
        linkedList.showAll();

        linkedList.updateByNo(new SimpleNode(4, "caroline+eric"));
        System.out.println("--修改后的链表内容--");
        linkedList.showAll();

        linkedList.delNode(4);
        linkedList.delNode(2);
        linkedList.delNode(1);
        System.out.println("--删除后的链表内容--");
        linkedList.showAll();

    }

}


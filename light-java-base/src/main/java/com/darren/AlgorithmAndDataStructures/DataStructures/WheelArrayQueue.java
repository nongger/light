package com.darren.AlgorithmAndDataStructures.DataStructures;

import java.util.Scanner;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-10-08 15:59
 * Desc   : 数组模拟环形队列
 */
public class WheelArrayQueue {
    private int capacity;
    private int[] items;
    // 队列的输出、输入是分别从头和尾来处理，因此需要两个变量 head及 tail分别记录队列前后端的下标，
    // head 会随着数据输出而改变，而 tail则是随着数据输入而改变
    private int head;
    private int tail;

    public WheelArrayQueue(int capacity) {
        this.capacity = capacity;
        this.items = new int[capacity];
        this.head = 0;// 指向队列的第一个元素的位置
        this.tail = 0;// 指向最后一个元素的后一个位置
    }

    public boolean isFull() {
        return head == (tail + 1) % capacity;
    }

    public boolean isEmpty() {
        return head == tail;
    }

    /**
     * 数据存入队列时
     * 将尾指针往后移：rear+1 , 当front == rear 【空】
     * 若尾指针 rear 小于队列的最大下标 maxSize-1，则将数据存入 rear所指的数组元素中，否则无法存入数据。 rear  == maxSize - 1[队列满]
     *
     * @param node
     */
    public void addQueue(int node) {
        if (isFull()) {
            System.err.println("队列已满，丢弃当前入队的数据");
            return;
        }
        items[tail] = node;
        // 将尾指针后移，环形队列考虑取模
        tail = (tail + 1) % capacity;
    }

    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，出队失败");
        }
        // 取出头结点
        int headValue = items[head];
        // 重新计算头结点的位置
        head = (head + 1) % capacity;
        return headValue;
    }

    public int size() {
        return (tail + capacity - head) % capacity;
    }

    public void showQueue() {
        if (isEmpty()) {
            System.err.println("队列为空");
            return;
        }
        for (int i = head; i < head + size(); i++) {
            System.out.printf("items[%d]=%d\n", i % capacity, items[i % capacity]);
        }
    }

    public int peekQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，无法展示头数据！");
        }
        return items[head];
    }


    public static void main(String[] args) {
        // 可用容量是数组大小减1
        WheelArrayQueue queue = new WheelArrayQueue(4);
        System.out.println("s(show)显示队列内容");
        System.out.println("h(head)显示头结点");
        System.out.println("a(add)入队一个元素");
        System.out.println("g(get)出队一个元素");
        System.out.println("e(exit)退出");

        Scanner scanner = new Scanner(System.in);
        char key = ' ';
        boolean loop = true;
        while (loop) {
            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    queue.showQueue();
                    break;
                case 'h':
                    try {
                        System.out.printf("队列的头数据：%d\n", queue.peekQueue());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 'a':
                    System.out.println("请输入要入队的值：");
                    int node = scanner.nextInt();
                    queue.addQueue(node);
                    break;
                case 'g':
                    try {
                        System.out.printf("当前队头数据：%d\n", queue.getQueue());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 'e':
                    loop = false;
                    scanner.close();
                    break;
                default:
                    System.err.println("命令无法识别！");
                    break;
            }

        }
        System.out.println("程序退出");

    }
}

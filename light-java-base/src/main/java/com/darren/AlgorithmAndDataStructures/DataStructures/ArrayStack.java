package com.darren.AlgorithmAndDataStructures.DataStructures;

/**
 * Project: light
 * Time   : 2020-11-07 14:35
 * Desc   : 数组模拟栈的基本操作
 */
public class ArrayStack {
    private int capacity;
    private int[] stack;
    private int top = -1;

    public ArrayStack() {
        this(16);
    }

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        stack = new int[capacity];
    }

    public boolean isFull() {
        return top == capacity - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean push(int element) {
        // 如果超过容量返回false
        if (isFull()) {
            System.err.println("栈已满！");
            return false;
        }
        stack[++top] = element;
        return true;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈内元素为空！");
        }
        return stack[top--];
    }

    public void showAll() {
        if (isEmpty()) {
            System.err.println("栈内没有元素");
        }
        for (int i = top; i != -1; i--) {
            System.out.printf("当前栈元素：%d, 值：%d\n", i, stack[i]);
        }
    }

    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(10);
        arrayStack.push(10);
        arrayStack.push(19);
        arrayStack.push(30);
        arrayStack.push(90);
        arrayStack.showAll();

        System.out.println("=========");
        System.out.println(arrayStack.pop());
    }
}

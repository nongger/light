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

    /**
     * 入栈
     *
     * @param element
     * @return
     */
    public boolean push(int element) {
        // 如果超过容量返回false
        if (isFull()) {
            System.err.println("栈已满！");
            return false;
        }
        stack[++top] = element;
        return true;
    }

    /**
     * 出栈
     *
     * @return
     */
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈内元素为空！");
        }
        return stack[top--];
    }

    /**
     * 查看栈顶元素，不弹出
     *
     * @return
     */
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("栈内元素为空！");
        }
        return stack[top];
    }

    public void showAll() {
        if (isEmpty()) {
            System.err.println("栈内没有元素");
        }
        for (int i = top; i != -1; i--) {
            System.out.printf("当前栈元素：%d, 值：%d\n", i, stack[i]);
        }
    }

    /**
     * 判断运算符的优先级
     * 此处假定只有四种简单运算
     * 数字越大优先级越高
     *
     * @param oper
     * @return
     */
    public int priority(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1;
        }

    }

    /**
     * 是否为操作符
     *
     * @param index
     * @return
     */
    public boolean isOper(char index) {
        return index == '+' || index == '-' || index == '*' || index == '/';
    }

    public int calculate(String expression) {
        return 0;
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

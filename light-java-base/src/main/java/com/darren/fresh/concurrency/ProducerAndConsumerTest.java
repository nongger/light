package com.darren.fresh.concurrency;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2018/6/28 20:53
 * Desc   : 生产者消费者案例-虚假唤醒
 * notifyAll和wait方法属于Object，线程被唤醒后从挂起处开始执行
 */
public class ProducerAndConsumerTest {
    // 单元测试不能测试线程问题
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Producer pro = new Producer(clerk);
        Consumer cus = new Consumer(clerk);

        new Thread(cus, "消费者 B").start();
        new Thread(pro, "生产者 A").start();

        new Thread(pro, "生产者 C").start();
        new Thread(cus, "消费者 D").start();
    }

}


//店员
/*
class Clerk {
    private int product = 0;

    //进货
    public synchronized void get() {//循环次数：0
        while (product >= 1) {//为了避免虚假唤醒问题，应该总是使用在循环中
            System.out.println("产品已满！");

            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }

        System.out.println(Thread.currentThread().getName() + " : " + ++product);
        this.notifyAll();
    }

    //卖货
    public synchronized void sale() {//product = 0; 循环次数：0
        while (product <= 0) {
            System.out.println("缺货！");

            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }

        System.out.println(Thread.currentThread().getName() + " : " + --product);
        this.notifyAll();
    }
}

//生产者
class Producer implements Runnable {
    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }

            clerk.get();
        }
    }
}

//消费者
class Consumer implements Runnable {
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}
*/

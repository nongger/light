package com.darren.interview.concurrent.lock;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Darren
 * @date 2019/4/2 23:00
 */
public class CAS {

    public static void main(String[] args) {
        // 当且仅当 V 的值等于 A时，CAS通过原子方式用新值B来更新V的值，否则不会执行任何操作（比较和替换是一个原子操作）。
        // 一般情况下是一个自旋操作，即不断的重试。
        AtomicInteger share = new AtomicInteger();
        System.out.println(share.incrementAndGet());

        /**
         *  AtomicStampedReference类就提供了此种能力，其中的 compareAndSet方法就是首先检查当前引用是否等于预期引用，并且当前标志是否等于预期标志，
         *  如果全部相等，则以原子方式将该引用和该标志的值设置为给定的更新值。
         *
         *  CAS 只对单个共享变量有效，当操作涉及跨多个共享变量时 CAS 无效。
         *  AtomicReference类来保证引用对象之间的原子性，你可以把多个变量放在一个对象里来进行 CAS 操作.
         *  所以我们可以使用锁或者利用AtomicReference类把多个共享变量合并成一个共享变量来操作。
         */
        // AtomicReference
    }

    public void Lock() throws ExecutionException, InterruptedException {
        // ReenTrantLock可以指定是公平锁还是非公平锁。而synchronized只能是非公平锁。
        // 所谓的公平锁就是先等待的线程先获得锁
        ReentrantLock lock = new ReentrantLock(true);
        //Monitor


        // 实现选择性通知
        // synchronized关键字与wait()和notify/notifyAll()方法相结合可以实现等待/通知机制
        Condition condition = lock.newCondition();
        condition.signalAll();


        // 线程池  new LinkedBlockingQueue<Runnable>()
        /**
         * 《阿里巴巴Java开发手册》中强制线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这
         样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险
         Executors 返回线程池对象的弊端如下：
         FixedThreadPool 和 SingleThreadExecutor ： 允许请求的队列长度为 Integer.MAX_VALUE,可能堆积大量的请求，从而导致OOM。
         CachedThreadPool 和 ScheduledThreadPool ： 允许创建的线程数量为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致OOM。
         */
        ExecutorService pool = Executors.newFixedThreadPool(1);

        // 没有返回值
        pool.execute(() -> {
            return;
        });
        /**
         * 有返回值可抛异常
         * 把Runnable接口或Callable接口的实现类
         * 提交（调用submit方法）给ThreadPoolExecutor或ScheduledThreadPoolExecutor时，会返回一个FutureTask对象。
         */
        Future<Integer> submit = pool.submit(() -> {
            return 9;
        });

        submit.get();
    }

    // https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247484832&amp;idx=1&amp;sn=f902febd050eac59d67fc0804d7e1ad5&source=41#wechat_redirect
    public void AQS() throws InterruptedException, BrokenBarrierException {
        // ReentrantLock
        // 构建锁和同步器的框架
        //AbstractQueuedSynchronizer



        /**
         * 独占锁
         * 内部基于AQS又实现了公平锁及非公平锁
         */
        ReentrantLock lock = new ReentrantLock();
        lock.tryLock();
        lock.unlock();

        /**
         * synchronized 和 ReentrantLock 都是一次只允许一个线程访问某个资源，Semaphore(信号量)可以指定多个线程同时访问某个资源。
         * Semaphore 有两种模式，公平模式和非公平模式。默认非公平模式
         */
        Semaphore semaphore = new Semaphore(32);
        // 执行 acquire 方法阻塞，直到有一个许可证可以获得然后拿走一个许可证；每个 release 方法增加一个许可证，这可能会释放一个阻塞的acquire方法。然而，其实并没有实际的许可证这个对象，Semaphore只是维持了一个可获得许可证的数量。 Semaphore经常用于限制获取某种资源的线程数量。
        semaphore.acquire();
        semaphore.release();
        // tryAcquire方法，该方法如果获取不到许可就立即返回false。
        semaphore.tryAcquire();
        semaphore.release();

        /**
         * 以CountDownLatch以例，任务分为N个子线程去执行，state也初始化为N（注意N要与线程个数一致）。
         * 这N个子线程是并行执行的，每个子线程执行完后countDown()一次，state会CAS(Compare and Swap)减1。
         * 等到所有子线程都执行完后(即state=0)，会unpark()主调用线程，然后主调用线程就会从await()函数返回，继续后余动作。
         * CountDownLatch 的内部类Sync实现了AbstractQueuedSynchronizer
         */
        CountDownLatch countDownLatch = new CountDownLatch(2);
        countDownLatch.countDown();
        countDownLatch.await();// 阻塞等待

        /**
         * CyclicBarrier 的字面意思是可循环使用（Cyclic）的屏障（Barrier）。
         * 它要做的事情是，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，
         * 所有被屏障拦截的线程才会继续干活。
         * CyclicBarrier默认的构造方法是 CyclicBarrier(int parties)，其参数表示屏障拦截的线程数量，
         * 每个线程调用await方法告诉 CyclicBarrier 我已经到达了屏障，然后当前线程被阻塞。
         *
         * CyclicBarrier 可以用于多线程计算数据，最后合并计算结果的应用场景。
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        cyclicBarrier.await();

        /**
         * 对于CountDownLatch来说，重点是“一个线程（多个线程）等待”，而其他的N个线程在完成“某件事情”之后，可以终止，也可以等待。
         * 而对于CyclicBarrier，重点是多个线程，在任意一个线程没有完成，所有的线程都必须等待。
         */

    }
}

/**
 * 如需自定义AQS重写下面几个AQS提供的模板方法即可
 * isHeldExclusively()//该线程是否正在独占资源。只有用到condition才需要去实现它。
 * tryAcquire(int)//独占方式。尝试获取资源，成功则返回true，失败则返回false。
 * tryRelease(int)//独占方式。尝试释放资源，成功则返回true，失败则返回false。
 * tryAcquireShared(int)//共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
 * tryReleaseShared(int)//共享方式。尝试释放资源，成功则返回true，失败则返回false。
 */
class SelfAQS extends AbstractQueuedSynchronizer {

    @Override
    protected boolean tryAcquire(int arg) {
        return super.tryAcquire(arg);
    }

    @Override
    protected boolean tryRelease(int arg) {
        return super.tryRelease(arg);
    }

    @Override
    protected int tryAcquireShared(int arg) {
        return super.tryAcquireShared(arg);
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
        return super.tryReleaseShared(arg);
    }

    @Override
    protected boolean isHeldExclusively() {
        return super.isHeldExclusively();
    }
}

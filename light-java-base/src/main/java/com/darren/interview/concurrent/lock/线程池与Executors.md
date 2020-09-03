
线程池提供了一种限制和管理资源（包括执行一个任务）。 每个线程池还维护一些基本统计信息，例如已完成任务的数量。

使用线程池的好处：

    降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的消耗。
    提高响应速度。当任务到达时，任务可以不需要的等到线程创建就能立即执行。
    提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。

Executor 框架结构(主要由三大部分组成)
1 任务。

执行任务需要实现的Runnable接口或Callable接口。
Runnable接口或Callable接口实现类都可以被ThreadPoolExecutor或ScheduledThreadPoolExecutor执行。

两者的区别：

    Runnable接口不会返回结果但是Callable接口可以返回结果。后面介绍Executors类的一些方法的时候会介绍到两者的相互转换。

2 任务的执行

如下图所示，包括任务执行机制的核心接口Executor ，以及继承自Executor 接口的ExecutorService接口。ScheduledThreadPoolExecutor和ThreadPoolExecutor这两个关键类实现了ExecutorService接口。

    注意： 通过查看ScheduledThreadPoolExecutor源代码我们发现ScheduledThreadPoolExecutor实际上是继承了ThreadPoolExecutor并实现了ScheduledExecutorService ，而ScheduledExecutorService又实现了ExecutorService，正如我们下面给出的类关系图显示的一样。
 异步计算的结果

Future接口以及Future接口的实现类FutureTask类。
当我们把Runnable接口或Callable接口的实现类提交（调用submit方法）给ThreadPoolExecutor或ScheduledThreadPoolExecutor时，会返回一个FutureTask对象。

我们以AbstractExecutorService接口中的一个submit方法为例子来看看源代码：

    public Future<?> submit(Runnable task) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<Void> ftask = newTaskFor(task, null);
        execute(ftask);
        return ftask;
    }


上面方法调用的newTaskFor方法返回了一个FutureTask对象。

    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new FutureTask<T>(runnable, value);
    }


2.3 Executor 框架的使用示意图

![Executor 框架的使用示意图](http://my-blog-to-use.oss-cn-beijing.aliyuncs.com/18-5-30/84823330.jpg)

    主线程首先要创建实现Runnable或者Callable接口的任务对象。
    备注： 工具类Executors可以实现Runnable对象和Callable对象之间的相互转换。（Executors.callable（Runnable task）或Executors.callable（Runnable task，Object resule））。

    然后可以把创建完成的Runnable对象直接交给ExecutorService执行（ExecutorService.execute（Runnable command））；或者也可以把Runnable对象或Callable对象提交给ExecutorService执行（ExecutorService.submit（Runnable task）或ExecutorService.submit（Callable task））。

    执行execute()方法和submit()方法的区别是什么呢？
    1)execute()方法用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功与否；
    2)submit()方法用于提交需要返回值的任务。线程池会返回一个future类型的对象，通过这个future对象可以判断任务是否执行成功，并且可以通过future的get()方法来获取返回值，get()方法会阻塞当前线程直到任务完成，而使用get（long timeout，TimeUnit unit）方法则会阻塞当前线程一段时间后立即返回，这时候有可能任务没有执行完。

    如果执行ExecutorService.submit（…），ExecutorService将返回一个实现Future接口的对象（我们刚刚也提到过了执行execute()方法和submit()方法的区别，到目前为止的JDK中，返回的是FutureTask对象）。由于FutureTask实现了Runnable，程序员也可以创建FutureTask，然后直接交给ExecutorService执行。

    最后，主线程可以执行FutureTask.get()方法来等待任务执行完成。主线程也可以执行FutureTask.cancel（boolean mayInterruptIfRunning）来取消此任务的执行。
三 ThreadPoolExecutor详解

线程池实现类ThreadPoolExecutor是Executor 框架最核心的类，先来看一下这个类中比较重要的四个属性
3.1 ThreadPoolExecutor类的四个比较重要的属性
![ThreadPoolExecutor比较重要的四个属性](http://my-blog-to-use.oss-cn-beijing.aliyuncs.com/18-4-16/32377576.jpg)

3.2 ThreadPoolExecutor类中提供的四个构造方法

我们看最长的那个，其余三个都是在这个构造方法的基础上产生（给定某些默认参数的构造方法）

    /**
     * 用给定的初始参数创建一个新的ThreadPoolExecutor。

     * @param keepAliveTime 当线程池中的线程数量大于corePoolSize的时候，如果这时没有新的任务提交，
     *核心线程外的线程不会立即销毁，而是会等待，直到等待的时间超过了keepAliveTime；
     * @param unit  keepAliveTime参数的时间单位
     * @param workQueue 等待队列，当任务提交时，如果线程池中的线程数量大于等于corePoolSize的时候，把该任务封装成一个Worker对象放入等待队列；
     * 
     * @param threadFactory 执行者创建新线程时使用的工厂
     * @param handler RejectedExecutionHandler类型的变量，表示线程池的饱和策略。
     * 如果阻塞队列满了并且没有空闲的线程，这时如果继续提交任务，就需要采取一种策略处理该任务。
     * 线程池提供了4种策略：
        1.AbortPolicy：直接抛出异常，这是默认策略；
        2.CallerRunsPolicy：用调用者所在的线程来执行任务；
        3.DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
        4.DiscardPolicy：直接丢弃任务；
     */
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
        if (corePoolSize < 0 ||
            maximumPoolSize <= 0 ||
            maximumPoolSize < corePoolSize ||
            keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }


3.3 如何创建ThreadPoolExecutor

在《阿里巴巴Java开发手册》“并发处理”这一章节，明确指出线程资源必须通过线程池提供，不允许在应用中自行显示创建线程。

为什么呢？

    使用线程池的好处是减少在创建和销毁线程上所消耗的时间以及系统资源开销，解决资源不足的问题。如果不使用线程池，有可能会造成系统创建大量同类线程而导致消耗完内存或者“过度切换”的问题。

另外《阿里巴巴Java开发手册》中强制线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险

    Executors 返回线程池对象的弊端如下：

        FixedThreadPool 和 SingleThreadExecutor ： 允许请求的队列长度为 Integer.MAX_VALUE,可能堆积大量的请求，从而导致OOM。
        CachedThreadPool 和 ScheduledThreadPool ： 允许创建的线程数量为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致OOM。

方式一：通过构造方法实现
通过构造方法实现
方式二：通过Executor 框架的工具类Executors来实现
我们可以创建三种类型的ThreadPoolExecutor：

    FixedThreadPool
    SingleThreadExecutor
    CachedThreadPool

对应Executors工具类中的方法如图所示：
通过Executor 框架的工具类Executors来实现

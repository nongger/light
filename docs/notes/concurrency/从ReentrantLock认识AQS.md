在锁机制中抢到资源的线程直接处理业务逻辑，抢不到资源的线程必然涉及一种排队等候机制，以及线程的阻塞/唤醒机制来保证所锁分配。  
  
## AQS体简介     
Lock接口的实现类，基本内部都聚合了一个队列同步器（AQS)的子类来完成线程访问控制。AQS主要采用CLH队列的变体来实现，将获取不到锁的线程加入队列。
将请求资源的线程封装成Node，通过CAS、自旋以及LockSupport的方式，维护state变量的状态，使并发达到同步控制的效果。

## 从ReentrantLock开始

```
ReentrantLock使用示意（仅主要调用，代码未完整展示）
private Lock lock = new ReentrantLock();
lock.lock();
try {
    System.out.println(Thread.currentThread().getName() + "获得锁");
} catch (Exception e) {
} finally {
    lock.unlock();
}
```
ReentrantLock支持公平锁和非公平锁（默认是非公平锁）本文先介绍非公平锁，最后简单介绍公平锁。    
是否公平锁可以通过构造器指定，源码如下：
```
    public ReentrantLock() {
        sync = new NonfairSync();
    }

    public ReentrantLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }
```
可以看到内部通过FairSync和NonfairSync来区分是否公平，他们都是Sync的子类，Sync继承自AbstractQueuedSynchronizer就是本文核心内容AQS。   
具体类继承关系参见下图：    
![锁内部基于AQS的实现](../../assets/img/锁AQS实现.png)

## AQS主要成员变量   
head和tail都是懒加载的，当发生线程排队是才初始化。   
state默认为0，用于控制共享资源的状态。  
```
    private transient volatile Node head;   
    
    private transient volatile Node tail;
    
    private volatile int state;
```
## ReentrantLock加锁流程   
```
当程序执行 lock.lock();
ReentrantLock内部调用NonfairSync的lock方法
    public void lock() {
        sync.lock();
    }
    
    final void lock() {
        // CAS设置state变量为1
        if (compareAndSetState(0, 1))
            // 如果设置成功（当前资源空闲）则设置当前资源使用的线程为当前线程，加锁成功
            // 独占与可重入的原理
            setExclusiveOwnerThread(Thread.currentThread());
        else
            // 如果设置失败（获取资源失败，当前资源已被占用）
            // 尝试获取锁，获取不到排队
            acquire(1);
    }
```
### acquire尝试获取锁流程  
``` 
public final void acquire(int arg) {
    // 尝试获取锁
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}

// tryAcquire由Lock自己内部实现
protected final boolean tryAcquire(int acquires) {
    return nonfairTryAcquire(acquires);
}
// 内部调用非公平尝试获取
final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    // 如果状态为0说明锁已被释放
    if (c == 0) {
        // 设定state状态为1，尝试锁定
        if (compareAndSetState(0, acquires)) {
            // 如果锁定成功更新当前使用线程为自己
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    // 如果不是0则代表已被锁定，判断一下是不是自己锁定的
    else if (current == getExclusiveOwnerThread()) {
        // 如果是自己锁定的则对state进行累加
        int nextc = c + acquires;
        if (nextc < 0) // overflow
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}

获取失败需要执行入队操作，即逻辑表达式的后半段
acquireQueued(addWaiter(Node.EXCLUSIVE), arg)
```
### AQS的队列操作    
AQS中队列是CLH的变种，使用的是虚拟双向链表    
#### 执行排队逻辑
如果队列为空需要初始化，否则直接加入队列
```
/**
 * Creates and enqueues node for current thread and given mode.
 * 为当前线程和给定模式创建节点并排队
 * @param mode Node.EXCLUSIVE for exclusive, Node.SHARED for shared
 * @return the new node
 */
private Node addWaiter(Node mode) {
    // 创建一个节点并设定模式（共享和独占，在ReentrantLock中为独占锁）
    Node node = new Node(Thread.currentThread(), mode);
    // Try the fast path of enq; backup to full enq on failure
    Node pred = tail;
    // 存在尾结点，将当前节点直接加入队尾
    if (pred != null) {
        node.prev = pred;
        if (compareAndSetTail(pred, node)) {
            pred.next = node;
            return node;
        }
    }
    // 没有尾节点代表队列尚未初始化（懒加载）
    enq(node);
    return node;
}

/**
 * 节点入队的逻辑，涉及初始化队列的逻辑
 */
private Node enq(final Node node) {
    for (;;) {
        Node t = tail;
        if (t == null) { // Must initialize 初始化部分（懒加载）
            // 初始化一个哨兵节点做为头结点
            if (compareAndSetHead(new Node()))
                tail = head;
        } else {
            // 与存在尾结点的逻辑相同，直接入队
            node.prev = t;
            if (compareAndSetTail(t, node)) {
                t.next = node;
                return t;
            }
        }
    }
}
```
#### 队列中线程获取资源的方式
线程入队后，仍会尝试获取失败才会进行阻塞park，等待资源释放后的唤醒动作unpark 
涉及LockSupport原理不详细介绍，简单理解LockSupport提供一个上限为1的许可证，unpark会发放一张许可证，park会消耗一张permit
简单理解和synchronized中使用的wait和notify作用相同，只不过LockSupport不一定要在锁中使用，以及park/unpark先后顺序没有要求。

```
final boolean acquireQueued(final Node node, int arg) {
   boolean failed = true;
   try {
       boolean interrupted = false;
       // 自旋！！！
       for (;;) {
           // 获得该节点的前置节点 
           final Node p = node.predecessor();
           // 如果前一个节点是头结点，说明它是下一个可以获取锁的线程，则尝试获取锁
           if (p == head && tryAcquire(arg)) {
               // 获取资源成功把当前节点设置为头结点 （重新维护队列将之前的head移除）
               setHead(node);
               p.next = null; // help GC
               failed = false;
               return interrupted;
           }
           // 前置节点不是head或未获取到锁，执行前置节点的判断，自旋最后park
           if (shouldParkAfterFailedAcquire(p, node) &&
               parkAndCheckInterrupt())
               interrupted = true;
       }
   } finally {
       if (failed)
           cancelAcquire(node);
   }
}

// 获取失败后执行的逻辑
private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
    int ws = pred.waitStatus;
    if (ws == Node.SIGNAL)
        /*
         * 如果前置节点时signal，则当前节点可以放心的进行park阻塞
         */
        return true;
    if (ws > 0) {
        /*
         * 前置节点已取消，需要重新维护队列，将取消的节点移出
         */
        do {
            node.prev = pred = pred.prev;
        } while (pred.waitStatus > 0);
        pred.next = node;
    } else {
        /*
         * 设置前置节点为signal
         */
        compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
    }
    return false;
}

附：头结点设置
private void setHead(Node node) {
    head = node;
    node.thread = null;// 新的哨兵节点诞生
    node.prev = null;
}
```
### 在队列中阻塞等待唤醒
```
/**
 * Convenience method to park and then check if interrupted
 *
 * @return {@code true} if interrupted
 */
private final boolean parkAndCheckInterrupt() {
    LockSupport.park(this);
    return Thread.interrupted();
}
```

## ReentrantLock释放锁流程
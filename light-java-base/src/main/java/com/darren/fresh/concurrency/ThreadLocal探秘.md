
set方法会获取当前的线程，通过当前线程获取 ThreadLocalMap对象。然后把需要存储的值放到这个 map里面。如果没有就调用 createMap创建对象。
```
public void set(T value) {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
}

ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}

void createMap(Thread t, T firstValue) {
    t.threadLocals = new ThreadLocalMap(this, firstValue);
}


```
ThreadLocal是 线程局部变量就是因为它只是通过 ThreadLocal把 变量存在了 Thread本身而已。
Thread类中
```
ThreadLocal.ThreadLocalMap threadLocals = null;

// 弱引用
static class Entry extends WeakReference<ThreadLocal<?>> {
    /** The value associated with this ThreadLocal. */
    Object value;
    Entry(ThreadLocal<?> k, Object v) {
        super(k);
        value = v;
    }
}

```
get方法就比较简单，获取当前线程，尝试获取当前线程里面的 threadLocals，如果没有获取到就调用 setInitialValue方法， setInitialValue基本和 set是一样的

```
public T get() {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null) {
            @SuppressWarnings("unchecked")
            T result = (T)e.value;
            return result;
        }
    }
    return setInitialValue();
}
```

## 类加载器
1. 启动类加载器 C/C++  rt.jar
2. 扩展类加载器 ext
3. 应用类加载器 classPath
4. 自定义类加载器

## 垃圾回收算法
1. 引用计数（存在循环引用问题）
2. 复制算法（年轻代  复制-清空-互换）
3. 标记清除（存在内存碎片）
4. 标记整理（标记-压缩  耗时）

## GC roots （进行可达性分析）
1. 虚拟机栈（局部变量表）中引用的对象
2. 方法区中的类静态属性引用的对象
3. 方法区中常量引用的对象
4. 本地方法栈JNI（Native方法）引用的对象

## JVM参数类型
* -X参数
    * java -Xint -version  解释执行
    * java -Xcomp -version 第一次使用就编译成本地代码
    * java -Xmixed -version 混合模式-先编译后执行

> jps -l 查看进程编号   
jinfo -flag 配置项 进程编号  查看进程的某项jvm配置   
jinfo -flags 18240

* -XX参数
    * jvm配置分Boolean类型形式 -XX:+/-属性，如（-XX:+PrintGCDetails）
    > jinfo -flag PrintGCDetails 17020 查看当前运行程序的jvm配置
    * KV设值型 -XX:属性key=属性值 如（-XX:MetaspaceSize=21807104）
    > jinfo -flag MetaspaceSize 17020   
    > 两个经典参数-Xms和-Xmx也属于KV设值型（较为常用起了别名）   
    > -Xms 等价于-XX:InitialHeapSize 1/64   
    > -Xmx 等价于-XX:MaxHeapSize     1/4   
* 盘点jvm家底
    * java -XX:+PrintFlagsInitial 查看初始默认值   
    * java -XX:+PrintFlagsFinal -version  查看修改后最终的值   
        * =原始值 :=修改后的值（自己修改或jvm修改后的值） 
    ```
     bool PrintGCCause             = true              {product}
     bool PrintGCDateStamps        = false             {manageable}
     bool PrintGCDetails          := true             {manageable}   
    ```
    * java -XX:+PrintCommandLineFlags -version   
* 常用参数
    * -Xms 等价于-XX:InitialHeapSize  初始内存大小分配  1/64   
    * -Xmx 等价于-XX:MaxHeapSize      最大内存分配  1/4   
    * -Xss 等价于-XX:ThreadStackSize  单个线程栈的大小  默认512-1024K （64位一般是1024K）   
    * -Xmn 等价于                     设置年轻代大小（新生代默认1/3，老年代2/3）
    * -XX:MetaspaceSize              设置元空间大小（元空间不在虚拟机中，使用本地内存；大小仅受本地内存限制）
    * -XX:+PrintGCDetails            打印GC日志
    * -XX:SurvivorRatio              设置新生代中Eden和S0/S1空间的比例，默认-XX:SurvivorRatio=8，即Eden : S0 : S1=8 : 1 : 1(设置的是Eden区占的比例
      > Eden = (R*Y)/(R+1+1)  
      > From = To = Y/(R+1+1)  
      > R：SurvivorRatio比例  
      > Y：新生代空间大小
    * -XX:NewRatio                   年轻代与老年代在堆中占比，默认-XX:NewRatio=2，新生代:老年代=1:2
    * -XX:MaxTenuringThreshold       设置垃圾的最大年龄（发生多少次youngGC后仍存活的对象进入老年代） 取值范围0~15，默认-XX:MaxTenuringThreshold=15
    
## GC日志分析
制造OOM查看GC情况 启动参数配置：-Xms10m -Xmx10m -XX:+PrintCommandLineFlags -XX:+PrintGCDetails  
```
日志说明[GC类型 [young区：GC前young区内存占用->GC后young区内存占用（young区总大小）][old区……] GC前堆内存占用->GC后堆内存占用（JVM堆总大小）GC耗时]  
[GC (Allocation Failure) [PSYoungGen: 503K->503K(2560K)] 895K->951K(9728K), 0.0006050 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]  
[Full GC (Allocation Failure) [PSYoungGen: 503K->0K(2560K)] [ParOldGen: 448K->713K(7168K)] 951K->713K(9728K), [Metaspace: 2996K->2996K(1056768K)], 0.0047257 secs] [Times: user=0.02 sys=0.00, real=0.00 secs]  

```

## 引用的强、软、弱、虚
* 强引用：只要某个对象有强引用与之关联，这个对象永远不会被回收，即使内存不足，JVM宁愿抛出OOM，也不会去回收。
* 软引用：      

## OOM异常
StackOverflowError 栈溢出，一般递归调用容易出现，栈空间仅1024K
OutOfMemoryError : Java heap space  堆溢出
OutOfMemoryError : Direct buffer memory  NIO操作不当会出现
OutOfMemoryError : GC overhead limit exceeded  过度的GC使用98%的时间换来的只有2%的收益
OutOfMemoryError : unable to create new native thread  linux系统默认运行单个进程可以创建线程数1024个

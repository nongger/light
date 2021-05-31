    
DefaultSingletonBeanRegistry
    实例化：申请内存
    初始化：变量赋值
```
/** 
    一级缓存：存放已经初始化完成的单例bean，单例池
    Cache of singleton objects: bean name to bean instance. */
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

	/** Cache of singleton factories: bean name to ObjectFactory. */
	private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

	/** 二级缓存：存放已经实例化，但是未初始化完成的bean
	Cache of early singleton objects: bean name to bean instance. */
	private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

```    
    

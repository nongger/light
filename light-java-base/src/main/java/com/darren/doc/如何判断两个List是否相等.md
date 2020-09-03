假设项目中有个需求比较两个ArrayList是否相同。
```
ArrayList<String> listA = new ArrayList<String>() {{
    add("a");
    add("b");
    add("c");
}};
ArrayList<String> listB = new ArrayList<String>() {{
    add("b");
    add("c");
    add("a");
}};
```
### 使用原生List.equals方法
我们可能首先想到的是直接使用List.equals进行比较。如下两个list你会发现返回了false！！！
```
System.out.println(listA.equals(listB));// 返回false
```
因为ArrayList实现了List接口、继承AbstractList抽象类，其equals方法是在AbstractList类中定义的，源代码如下：
 ```
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof List))
            return false;

        ListIterator<E> e1 = listIterator();
        ListIterator<?> e2 = ((List<?>) o).listIterator();
        // 注意这里：两个list是同步遍历的，如果list元素排序不同也会认为两个list不同！
        while (e1.hasNext() && e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1==null ? o2==null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }
```
从源码可以看出，equals方法并不关心List的具体实现类，只要是实现了List接口，并且所有元素相等、长度也相等的话就表明两个List是相等的。
有一点可能会被忽略这个方法隐性的要求**两个list同序**，这就是为什么我们对列子中的两个list使用equals得到的是false。

>方法要求：两个list非空，集合顺序相同
如果真的想使用该方法判断list的是否相等，可以先对两个集合进行排序
```
Collections.sort(listA);
Collections.sort(listB);
System.out.println(listA.equals(listB));// true
```
### 使用开源工具类CollectionUtils.isEqualCollection
org.apache.commons.collections4.CollectionUtils提供了集合相等的判断工具方法isEqualCollection，只要我们确保两个集合非空可以直接使用该方法来判断集合相等性。
```
System.out.println(CollectionUtils.isEqualCollection(listA, listB));// true
```
简单分析一下源码：
```
    /**
     * Returns {@code true} iff the given {@link Collection}s contain
     * exactly the same elements with exactly the same cardinalities.
     * <p>
     * That is, iff the cardinality of <i>e</i> in <i>a</i> is
     * equal to the cardinality of <i>e</i> in <i>b</i>,
     * for each element <i>e</i> in <i>a</i> or <i>b</i>.
     *
     * @param a  the first collection, must not be null  两个参数都不能为空
     * @param b  the second collection, must not be null
     * @return <code>true</code> iff the collections contain the same elements with the same cardinalities.
     * 注意这句它说明了该方法的实现原理：集合包含相同的元素且元素的基数相同
     */
    public static boolean isEqualCollection(final Collection<?> a, final Collection<?> b) {
        // 判断集合大小
        if(a.size() != b.size()) {
            return false;
        }
        final CardinalityHelper<Object> helper = new CardinalityHelper<>(a, b);
        // 判断两个集合包含的不同元素的个数是否相同
        if(helper.cardinalityA.size() != helper.cardinalityB.size()) {
            return false;
        }
        // 判断每个元素在两个集合中出现的频次是否相同
        for( final Object obj : helper.cardinalityA.keySet()) {
            if(helper.freqA(obj) != helper.freqB(obj)) {
                return false;
            }
        }
        return true;
    }
    
    // 统计每个元素的个数
    public static <O> Map<O, Integer> getCardinalityMap(final Iterable<? extends O> coll) {
        final Map<O, Integer> count = new HashMap<>();
        for (final O obj : coll) {
            final Integer c = count.get(obj);
            if (c == null) {
                count.put(obj, Integer.valueOf(1));
            } else {
                count.put(obj, Integer.valueOf(c.intValue() + 1));
            }
        }
        return count;
    }
    
    // 获得每个元素出现的频次
    private int getFreq(final Object obj, final Map<?, Integer> freqMap) {
        final Integer count = freqMap.get(obj);
        if (count != null) {
            return count.intValue();
        }
        return 0;
    }    
```
其实isEqualCollection方法的注释已经说明的比较清楚了 
>iff the collections contain the same elements with the same cardinalities.
1.集合大小是否相同
2.比较元素的个数
3.比较元素出现的频次
总体来说这种方式比使用原生的相等性判断要方便，且算法复杂度较低。

### 测试源码：
```
    @Test
    public void collection() {
        ArrayList<String> listA = new ArrayList<String>() {{
            add("a");
            add("b");
            add("c");
        }};
        ArrayList<String> listB = new ArrayList<String>() {{
            add("b");
            add("c");
            add("a");
        }};
        System.out.println(listA.equals(listB));// false,缺点：需要先对集合进行排序

        System.out.println(listA.containsAll(listB) && listB.containsAll(listA));// true,交叉包含判断，缺点：无法判断集合包含相同元素的情况[a,b,c]和[a,a,b,c]
        System.out.println(CollectionUtils.isEqualCollection(listA, listB));// true，推荐，使用简单入参非空即可，算法复杂度低，不用排序

        Collections.sort(listA);
        Collections.sort(listB);
        System.out.println(listA.equals(listB));// true，用于佐证集合排序后可以使用该API判断相等
    }
```

> 请你一定不要停下来，成为你想成为的人
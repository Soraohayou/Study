# 协程上下文-CoroutineContext

## 概念
CoroutineContext是一个容器，它包含了协程的所有上下文信息。这些上下文信息包括：
+ 协程的状态：协程的状态表示协程的生命周期。协程可以处于 Active、Completed、Canceled 等状态。
+ 协程的调度策略：协程的调度策略决定了协程在哪里执行。协程可以执行在主线程、后台线程、或其他协程池中。
+ 协程的标签：协程的标签用于标识协程。
+ 协程的拦截器：协程的拦截器用于拦截协程的执行流程。
+ 协程的异常捕获：用于处理协程内部发生的未捕获异常。

协程上下文的获取可以在协程作用域中调用.getCoroutineContext()获取,不同的协程作用域的默认上下文实现是不同的,也可以自定义上下文对象

``` kotlin
GlobalScope.launch {
    var context = this.coroutineContext
}
```
## CoroutineContext的组合

CoroutineContext由多个组件组成，这些组件可以通过 + 来进行组合，并且由于重新定义了get操作符，所以可以直接使用context[key]来获取对应的上下文组件元素。

``` kotlin
GlobalScope.launch {
    val a = this.coroutineContext + Dispatchers.IO 
}
```
CoroutineContext的内部是由key-value的形式进行存储信息的，大部分的coroutineScope的默认上下文环境都为EmptyCoroutineContext，根据运算符重载的plus方法，进行添加和替换

``` kotlin
public interface CoroutineContext {
    // 运算符重载重载[]函数
    public operator fun <E : Element> get(key: Key<E>): E?
    public fun <R> fold(initial: R, operation: (R, Element) -> R): R
    public operator fun plus(context: CoroutineContext): CoroutineContext =
        if (context === EmptyCoroutineContext) this else // fast path -- avoid lambda creation
            context.fold(this) { acc, element ->
                val removed = acc.minusKey(element.key)
                if (removed === EmptyCoroutineContext) element else {
                    // make sure interceptor is always last in the context (and thus is fast to get when present)
                    val interceptor = removed[ContinuationInterceptor]
                    if (interceptor == null) CombinedContext(removed, element) else {
                        val left = removed.minusKey(ContinuationInterceptor)
                        if (left === EmptyCoroutineContext) CombinedContext(element, interceptor) else
                            CombinedContext(CombinedContext(left, element), interceptor)
                    }
                }
            }
    public interface Element : CoroutineContext {
        public val key: Key<*>
        public override operator fun <E : Element> get(key: Key<E>): E? =
            @Suppress("UNCHECKED_CAST")
            if (this.key == key) this as E else null
        public override fun <R> fold(initial: R, operation: (R, Element) -> R): R =
            operation(initial, this)
        public override fun minusKey(key: Key<*>): CoroutineContext =
            if (this.key == key) EmptyCoroutineContext else this
    }
}
```





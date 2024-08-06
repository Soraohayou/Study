# CoroutineScope-协程作用域

```kotlin
public interface CoroutineScope {
    public val coroutineContext: CoroutineContext
}
```

CoroutineScope中只包含一个待实现的变量CoroutineContext，我们可以认为它是提供CoroutineContext的容器，保证CoroutineContext能在整个协程运行中传递下去，约束CoroutineContext的作用边界

举一个例子lifecycleScope

```kotlin
lifecycleScope(mHandler.asCoroutineDispatcher("123")).launch {

}

val LifecycleOwner.lifecycleScope: LifecycleCoroutineScope
    get() = lifecycle.coroutineScope
    
val Lifecycle.coroutineScope: LifecycleCoroutineScope
    get() {
        while (true) {
            val existing = mInternalScopeRef.get() as LifecycleCoroutineScopeImpl?
            if (existing != null) {
                return existing
            }
            val newScope = LifecycleCoroutineScopeImpl(
                this,
                SupervisorJob() + Dispatchers.Main.immediate
            )
            if (mInternalScopeRef.compareAndSet(null, newScope)) {
                newScope.register()
                return newScope
            }
        }
    }
```

在上述代码中，它创建了一个LifecycleCoroutineScopeImpl实例，它实现了CoroutineScope接口，同时传入SupervisorJob() + Dispatchers.Main作为它的CoroutineContext。最后调用了
LifecycleCoroutineScopeImpl.register()方法

``` kotlin
internal class LifecycleCoroutineScopeImpl(
    override val lifecycle: Lifecycle,
    override val coroutineContext: CoroutineContext
) : LifecycleCoroutineScope(), LifecycleEventObserver {
    init {
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            coroutineContext.cancel()
        }
    }

    fun register() {
        launch(Dispatchers.Main) {
            if (lifecycle.currentState >= Lifecycle.State.INITIALIZED) {
                lifecycle.addObserver(this@LifecycleCoroutineScopeImpl)
            } else {
                coroutineContext.cancel()
            }
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (lifecycle.currentState <= Lifecycle.State.DESTROYED) {
            lifecycle.removeObserver(this)
            coroutineContext.cancel()
        }
    }
}
```

可以看到，LifecycleCoroutineScopeImpl实现了LifecycleEventObserver接口，首先在lifecycle对象注册了当前对象为监听者并实现了onStateChanged方法，当`lifecycle.currentState <= Lifecycle.State.DESTROYED`时取消当前携程

再看一下CoroutineScope
``` kotlin
@Suppress("FunctionName")
public fun CoroutineScope(context: CoroutineContext): CoroutineScope =
    ContextScope(if (context[Job] != null) context else context + Job())
```



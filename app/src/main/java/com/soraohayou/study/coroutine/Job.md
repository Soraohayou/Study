# Job

首先来看一下job的源码
``` kotlin
public interface Job : CoroutineContext.Element {

    /**
    * 当该Job处于活动状态时，返回true——它已经开始，没有完成，也没有取消。
    * 如果没有取消或失败，等待其子任务完成的Job仍被认为是活动的。
    */
    public val isActive: Boolean

    /**
    * 当Job因任何原因完成时返回true。作业被取消或失败并已完成其执行也被视为完成。
    * Job只有在所有子任务完成后才算完成。
    */
    public val isCompleted: Boolean

    /**
    *如果该作业因任何原因被取消，无论是通过显式调用cancel，还是因为它失败或其子或父作业被取消，
    * 则返回true。在一般情况下，它并不意味着任务已经完成，因为它可能仍然在完成它正在做的事情，
    * 并等待它的子任务完成。
    */
    public val isCancelled: Boolean

    /**
    * 如果Job所在的协程还没有被启动那么调用这个方法就会启动协程
    * 如果这个协程被启动了返回true，如果已经启动或者执行完毕了返回false
    */
    public fun start(): Boolean

    /**
    * 取消此Job，可用于指定错误消息或提供有关取消原因的其他详细信息
    */
    public fun cancel(cause: CancellationException? = null)

    /**
    * 取消此Job
    */
    public fun cancel(): Unit = cancel(null)

    public fun cancel(cause: Throwable? = null): Boolean
}
```
有三个状态查询的变量和三个操控状态的方法。由此可见job的作用：监视协程的生命周期、改变协程的状态


``` kotlin
| **State**                        | [isActive] | [isCompleted] | [isCancelled] |
| -------------------------------- | ---------- | ------------- | ------------- |
| _New_ (optional initial state)   | `false`    | `false`       | `false`       |
| _Active_ (default initial state) | `true`     | `false`       | `false`       |
| _Completing_ (transient state)   | `true`     | `false`       | `false`       |
| _Cancelling_ (transient state)   | `false`    | `false`       | `true`        |
| _Cancelled_ (final state)        | `false`    | `true`        | `true`        |
| _Completed_ (final state)        | `false`    | `true`        | `false`       |
```
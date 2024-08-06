package com.soraohayou.study.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @description:
 * @author: admin
 * @date: 2024/6/25
 * @email: 1145338587@qq.com
 */

/**
 * 结果
 * 1. timeConsuming - 11
 * 2 timeConsuming - 12
 * 3 timeConsuming - 12
 * 由于携程调度器的原因，Default和IO所处在不同的线程池，虽然2和3的线程id一样，但是其属于不同的线程池。CPU会在切汇线程时，对线程调度进行优化，
 * 所以可能和在挂起线程时所在的线程不同
 *
 */

fun main() {
    CoroutineScope(Dispatchers.Default).launch {
        println("1. timeConsuming - ${Thread.currentThread().id}")
        timeConsuming()
        println("3 timeConsuming - ${Thread.currentThread().id}")
    }
    Thread.sleep(3000);
}

private suspend fun timeConsuming() = withContext(Dispatchers.IO) {
    delay(2000)
    println("2 timeConsuming - ${Thread.currentThread().id}")
    "123"
}
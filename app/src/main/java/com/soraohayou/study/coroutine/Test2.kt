package com.soraohayou.study.coroutine

import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.concurrent.thread

/**
 * @description:
 * @author: admin
 * @date: 2024/6/25
 * @email: 1145338587@qq.com
 */
/**
 * 1
 */
val work = Runnable {
    printlnWithThread("do work 1")
    switchThread3()
}

/**
 * 2
 */
fun switchThread3() = thread {
    printlnWithThread("do work 2")
    works.add(Runnable { printlnWithThread("do work 3") })
}

/**
 * 3
 */
val otherWork1 = Runnable {
    Thread.sleep(100) // 模拟耗时, 避免main方法中work结束太早，newThread添加work3失败
    printlnWithThread("do work a")
}

/**
 * 4
 */
val otherWork2 = Runnable {
    printlnWithThread("do work X")
}

// prevent ConcurrentModificationException
val works = ConcurrentLinkedQueue<Runnable>()
fun main() {
    works.addAll(listOf(work, otherWork1, otherWork2))
    works.forEach { it.run() }
}

fun printlnWithThread(str: String) {
    println("${Thread.currentThread().name} : $str")
}

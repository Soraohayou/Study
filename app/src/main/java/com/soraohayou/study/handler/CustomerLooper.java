package com.soraohayou.study.handler;

import java.util.UUID;

/**
 * @description:
 * @author: admin
 * @date: 2024/8/1
 * @email: 1145338587@qq.com
 */
public class CustomerLooper {

    private String id = UUID.randomUUID().toString();

    private Object mLock = new Object();

    private static ThreadLocal<CustomerLooper> mLooper = new ThreadLocal<>();

    private CustomerMessageQueue mMessageQueue;

    /**
     * 准备
     */
    public static CustomerLooper prepare() {
        CustomerLooper looper = new CustomerLooper();
        mLooper.set(looper);
        return looper;
    }

    /**
     * 开始轮循mMessageQueue
     */
    public void loop() {
        while (getOnceMessage()) {
        }
    }

    private boolean getOnceMessage() {
        return true;
    }

}

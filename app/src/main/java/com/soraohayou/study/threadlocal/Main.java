package com.soraohayou.study.threadlocal;

import androidx.annotation.Nullable;

/**
 * @description:
 * @author: admin
 * @date: 2024/7/10
 * @email: 1145338587@qq.com
 */
public class Main {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>() {

        @Nullable
        @Override
        protected String initialValue() {
            return "init";
        }

    };

    public static void main(String[] args) throws InterruptedException {
        new Thread(new thread1()).start();
        new Thread(new thread2()).start();
        Thread.sleep(5000);
    }

    static class thread1 implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "  " + threadLocal.get());
        }
    }

    static class thread2 implements Runnable {

        @Override
        public void run() {
            threadLocal.set("4321");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "  " + threadLocal.get());
        }
    }
}

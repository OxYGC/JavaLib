package com.yanggc.javalib.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过ReentrantLock的lock以及unlock 进行定制线程
 * Description:
 * 交替打印AA BB CC 十次
 * 通过前面的线程 循环带出后面线程
 *
 *
 * @Author: YangGC
 * DateTime: 08-21
 */
public class ThreadOfRLock {

    // 通过JDK5中的Lock锁来保证线程的访问的互斥
    static Lock rLock = new ReentrantLock();
    //通过state的值来确定是否打印
    private static int state = 1;

    static class ThreadA extends Thread {
        @Override
        public void run() {
            for (int i = 1; i <= 10;) {
                try {
                    rLock.lock();
                    // 多线程并发，不能用if，必须用循环测试等待条件，避免虚假唤醒
                    while (state % 3 == 1) {
                        System.out.println(Thread.currentThread().getName()+"->> AA");
                        state++;
                        i++;
                    }
                } finally {
                    rLock.unlock();
                }
            }
        }
    }
    static class ThreadB extends Thread {
        @Override
        public void run() {
            for (int i = 1; i <= 10;) {
                try {
                    rLock.lock();
                    while (state % 3 == 2) {
                        System.out.println(Thread.currentThread().getName()+"->> BB");
                        state++;
                        i++;
                    }
                } finally {
                    rLock.unlock();
                }
            }
        }
    }
    static class ThreadC extends Thread {
        @Override
        public void run() {
            for (int i = 1; i <= 10;) {
                try {
                    rLock.lock();
                    while (state % 3 == 0) {
                        System.out.println(Thread.currentThread().getName()+"->> CC");
                        state++;
                        i++;

                    }
                } finally {
                    rLock.unlock();
                }
            }
        }
    }
    public static void main(String[] args) {
        new ThreadA().start();
        new ThreadB().start();
        new ThreadC().start();
    }



}

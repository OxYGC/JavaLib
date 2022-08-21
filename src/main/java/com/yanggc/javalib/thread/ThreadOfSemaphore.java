package com.yanggc.javalib.thread;

import java.util.concurrent.Semaphore;

/**
 * 信号量方式
 * Description:
 * 定义的全局变量，使用static
 * 通过static Semaphore A = new Semaphore(1);，代表信号量为1
 * <p>
 * 具体其代码块为：
 * semaphore.acquire(); 获取信号量
 * semaphore.release();释放信号量
 *
 * 循环打印AA BB CC 三次
 *
 * @Author: YangGC
 * DateTime: 08-21
 */
public class ThreadOfSemaphore {


    // 以A开始的信号量,初始信号量数量为1
    private static Semaphore A = new Semaphore(1);
    // B、C信号量,A完成后开始,初始信号数量为0
    private static Semaphore B = new Semaphore(0);
    private static Semaphore C = new Semaphore(0);

    static class ThreadA extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {
                    A.acquire();// A获取信号执行,A信号量减1,当A为0时将无法继续获得该信号量
                    System.out.println("AA");
                    B.release();// B释放信号，B信号量加1（初始为0），此时可以获取B信号量
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {
                    B.acquire();
                    System.out.println("BB");
                    C.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadC extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {
                    C.acquire();
                    System.out.println("CC");
                    A.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        new ThreadA().start();
        new ThreadB().start();
        new ThreadC().start();
    }

//    static class ThreadOfSemaphoreTest {
//        public static void main(String[] args) {
//            ThreadOfSemaphore threadOfSemaphore = new ThreadOfSemaphore();
//
//        }
//    }


}

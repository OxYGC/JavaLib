package com.yanggc.javalib.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 定制化通信
 * <p>
 * Description:
 * 启动三个线程，按照如下要求：
 * AA打印5此，BB打印10次，CC打印15次，一共进行10轮
 * 每个线程添加一个标志位，是该标志位则执行操作，并且修改为下一个标志位，通知下一个标志位的线程
 * 创建一个可重入锁private Lock lock = new ReentrantLock();
 * 分别创建三个开锁通知private Condition c1 = lock.newCondition();
 * 通过标志位Flag 和Condition 的wait 当前线程, sign(钥匙) 唤醒下一个线程 进行交替打印
 *
 * @Author: YangGC
 * DateTime: 08-21
 */
public class CustomThread {

    //定义标志位
    private int flag = 1;

    //创建Lock锁
    Lock rLock = new ReentrantLock();

    //创建3个Condition 环境条件（Condition是绑定当前线程的）
    Condition c1 = rLock.newCondition();
    Condition c2 = rLock.newCondition();
    Condition c3 = rLock.newCondition();

    //打印5次,loop表示第几轮
    public void print5Count(int loop) throws InterruptedException {
        //上锁
        rLock.lock();
        try {
            //打印5次的标志位为1 打印10次标志位为2 打印15次的标志位为3, 非本次条件为等待
            while (flag != 1) {
                c1.await();
            }
            //业务处理
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + i + ": 轮数:" + loop);
            }
            //通知 修改标志位2
            flag = 2;
            //
            c2.signal();
        } finally {
            //释放锁
            rLock.unlock();
        }
    }

    //打印10次,loop表示第几轮
    public void print10Count(int loop) throws InterruptedException {
        //上锁
        rLock.lock();
        try {
            //打印5次的标志位为1 打印10次标志位为2 打印15次的标志位为3, 非本次条件为等待
            while (flag != 2) {
                c2.await();
            }
            //业务处理
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + i + ": 轮数:" + loop);
            }
            //通知 修改标志位2
            flag = 3;
            c3.signal();
        } finally {
            //释放锁
            rLock.unlock();
        }
    }


    //打印5次,loop表示第几轮
    public void print15Count(int loop) throws InterruptedException {
        //上锁
        rLock.lock();
        try {
            //打印5次的标志位为1 打印10次标志位为2 打印15次的标志位为3, 非本次条件为等待
            while (flag != 3) {
                c3.await();
            }
            //业务处理
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + i + ": 轮数:" + loop);
            }
            //通知 标志位为1的
            flag = 1;
            c1.signal();
        } finally {
            //释放锁
            rLock.unlock();
        }
    }

    public static class CustomThreadTest {

        public static void main(String[] args) {
            CustomThread customThread = new CustomThread();

            new Thread(() -> {
                for (int i = 1; i <= 10; i++) {
                    try {
                        customThread.print5Count(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "AA").start();

            new Thread(() -> {
                for (int i = 1; i <= 10; i++) {
                    try {
                        customThread.print10Count(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "BB").start();


            new Thread(() -> {
                for (int i = 1; i <= 10; i++) {
                    try {
                        customThread.print15Count(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "CC").start();
        }
    }


}

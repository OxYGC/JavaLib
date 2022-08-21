package com.yanggc.javalib.thread;

/**
 * 通过synchronized锁定 对象标志位的 wait与 notifyAll 定制线程间的通信
 * Description:
 * <p>
 * 启动三个线程，按照如下要求：
 * AA打印5次，BB打印10次，CC打印15次，一共进行10轮
 * <p>
 *
 * @Author: YangGC
 * DateTime: 08-21
 */
public class ThreadOfSynchronized {


    private static String message = "AA";
    private static Integer loop = 1;
    private static Object lock = new Object();

    public void ThreadOfSync() {
        new Thread(() -> {
            int count = 1;

            synchronized (lock) {
                while (count <= 5) {
                    while (!message.equals("AA")) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(message);
                    count = count + 1;
                }
                //while end
                message = "BB";
                lock.notifyAll();
            }
            //sync end

        }, "AA").start();

        new Thread(() -> {
            int count = 1;

            synchronized (lock) {
                while (count <= 10) {
                    while (!message.equals("BB")) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //while end
                    System.out.println(message);
                    count = count + 1;

                }
                message = "CC";
                lock.notifyAll();
            }
            //sync end
        }, "BB").start();


        new Thread(() -> {
            int count = 1;
            synchronized (lock) {
                while (count <= 15) {
                    while (!message.equals("CC")) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //while end
                    System.out.println(message);
                    count = count + 1;
                }
                message = "AA";
                loop+=1;
                System.out.println("current loop: "+ loop);
                lock.notifyAll();
            }
            //sync end
        }, "CC").start();
    }


    public static class ThreadOfSynchronizedTest {
        public static void main(String[] args) {
            ThreadOfSynchronized threadOfSynchronized = new ThreadOfSynchronized();
            for (int i = 1; i <= 10; i++) {
                threadOfSynchronized.ThreadOfSync();
            }
        }
    }


}

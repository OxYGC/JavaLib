package com.yanggc.javalib.thread;

/**
 * 通过线程配合sleep不加锁定义状态变量(非线程安全,可能会少打印)
 *
 * Description:
 * <p>
 * 启动三个线程，按照如下要求：
 * AA打印5此，BB打印10次，CC打印15次，一共进行10轮
 * <p>
 *
 * @Author: YangGC
 * DateTime: 08-21
 */
public class ThreadOfSleep {

    private static String message = "AA";
    private static Integer loop = 1;

    public void TdSleep() {
        new Thread(() -> {
            int count = 1;
            while (count <= 5) {
                while (!message.equals("AA")) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(message);
                count = count + 1;
            }
            //while end
            message = "BB";
        }, "AA").start();

        new Thread(() -> {
            int count = 1;

                while (count <= 10) {
                    while (!message.equals("BB")) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //while end
                    System.out.println(message);
                    count = count + 1;

                }
                message = "CC";

        }, "BB").start();


        new Thread(() -> {
            int count = 1;
                while (count <= 15) {
                    while (!message.equals("CC")) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //while end
                    System.out.println(message);
                    count = count + 1;
                }
                message = "AA";
//                loop+=1;
//                System.out.println("current loop: "+ loop);

        }, "CC").start();

    }


    static class ThreadOfSleepTest {

        public static void main(String[] args) {
            ThreadOfSleep threadOfSleep = new ThreadOfSleep();
            for (int i = 1; i <= 10; i++) {
                threadOfSleep.TdSleep();
            }
//            threadOfSleep.TdSleep();
        }
    }


}

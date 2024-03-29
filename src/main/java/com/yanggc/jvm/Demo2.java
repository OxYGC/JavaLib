package com.yanggc.jvm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Description:
 *
 * @Author: YangGC
 * DateTime: 08-25
 */
public class Demo2 {
    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Demo2 test = new Demo2();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 10000; i++) {
            reader.readLine();
            int a = (int) Math.round(Math.random() * 1000);
            int b = (int) Math.round(Math.random() * 1000);
            System.out.println(test.add(a, b));
        }
    }
}

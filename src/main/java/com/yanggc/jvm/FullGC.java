package com.yanggc.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: YangGC
 * DateTime: 08-25
 */
public class FullGC {

    public static void main(String[] args) {
        byte[] array1 = new byte[4 * 1024 * 1024];
        array1 = null;

        byte[] array2 = new byte[2 * 1024 * 1024];
        byte[] array3 = new byte[2 * 1024 * 1024];
        byte[] array4 = new byte[2 * 1024 * 1024];
        byte[] array5 = new byte[128 * 1024];

        byte[] array6 = new byte[2 * 1024 * 1024];
        int i = 0;
        List<Object> integerList = new ArrayList<>();

        while (true) {
            integerList.add(i);
            i++;
            System.out.println(i);

        }
    }

}

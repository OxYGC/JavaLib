package com.yanggc.bizlib;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 电子红包生成器: 参考微信红包
 * Description:
 *
 * @Author: YangGC
 * DateTime: 05-16
 */
public class RedEnvelopesGenerator {


    /**
     * 剩余随机计算 (对剩余的金额进行随机计算)
     * 通过测试可以得出先领取的金额较大，后领取的金额较小,显然不太公平
     * @param totalAmount 总数量
     * @param number      分包的人数
     * @param minAmount   单包最小金额
     * @return
     */
    public static List<BigDecimal> surplusRandom(BigDecimal totalAmount, BigDecimal number, BigDecimal minAmount) {
        if(minAmount == null){ minAmount = BigDecimal.valueOf(0.01); }
        BigDecimal surplusAmount = totalAmount.subtract(minAmount.multiply(number)).setScale(2,RoundingMode.CEILING);
        List<BigDecimal> result = new ArrayList<>(number.intValue());
        BigDecimal currentAmount;
        Random random = new Random();
        for (int i = 0; i < number.intValue(); i++) {
            if (i == (number.intValue() - 1)) {
                currentAmount = surplusAmount;
            } else {
                currentAmount = surplusAmount.multiply(BigDecimal.valueOf(random.nextInt(100)).divide(BigDecimal.valueOf(100))).setScale(2, RoundingMode.CEILING);
                surplusAmount = currentAmount.compareTo(surplusAmount) > 0 ? BigDecimal.ZERO : surplusAmount.subtract(currentAmount);
            }
            result.add(currentAmount.add(minAmount));
        }
        return result;
    }


    /**
     * 二倍均值法 (每次计算最高不超过平均每人红包的2倍)
     * 微信红包默认为该算法
     * 此算法很好的保证了几率大致均等
     *
     * @param totalAmount 总数量
     * @param number      分包的人数
     * @param minAmount   单包最小金额
     * @return
     */
    public static List<BigDecimal> doubleMean(BigDecimal totalAmount, BigDecimal number, BigDecimal minAmount) {
        //固定常量K倍均值算法
        final int k = 2;
        if (minAmount == null) {
            minAmount = BigDecimal.valueOf(0.01);
        }
        List<BigDecimal> result = new ArrayList<>(number.intValue());
        BigDecimal surplusAmount = totalAmount.subtract(minAmount.multiply(number));
        BigDecimal currAmount;
        Random random = new Random();
        for (int i = 0; i < number.intValue(); i++) {
            //最后一个是直接剩余的金额
            if (i == number.intValue() - 1) {
                currAmount = surplusAmount;
            } else {
                currAmount = surplusAmount.multiply(BigDecimal.valueOf(k)).divide(number.subtract(BigDecimal.valueOf((i))), 2, BigDecimal.ROUND_CEILING)
                        .multiply(BigDecimal.valueOf(random.nextInt(100))).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_CEILING);
            }
            //如果当前摇出来的金额大于剩余金额就直接舍弃,金额为0
            surplusAmount = surplusAmount.compareTo(currAmount) > 0 ? surplusAmount.subtract(currAmount) : BigDecimal.ZERO;
            result.add(currAmount.add(minAmount));
        }
        return result;
    }


    /**
     * 整体随机
     * 以10元10个红包为例，随机10个数，红包金额公式为：红包总额 * 随机数/随机数总和，假设10个随机数为[5,9,8,7,6,5,4,3,2,1],10个随机数总和为50，
     * 第一个红包10*5/50，得1元。
     * 第二个红包10*9/50，得1.8元。
     * 第三个红包10*8/50，得1.6元。
     *
     *
     * 测试后结论:
     * 此算法随机性较大。
     * @param totalAmount
     * @param number
     * @param minAmount
     * @return
     */
    public static List<BigDecimal> overallRandom(BigDecimal totalAmount, BigDecimal number, BigDecimal minAmount) {
        List<BigDecimal> result = new ArrayList<>(number.intValue());
        BigDecimal surplusAmount = totalAmount.subtract(minAmount.multiply(number)).setScale(2,RoundingMode.CEILING);

        int randomSumInt = 0;
        int[] rndElements = new int[number.intValue()];
        Random random = new Random();
        for (int i = 0; i < number.intValue(); i++) {
            rndElements[i] = random.nextInt(100);
            randomSumInt += rndElements[i];
        }
        BigDecimal randomSum = BigDecimal.valueOf(randomSumInt);
        BigDecimal currentAmount;

        for (int i = 0; i < number.intValue(); i++) {
            if (i == (number.intValue() - 1)) {
                currentAmount = surplusAmount;
            }else{
                currentAmount = surplusAmount.multiply(BigDecimal.valueOf(rndElements[i]).divide(randomSum,2,RoundingMode.CEILING)).setScale(2,RoundingMode.CEILING);
                surplusAmount = currentAmount.compareTo(surplusAmount) > 0 ? BigDecimal.ZERO : surplusAmount.subtract(currentAmount);
            }
            result.add(currentAmount.add(minAmount));
        }
        return result;
    }





    public static void main(String[] args) {
        BigDecimal origTotalAmount = BigDecimal.valueOf(100);
        BigDecimal number = BigDecimal.valueOf(10);
//        List<BigDecimal> bigDecimals = doubleMean(origTotalAmount, number, BigDecimal.valueOf(0.01));
        List<BigDecimal> bigDecimals = overallRandom(origTotalAmount, number, BigDecimal.valueOf(0.01));
//        List<BigDecimal> bigDecimals = surplusRandom(origTotalAmount, number, null);
        BigDecimal totalAmount = bigDecimals.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(bigDecimals);
        System.out.println(origTotalAmount + "..totalAmount: " + totalAmount);

    }

}


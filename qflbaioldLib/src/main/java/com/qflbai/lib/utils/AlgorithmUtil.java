package com.qflbai.lib.utils;

/**
 * @author WenXian Bai
 * @Date: 2018/4/27.
 * @Description: 算法工具类
 */

public class AlgorithmUtil {
    /**
     * 最大公约数
     * @param numerator
     * @param denominator
     * @return
     */
    public static int gcd(int numerator, int denominator) {
        int a = numerator;
        int b = denominator;

        while (b != 0) {
            int oldB = b;

            b = a % b;
            a = oldB;
        }

        return Math.abs(a);
    }

    /**
     * int转double
     * @param value
     * @return
     */
    public double intToDouble(int value){
        return value+0.0000;
    }
}

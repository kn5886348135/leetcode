package com.serendipity.algo2bitoperation;

/**
 * @author jack
 * @version 1.0
 * @description 奇数 odd number
 *              偶数 even number
 * @date 2023/03/30/15:52
 */
public class EvenTimesOddTimes {

    public static void main(String[] args) {
        // 位运算交换两个整数
        int a = 5;
        int b = 7;

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(a);
        System.out.println(b);
    }

    // arr中，只有一种数，出现奇数次
    // 运用异或运算的交换律和结合律，数组所有元素异或的结果就是答案
    // 0 ^ N == N
    // N ^ N == 0
    public static void printOddTimesNum1(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            ans ^= arr[i];
        }
        System.out.println(ans);
    }

    // arr中，有两种数，出现奇数次
    public static void printOddTimesNum2(int[] arr) {
        int res = 0;
        // 拿到奇数次数的异或结果
        for (int i = 0; i < arr.length; i++) {
            res ^= arr[i];
        }
        // 拿到最右边的1
        int rightOne = res & (-res);

        // 其中一个奇数次的数记为a
        int a = 0;
        for (int i = 0 ; i < arr.length;i++) {
            // 跳过偶数次的数和b
            if ((arr[i] & rightOne) != 0) {
                a ^= arr[i];
            }
        }
        System.out.println(a + " " + (res ^ a));
    }
}

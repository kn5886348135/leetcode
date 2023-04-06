package com.serendipity.common;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/03/30/14:59
 */
public class CommonUtil {

    // 二进制交换数组的两个位置
    public static void swap(int[] arr, int i, int j) {
        // i == j时，异或结果为0
        if (i == j) {
            return;
        }
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    // 二进制交换整数
    public static void swap(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
    }

    // 计算一个整数的二进制中1的个数
    public static int bit1counts(int num) {
        int count = 0;
        while(num != 0) {
            // 拿到最右边的1
            int rightOne = num & ((~num) + 1);
            count++;
            // 最右边的1变成0，其他的1和0保持不变
            num ^= rightOne;
            // num -= rightOne
        }
        return count;
    }

    // 生成数组，长度随机，最大值随机
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    public static int[] generateRandomDiffArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (maxSize * Math.random() + 2)];
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            int random = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            while (set.contains(random)) {
                random = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            }
            set.add(random);
            arr[i] = random;
        }
        return arr;
    }

    // 生成相邻不相等的数组
    public static int[] generateRandomDiffAdjacentArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        arr[0] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
        for (int i = 1; i < arr.length; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
            } while (arr[i] == arr[i - 1]);
        }
        return arr;
    }

    // 打印数组
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 打印int类型二进制字符串方法
    public static void printCompleteBinaryStr(int num) {
        for (int i = 31; i >= 0; i--) {
            // 打印二进制位
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
            // 增加空格
            if (i < 31 && (i + 1) % 8 == 0) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}

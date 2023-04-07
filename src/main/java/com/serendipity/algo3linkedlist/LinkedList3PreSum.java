package com.serendipity.algo3linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jack
 * @version 1.0
 * @description 前缀和
 * @date 2023/04/07/23:13
 */
public class LinkedList3PreSum {

    public static void main(String[] args) {
        int count = 1000000;
        Set set = new HashSet();
        while (count > 0) {
            set.add(f1());
            count--;
        }
        for (Object o : set) {
            System.out.println(o + "\t");
        }
    }

    private static int preSum(int[] arr, int left,int right) {
        int length = arr.length;
        int[] preSum = new int[length];
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                preSum[i] = arr[i];
            } else {
                preSum[i] = preSum[i - 1] + arr[i];
            }
        }
        return left == 0 ? preSum[right] : preSum[right] - preSum[left];
    }

    // 随机返回1 2 3 4 5
    private static int f1(){
        return (int) (Math.random() * 5) + 1;
    }

    // 等概率返回0或者1
    private static int f2(){
        int ans = 0;
        do {
            ans = f1();
        } while (ans == 3);
        return ans < 3 ? 0 : 1;
    }

    // 000 - 111 等概率
    private static int f3(){
        return f2() << 2 + f2() << 1 + f2();
    }

    // 固定概率返回0或者1
    private static int x(){
        return Math.random() < 0.84 ? 0 : 1;
    }

    // 等概率返回0或者1
    private static int y() {
        int ans = 0;
        do {
            ans = x();
        } while (ans == x());
        return ans;
    }
}

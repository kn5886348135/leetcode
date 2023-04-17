package com.serendipity.algo12dynamicprogramming.quadrangle;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个非负数组arr，长度为N，
 *              那么有N-1种方案可以把arr切成左右两部分
 *              每一种方案都有，min{左部分累加和，右部分累加和}
 *              求这么多方案中，min{左部分累加和，右部分累加和}的最大值是多少？
 *              整个过程要求时间复杂度O(N)
 * @date 2023/03/25/23:09
 */
public class BestSplitForAll {

    public static void main(String[] args) {
        int n = 20;
        int max = 30;
        int testTime = 1000000;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n);
            int[] arr = randomArray(len, max);
            int ans1 = bestSplit1(arr);
            int ans2 = bestSplit2(arr);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
    }

    // 对数器
    public static int bestSplit1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int len = arr.length;
        int ans = 0;
        for (int s = 0; s < len - 1; s++) {
            int sumL = 0;
            for (int left = 0; left <= s; left++) {
                sumL += arr[left];
            }
            int sumR = 0;
            for (int right = s + 1; right < len; right++) {
                sumR += arr[right];
            }
            ans = Math.max(ans, Math.min(sumL, sumR));
        }
        return ans;
    }

    // 利用前缀和数组
    public static int bestSplit2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int len = arr.length;
        int sumAll = 0;
        for (int num : arr) {
            sumAll += num;
        }
        int ans = 0;
        int sumL = 0;
        for (int s = 0; s < len - 1; s++) {
            sumL += arr[s];
            int sumR = sumAll - sumL;
            ans = Math.max(ans, Math.min(sumL, sumR));
        }
        return ans;
    }

    public static int[] randomArray(int len, int max) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }
}

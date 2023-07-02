package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 最多能完成排序的块
 *              给定一个长度为 n 的整数数组 arr ，它表示在 [0, n - 1] 范围内的整数的排列。
 *
 *              我们将 arr 分割成若干 块 (即分区)，并对每个块单独排序。将它们连接起来后，使得连接的结果和按升序排序后的原数组相同。
 *
 *              返回数组能分成的最多块数量。
 * @date 2023/06/29/0:59
 */
public class LeetCode769 {

    public static void main(String[] args) {

    }

    // 贪心算法
    // 定理一：对于某个块 A，它由块 B 和块 C 组成，即 A=B+C。如果块 B 和块 C 分别排序后都与原数组排序后的
    // 结果一致，那么块 A 排序后与原数组排序后的结果一致。
    // 定理二：对于某个块 A，它由块 B 和块 C 组成，即 A=B+C。如果块 A 和块 B 分别排序后都与原数组排序后的
    // 结果一致，那么块 C 排序后与原数组排序后的结果一致。
    public static int maxChunksToSorted1(int[] arr) {
        int m = 0, res = 0;
        for (int i = 0; i < arr.length; i++) {
            m = Math.max(m, arr[i]);
            if (m == i) {
                res++;
            }
        }
        return res;
    }

    public int maxChunksToSorted2(int[] arr) {
        int n = arr.length, ans = 0;
        for (int i = 0, j = 0, min = n, max = -1; i < n; i++) {
            min = Math.min(min, arr[i]);
            max = Math.max(max, arr[i]);
            if (j == min && i == max) {
                ans++; j = i + 1; min = n; max = -1;
            }
        }
        return ans;
    }

}

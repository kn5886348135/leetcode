package com.serendipity.skills.subseqlen;

import com.serendipity.common.CommonUtil;

import java.util.HashMap;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个整数组成的无序数组arr，值可能正、可能负、可能0
 *              给定一个整数值K
 *              找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
 *              返回其长度
 * @date 2023/03/25/19:43
 */
public class LongestSumSubArrayLength {

    public static void main(String[] args) {
        int maxSize = 50;
        int maxValue = 100;
        int testTime = 500000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, false);
            int k = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
            int ans1 = maxLength(arr, k);
            int ans2 = right(arr, k);
            if (ans1 != ans2) {
                CommonUtil.printArray(arr);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    public static int maxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // key 前缀和
        // value 最早出现前缀和是key的value
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int len = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (map.containsKey(sum - k)) {
                len = Math.max(i - map.get(sum - k), len);
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return len;
    }

    // 对数器
    public static int right(int[] arr, int K) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (valid(arr, i, j, K)) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }

    public static boolean valid(int[] arr, int L, int R, int K) {
        int sum = 0;
        for (int i = L; i <= R; i++) {
            sum += arr[i];
        }
        return sum == K;
    }
}

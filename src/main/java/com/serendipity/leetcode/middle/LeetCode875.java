package com.serendipity.leetcode.middle;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description 爱吃香蕉的珂珂
 *              珂珂喜欢吃香蕉。这里有 n 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 h 小时后回来。
 *
 *              珂珂可以决定她吃香蕉的速度 k （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 k 根。如果这堆香蕉
 *              少于 k 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。
 *
 *              珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
 *
 *              返回她可以在 h 小时内吃掉所有香蕉的最小速度 k（k 为整数）。
 * @date 2023/06/28/16:29
 */
public class LeetCode875 {

    public static void main(String[] args) {
        int maxSize = 200;
        int maxValue = 2000000;
        int testTimes = 100000;
        int maxHour = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] piles = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int h = (int) (Math.random() * maxHour);
            while (h < piles.length) {
                h = (int) (Math.random() * maxHour);
            }
            if (validateMinEatingSpeed(piles, h) != minEatingSpeed(piles, h)) {
                System.out.println("minEatingSpeed failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器
    public static int validateMinEatingSpeed(int[] piles, int h) {
        int max = 0;
        for (int pile : piles) {
            max = Math.max(max, pile);
        }

        for (int i = 1; i < max; i++) {
            if (eatingCost(i, piles) <= h) {
                return i;
            }
        }
        return max;
    }

    // 二分法查找能吃完的最小值
    public static int minEatingSpeed(int[] piles, int h) {
        int max = 0;
        for (int pile : piles) {
            max = Math.max(max, pile);
        }
        int left = 1;
        int right = max;
        int mid;
        int ans = max;
        while (left <= right) {
            mid = left + ((right - left) >> 1);
            if (eatingCost(mid, piles) <= h) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    // 判断是否能吃完
    private static long eatingCost(int k, int[] piles) {
        long total = 0;
        for (int pile : piles) {
            total += (pile + k - 1) / k;
        }
        return total;
    }
}

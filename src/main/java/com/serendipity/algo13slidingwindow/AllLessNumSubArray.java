package com.serendipity.algo13slidingwindow;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.LinkedList;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个整型数组arr，和一个整数num
 *              某个arr中的子数组sub，如果想达标，必须满足：
 *              sub中最大值 – sub中最小值 <= num，
 *              返回arr中达标子数组的数量
 * @date 2023/03/15/18:18
 */
public class AllLessNumSubArray {

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 200;
        int testTimes = 100000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, false);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = allLessNumSubArray1(arr, sum);
            int ans2 = allLessNumSubArray2(arr, sum);
            if (ans1 != ans2) {
                System.out.println(MessageFormat.format("allLessNumSubArray failed, ans1 {0}, ans2 {1}, sum {2}",
                        new String[]{String.valueOf(ans1), String.valueOf(ans2), String.valueOf(sum)}));
                CommonUtil.printArray(arr);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 暴力递归 对数器 子数组连续
    public static int allLessNumSubArray1(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 0) {
            return 0;
        }
        int len = arr.length;
        int ans = 0;
        for (int left = 0; left < len; left++) {
            for (int right = left; right < len; right++) {
                int max = arr[left];
                int min = arr[left];
                for (int i = left + 1; i <= right; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= num) {
                    ans++;
                }
            }
        }
        return ans;
    }

    // 滑动窗口
    // 如果S[left...right]达标，那么S[left...right]的子数组一定达标
    // 如果S[left...right]不达标，那么包含S[left...right]的数组一定不达标
    public static int allLessNumSubArray2(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 0) {
            return 0;
        }
        int len = arr.length;
        int ans = 0;
        LinkedList<Integer> maxWindow = new LinkedList<>();
        LinkedList<Integer> minWindow = new LinkedList<>();
        int right = 0;
        // 窗口右侧向右滑动到不满足条件后，窗口左侧向右滑动一次
        // 时间复杂度为O(n)
        for (int left = 0; left < len; left++) {

            // 窗口右侧一直向右滑动，直到不满足条件
            while (right < len) {
                // 子数组最大值保持在队列头部
                while (!maxWindow.isEmpty() && arr[maxWindow.peekLast()] <= arr[right]) {
                    maxWindow.pollLast();
                }
                maxWindow.addLast(right);
                // 子数组最小值保持在队列头部
                while (!minWindow.isEmpty() && arr[minWindow.peekLast()] >= arr[right]) {
                    minWindow.pollLast();
                }
                minWindow.addLast(right);

                // 判断是否合法数组
                if (arr[maxWindow.peekFirst()] - arr[minWindow.peekFirst()] > num) {
                    break;
                }
                right++;
            }
            ans += right - left;
            // 因为下一次循环left要向右移动，所以要移除left
            if (maxWindow.peekFirst() == left) {
                maxWindow.pollFirst();
            }
            if (minWindow.peekFirst() == left) {
                minWindow.pollFirst();
            }
        }
        return ans;
    }
}

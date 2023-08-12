package com.serendipity.algo12dynamicprogramming.otherdp;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个数组arr，和一个正数M，
 *              返回在arr的子数组在长度不超过M的情况下，最大的累加和
 * @date 2023/03/28/21:51
 */
public class MaxSumLengthNoMore {

    public static void main(String[] args) {
        int max = 50;
        int maxSize = 20;
        int maxValue = 100;
        int testTime = 1000000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, false);
            int[] arr1 = new int[arr.length];
            System.arraycopy(arr, 0, arr1, 0, arr.length);
            int[] arr2 = new int[arr.length];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            int m = ((int) Math.random() * max) + 1;
            while (m > arr.length) {
                m = ((int) Math.random() * max) + 1;
            }
            int ans1 = maxSum1(arr1, m);
            int ans2 = maxSum2(arr2, m);
            if (ans1 != ans2) {
                System.out.println(MessageFormat.format("max sum failed, arr {0}, ans1 {1}, ans2 {2}, m {3}",
                        new String[]{Arrays.stream(arr).boxed().map(String::valueOf).collect(Collectors.joining(" ")),
                                String.valueOf(ans1), String.valueOf(ans2), String.valueOf(m)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器 时间复杂度O(N^2)
    public static int maxSum1(int[] arr, int m) {
        if (arr == null || arr.length == 0 || m < 1) {
            return 0;
        }
        int len = arr.length;
        int max = Integer.MIN_VALUE;
        for (int left = 0; left < len; left++) {
            int sum = 0;
            for (int right = left; right < len; right++) {
                if (right - left + 1 > m) {
                    break;
                }
                sum += arr[right];
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    // 时间复杂度O(N)
    public static int maxSum2(int[] arr, int m) {
        if (arr == null || arr.length == 0 || m < 1) {
            return 0;
        }
        int len = arr.length;
        int[] sum = new int[len];
        sum[0] = arr[0];
        for (int i = 1; i < len; i++) {
            sum[i] = sum[i - 1] + arr[i];
        }
        LinkedList<Integer> qmax = new LinkedList<>();
        int i = 0;
        int end = Math.min(len, m);
        for (; i < end; i++) {
            while (!qmax.isEmpty() && sum[qmax.peekLast()] <= sum[i]) {
                qmax.pollLast();
            }
            qmax.add(i);
        }
        int max = sum[qmax.peekFirst()];
        int left = 0;
        for (; i < len; left++, i++) {
            if (qmax.peekFirst() == left) {
                qmax.pollFirst();
            }
            while (!qmax.isEmpty() && sum[qmax.peekLast()] <= sum[i]) {
                qmax.pollLast();
            }
            qmax.add(i);
            max = Math.max(max, sum[qmax.peekFirst()] - sum[left]);
        }
        for (; left < len - 1; left++) {
            if (qmax.peekFirst() == left) {
                qmax.pollFirst();
            }
            max = Math.max(max, sum[qmax.peekFirst()] - sum[left]);
        }
        return max;
    }
}

package com.serendipity.algo13slidingwindow;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author jack
 * @version 1.0
 * @description 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
 *              你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要
 *              消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
 *              给定两个整数数组 gas 和 cost ，如果你可以绕环路行驶一周，则返回出发时加油站的
 *              编号，否则返回 -1 。如果存在解，则保证它是唯一的。
 *
 *              问题变种
 *              返回所有可以跑完的加油站数组
 * @date 2023/03/15/19:55
 */
public class LeetCode134 {

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 200;
        int testTimes = 100000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] gas = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int[] cost = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int ans1 = canCompleteCircuit1(gas, cost);
            int ans2 = canCompleteCircuit2(gas, cost);
            if (ans1 != ans2) {
                System.out.println(MessageFormat.format("canCompleteCircuit failed, ans1 {0}, ans2 {1}",
                        new String[]{String.valueOf(ans1), String.valueOf(ans2)}));
                CommonUtil.printArray(gas);
                CommonUtil.printArray(cost);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 暴力循环 O(n2)
    public static int canCompleteCircuit1(int[] gas, int[] cost) {
        // TODO
        Set<Integer> set = new HashSet<>();
        return -1;
    }

    // 滑动窗口
    // 这个方法的时间复杂度O(N)，额外空间复杂度O(N)
    public static int canCompleteCircuit2(int[] gas, int[] cost) {
        boolean[] good = goodArray(gas, cost);
        for (int i = 0; i < gas.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    public static boolean[] goodArray(int[] gas, int[] cost) {
        int n = gas.length;
        int m = n << 1;
        int[] arr = new int[m];
        for (int i = 0; i < n; i++) {
            arr[i] = gas[i] - cost[i];
            arr[i + n] = gas[i] - cost[i];
        }

        for (int i = 1; i < m; i++) {
            arr[i] += arr[i - 1];
        }

        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            while (!list.isEmpty() && arr[list.peekLast()] >= arr[i]) {
                list.pollLast();
            }
            list.addLast(i);
        }

        boolean[] ans = new boolean[n];
        for (int offset = 0, i = 0, j = n; j < m; offset = arr[i++], j++) {
            if (arr[list.peekFirst()] - offset >= 0) {
                ans[i] = true;
            }
            if (list.peekFirst() == i) {
                list.pollFirst();
            }
            while (!list.isEmpty() && arr[list.peekLast()] >= arr[j]) {
                list.pollLast();
            }
            list.addLast(j);
        }
        return ans;
    }
}

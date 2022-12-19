package com.serendipity.greedyalgorithm;

import java.util.PriorityQueue;

/**
 * @author jack
 * @version 1.0
 * @description 最小代价切割金条
 * @date 2022/12/19/16:48
 */
public class SplitGold {

    private static int leastCost1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0);
    }

    // 暴力尝试
    // arr是待合并的，pre是之前的合并产生的花费
    private static int process(int[] arr, int pre) {
        // 递归终止条件
        if (arr.length == 1) {
            return pre;
        }

        int result = Integer.MAX_VALUE;
        // 双层循环遍历所有合并的结果
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                // 合并i、j，递归拿到合并后的结果
                result = Math.min(result, process(copyAndMerge(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return result;
    }

    // 将i、j合并并复制新的数组返回
    private static int[] copyAndMerge(int[] arr, int i, int j) {
        int[] result = new int[arr.length - 1];
        int resulti = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                result[resulti++] = arr[arri];
            }
        }
        result[resulti] = arr[i] + arr[j];
        return result;
    }

    private static int leastCost2(int[] arr) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            heap.add(arr[i]);
        }
        int sum = 0;
        int cur = 0;
        while (heap.size() > 1) {
            cur = heap.poll() + heap.poll();
            sum += cur;
            heap.add(cur);
        }
        return sum;
    }

    // -------------------------------test-------------------------------
    private static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int count = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < count; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (leastCost1(arr) != leastCost2(arr)) {
                System.out.println("failed");
            }
        }
        System.out.println("success");
    }
}

package com.serendipity.algo8greedy;

import java.util.PriorityQueue;

/**
 * @author jack
 * @version 1.0
 * @description 分割金条，60长度分成10、20、30。一块金条切成两半，是需要花费和长度数值一样的铜板的。
 *              比如长度为20的金条，不管怎么切，都要花费20个铜板。 一群人想整分整块金条，怎么分最省铜板?
 *              例如,给定数组{10,20,30}，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。
 *              如果先把长度60的金条分成10和50，花费60; 再把长度50的金条分成20和30，花费50;一共花费110铜板。
 *              但如果先把长度60的金条分成30和30，花费60;再把长度30金条分成10和20， 花费30;一共花费90铜板。
 *
 *              输入一个数组，返回分割的最小代价。暂不考虑切割的顺序，如果考虑切割顺序需要使用动态规划和四边形不等式。
 *
 *              暴力尝试，双层循环将i和j合并，递归得到花费，最后将最小的花费返回。
 *              将所有分割后的长度放入小根堆，每次获取两个长度相加然后放回小根堆，重复直到小根堆只剩一个元素。
 *              每次都切割最大长度的反例97、98  和  100、99。正确操作是第一次切割成100+99和98+97，而不是切割成100和99+98+97。
 *              反例的证明就是哈夫曼编码。
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

package com.serendipity.algo4sort;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author jack
 * @version 1.0
 * @description 已知一个几乎有序的数组。几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离一定不超过k，并且
 *              k相对于数组长度来说是比较小的。请选择一个合适的排序策略，对这个数组进行排序。
 * @date 2023/04/10/16:39
 */
public class SortArrayDistanceLessK {

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 100;
        int testTimes = 500000;
        boolean succeed = true;
        for (int i = 0; i < testTimes; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = randomArrayNoMoveMoreK(maxSize, maxValue, k);
            int[] arr1 = new int[arr.length];
            System.arraycopy(arr, 0, arr1, 0, arr.length);
            int[] arr2 = new int[arr.length];
            System.arraycopy(arr, 0, arr2, 0, arr.length);

            sortedArrDistanceLessK(arr1, k);
            Arrays.sort(arr2);
            if (!CommonUtil.isEqual(arr1, arr2)) {
                System.out.println("sortedArrDistanceLessK failed, k " + k);
                System.out.println("original");
                CommonUtil.printArray(arr);
                System.out.println("sortedArrDistanceLessK result");
                CommonUtil.printArray(arr1);
                System.out.println("Arrays.sort result");
                CommonUtil.printArray(arr2);
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    public static void sortedArrDistanceLessK(int[] arr, int k) {
        if (arr == null || arr.length < 2) {
            return;
        }
        PriorityQueue<Integer> priorityQueue = new PriorityQueue();
        int index = 0;
        for (; index < Math.min(arr.length, k); index++) {
            priorityQueue.add(arr[index]);
        }
        int i = 0;
        for (; index < arr.length; i++, index++) {
            priorityQueue.add(arr[index]);
            arr[i] = priorityQueue.poll();
        }
        while (!priorityQueue.isEmpty()) {
            arr[i++] = priorityQueue.poll();
        }
    }

    // 生成几乎有序的数组，逆序长度不超过k
    public static int[] randomArrayNoMoveMoreK(int maxSize, int maxValue, int k) {
        int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue);
        Arrays.sort(arr);
        boolean[] isSwap = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int j = Math.min(i + (int) (Math.random() * (k + 1)), arr.length - 1);
            if (!isSwap[i] && !isSwap[j]) {
                isSwap[i] = true;
                isSwap[j] = true;
                CommonUtil.swap(arr, i, j);
            }
        }
        return arr;
    }
}

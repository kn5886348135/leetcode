package com.serendipity.slidingwindow;

import java.util.LinkedList;

/**
 * @author jack
 * @version 1.0
 * @description 假设一个固定大小为W的窗口，依次划过arr，
 *              返回每一次滑出状况的最大值
 *              例如，arr = [4,3,5,4,3,3,6,7], W = 3
 *              返回：[5,5,5,4,6,7]
 * @date 2023/03/15/15:46
 */
public class SlidingWindowMaxArray {

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 50;
        int width = 3;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            while (len < width) {
                len = (int) (Math.random() * maxLen);
            }
            int[] arr = generateRandomArr(len, maxValue);
            int[] ans1 = slidingWindow1(arr, width);
            int[] ans2 = slidingWindow2(arr, width);
            for (int j = 0; j < ans1.length; j++) {
                if (ans1[j] != ans2[j]) {
                    System.out.println("Oops!");
                    for (int num : arr) {
                        System.out.print(num + " ");
                    }
                    System.out.println("arr iterator");
                    for (int num : ans1) {
                        System.out.print(num + " ");
                    }
                    System.out.println("ans1 iterator");
                    for (int num : ans2) {
                        System.out.print(num + " ");
                    }
                    System.out.println("ans2 iterator");
                }
            }
        }
    }

    // 暴力循环，对数器
    public static int[] slidingWindow1(int[] arr, int width) {
        if (arr == null || arr.length < width || width < 1) {
            return null;
        }
        int len = arr.length;
        int[] ans = new int[len - width + 1];
        int index = 0;
        int left = 0;
        int right = width - 1;
        while (right < len) {
            int max = arr[left];
            for (int i = left + 1; i <= right; i++) {
                max = Math.max(max, arr[i]);
            }
            ans[index++] = max;
            left++;
            right++;
        }
        return ans;
    }

    // 窗口滑动的时候，将新元素按照从大到小的顺序放到双端队列中，队列的元素不能超过width个
    // 每次滑动后，双端队列的第一个值就是窗口内最大值
    public static int[] slidingWindow2(int[] arr, int width) {
        if (arr == null || arr.length < width || width < 1) {
            return null;
        }

        // 双端队列保存数组下标
        LinkedList<Integer> list = new LinkedList<>();

        int len = arr.length;
        int[] ans = new int[len - width + 1];

        // ans数组的下标
        int index = 0;
        for (int right = 0; right < len; right++) {
            // 比arr[right]小的元素出队列
            // 每一个元素arr[index]都必须入队，比arr[index]小的元素一定不会是窗口的最大值
            // 比arr[index]大的元素可能是新窗口的最大值，也可能不会出现在新窗口中
            while (!list.isEmpty() && arr[list.peekLast()] <= arr[right]) {
                list.pollLast();
            }
            // 每个元素都入队
            list.addLast(right);
            // 控制窗口大小，避免超过width
            if (list.peekFirst() == right - width) {
                list.pollFirst();
            }
            // 形成窗口
            if (right >= width - 1) {
                ans[index++] = arr[list.peekFirst()];
            }
        }
        return ans;
    }

    public static int[] generateRandomArr(int length, int value) {
        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    public static void printArr(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

}

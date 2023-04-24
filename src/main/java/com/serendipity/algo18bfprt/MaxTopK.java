package com.serendipity.algo18bfprt;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个无序数组arr中，长度为N，给定一个正数k，返回top k个最大的数
 *
 *              不同时间复杂度三个方法：
 *
 *              1）O(N*logN)
 *              2）O(N + K*logN)
 *              3）O(n + k*logk)
 * @date 2023/03/18/15:36
 */
public class MaxTopK {

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 100;
        int testTimes = 500000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, true);

            int[] arr1 = new int[arr.length];
            System.arraycopy(arr, 0, arr1, 0, arr.length);
            int[] arr2 = new int[arr.length];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            int[] arr3 = new int[arr.length];
            System.arraycopy(arr, 0, arr3, 0, arr.length);

            int[] ans1 = maxTopK1(arr1, k);
            int[] ans2 = maxTopK2(arr2, k);
            int[] ans3 = maxTopK3(arr3, k);
            if (!CommonUtil.isEqual(ans1, ans2) || !CommonUtil.isEqual(ans1, ans3)) {
                System.out.println("maxTopK failed");
                CommonUtil.printArray(ans1);
                CommonUtil.printArray(ans2);
                CommonUtil.printArray(ans3);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器
    // 排序、收集 O(N * logN) 时间复杂度
    // k >= 1
    public static int[] maxTopK1(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        int len = arr.length;
        k = Math.min(len, k);
        Arrays.sort(arr);
        int[] ans = new int[k];
        for (int i = len - 1, j = 0; j < k; i--, j++) {
            ans[j] = arr[i];
        }
        return ans;
    }

    // 大根堆 O(N + K * logN)
    public static int[] maxTopK2(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        int len = arr.length;
        k = Math.min(len, k);

        // 从底向上建堆，时间复杂度O(N)
        // 整个数组最大值放到堆顶
        for (int i = len - 1; i >= 0; i--) {
            heapify(arr, i, len);
        }

        // 只把前K个数放在arr末尾，然后收集，O(K * logN)
        int heapSize = len;
        CommonUtil.swap(arr, 0, --heapSize);
        int count = 1;
        while (heapSize > 0 && count < k) {
            heapify(arr, 0, heapSize);
            CommonUtil.swap(arr, 0, --heapSize);
            count++;
        }
        int[] ans = new int[k];
        for (int i = len - 1, j = 0; j < k; i--, j++) {
            ans[j] = arr[i];
        }
        return ans;
    }

    public static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[largest] > arr[index] ? largest : index;
            if (largest == index) {
                break;
            }
            CommonUtil.swap(arr, largest, index);
            index = largest;
            left = index * 2 + 1;
        }
    }

    // 时间复杂度 O(N + K * logK)
    public static int[] maxTopK3(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        int len = arr.length;
        k = Math.min(len, k);

        // O(N)
        int num = minKth(arr, len - k);
        int[] ans = new int[k];
        int index = 0;
        for (int i = 0; i < len; i++) {
            if (arr[i] > num) {
                ans[index++] = arr[i];
            }
        }
        // 等于部分
        for (; index < k; index++) {
            ans[index] = num;
        }
        // O(K * logK)
        Arrays.sort(ans);
        for (int left = 0, right = k - 1; left < right; left++, right--) {
            CommonUtil.swap(ans, left, right);
        }
        return ans;
    }

    // 时间复杂度O(N)
    public static int minKth(int[] arr, int index) {
        int left = 0;
        int right = arr.length - 1;
        int pivot = 0;
        int[] range = null;

        while (left < right) {
            pivot = arr[left + (int) (Math.random() * (right - left + 1))];
            range = partition(arr, left, right, pivot);
            if (index < range[0]) {
                right = range[0] - 1;
            } else if (index > range[1]) {
                left = range[1] + 1;
            } else {
                return pivot;
            }
        }
        return arr[left];
    }

    public static int[] partition(int[] arr, int left, int right, int pivot) {
        int less = left - 1;
        int more = right + 1;
        int cur = left;
        while (cur < more) {
            if (arr[cur] < pivot) {
                CommonUtil.swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) {
                CommonUtil.swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }
}

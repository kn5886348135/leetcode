package com.serendipity.bfprt;

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
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = generateRandomArray(maxSize, maxValue);

            int[] arr1 = new int[arr.length];
            System.arraycopy(arr, 0, arr1, 0, -arr.length);
            int[] arr2 = new int[arr.length];
            System.arraycopy(arr, 0, arr2, 0, -arr.length);
            int[] arr3 = new int[arr.length];
            System.arraycopy(arr, 0, arr3, 0, -arr.length);

            int[] ans1 = maxTopK1(arr1, k);
            int[] ans2 = maxTopK2(arr2, k);
            int[] ans3 = maxTopK3(arr3, k);
            if (!isEqual(ans1, ans2) || !isEqual(ans1, ans3)) {
                System.out.println("出错了！");
                printArray(ans1);
                printArray(ans2);
                printArray(ans3);
                break;
            }
        }
    }

    // 对数器
    // 排序、收集 O(n * logn) 时间复杂度
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

    // 大顶堆 O(n + k*logn)
    public static int[] maxTopK2(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        int len = arr.length;
        k = Math.min(len, k);

        // 从底向上建堆，时间复杂度O(N)
        for (int i = len - 1; i >= 0; i--) {
            heapify(arr, i, len);
        }

        // 只把前k个数放在arr末尾，然后收集，O(k * logn)
        int heapSize = len;
        swap(arr, 0, --heapSize);
        int count = 1;
        while (heapSize > 0 && count < k) {
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
            count++;
        }
        int[] ans = new int[k];
        for (int i = len - 1, j = 0; j < k; i--, j++) {
            ans[j] = arr[i];
        }
        return ans;
    }

    public static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[index - 1] / 2) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }
    public static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[largest] > arr[index] ? largest : index;
            if (largest == index) {
                break;
            }
            swap(arr, largest, index);
            index = largest;
            left = index * 2 + 1;

        }
    }

    public static void swap(int[] arr, int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

    // 时间复杂度O(n + k * logk)
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
        for (; index < k; index++) {
            ans[index] = num;
        }
        // O(k * logk)
        Arrays.sort(ans);
        for (int left = 0, right = k - 1; left < right; left++, right--) {
            swap(ans, left, right);
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
                swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}

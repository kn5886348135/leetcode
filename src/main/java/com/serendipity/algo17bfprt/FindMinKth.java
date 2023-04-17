package com.serendipity.algo17bfprt;

import java.util.PriorityQueue;

/**
 * @author jack
 * @version 1.0
 * @description 无序数组中找到第K小的数
 * @date 2023/03/18/12:56
 */
public class FindMinKth {

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
            }
        }
    }

    // 大顶堆 O(n * logk)
    public static int minKth1(int[] arr, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int i = 0; i < k; i++) {
            maxHeap.add(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            if (arr[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();
    }


    // 改写快排，利用荷兰国旗问题 O(n)时间复杂度
    // 最坏时间复杂度O(n2)，最好时间复杂度O(n)，时间复杂度的证明需要计算概率期望，很复杂
    // k >= 1
    public static int minKth2(int[] array, int k) {
        int[] arr = new int[array.length];
        System.arraycopy(array, 0, arr, 0, arr.length);

        return process2(arr, 0, arr.length - 1, k - 1);
    }

    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // arr[left...right]范围上做荷兰国旗划分，找到位于index的数
    public static int process2(int[] arr, int left, int right, int index) {
        if (left == right) {
            // L = =R ==INDEX
            return arr[left];
        }
        // 不止一个数  L +  [0, R -L]
        int pivot = arr[left + (int) (Math.random() * (right - left + 1))];
        int[] range = partition(arr, left, right, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return process2(arr, left, range[0] - 1, index);
        } else {
            return process2(arr, range[1] + 1, right, index);
        }
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

    public static void swap(int[] arr, int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

    // bfprt算法 时间复杂度O(n)
    public static int minKth3(int[] array, int k) {
        int[] arr = new int[array.length];
        System.arraycopy(array, 0, arr, 0, arr.length);

        return bfprt(arr, 0, arr.length - 1, k - 1);
    }

    // arr[left...right] 如果排序的话，返回位于index的数
    public static int bfprt(int[] arr, int left, int right, int index) {
        if (left == right) {
            return arr[left];
        }

        // left...right 每5个数一组
        // 5个数内部排序
        // 5个数中的中位数组成新数组
        // 返回新数组的中位数
        // 保证每次都可以淘汰一部分(3n/10)数据
        int pivot = medianOfMedians(arr, left, right);
        int[] range = partition(arr, left, right, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return bfprt(arr, left, range[0] - 1, index);
        } else {
            return bfprt(arr, range[1] + 1, right, index);
        }
    }

    // arr[left...right] 5个数一组
    // 5个数内部排序
    // 每个小组的中位数组成新的数组matrix
    // 返回matrix的中位数
    public static int medianOfMedians(int[] arr, int left, int right) {
        int size = right - left + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] matrix = new int[size / 5 + offset];
        for (int i = 0; i < matrix.length; i++) {
            int first = left + i * 5;
            // L ... L + 4
            // L +5 ... L +9
            // L +10....L+14
            matrix[i] = getMedian(arr, first, Math.min(right, first + 4));
        }
        // marr中，找到中位数
        // marr(0, marr.len - 1,  mArr.length / 2 )
        return bfprt(matrix, 0, matrix.length - 1, matrix.length / 2);
    }

    public static int getMedian(int[] arr, int left, int right) {
        insertionSort(arr, left, right);
        return arr[(left + right) / 2];
    }

    // 插入排序
    public static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            for (int j = i - 1; j >= left && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

}

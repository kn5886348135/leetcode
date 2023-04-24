package com.serendipity.algo18bfprt;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.PriorityQueue;

/**
 * @author jack
 * @version 1.0
 * @description 无序数组中找到第K小的数
 * @date 2023/03/18/12:56
 */
public class FindMinKth {

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 100;
        int testTimes = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            int ans4 = minKth4(arr, k);
            if (ans1 != ans2 || ans2 != ans3 || ans1 != ans4) {
                System.out.println(MessageFormat.format("minKth failed, k {0}, ans1 {1}, ans2 {2}, ans3 {3}, ans4 {4}",
                        new String[]{String.valueOf(k), String.valueOf(ans1), String.valueOf(ans2),
                                String.valueOf(ans3), String.valueOf(ans4)}));
                CommonUtil.printArray(arr);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 大根堆 O(n * logk)
    // 堆的大小控制为k，遍历完整个数组，堆顶元素就是答案
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
    // 递归的时候只走一个分支，不像快排两边分支都要执行
    // k >= 1
    public static int minKth2(int[] array, int k) {
        int[] arr = new int[array.length];
        System.arraycopy(array, 0, arr, 0, arr.length);

        return process2(arr, 0, arr.length - 1, k - 1);
    }

    // arr[left...right]范围上做荷兰国旗划分，找到位于index的数
    public static int process2(int[] arr, int left, int right, int index) {
        // left == right == index
        if (left == right) {
            return arr[left];
        }
        // 随机选择pivot，left +  [0, right - left]
        int pivot = arr[left + (int) (Math.random() * (right - left + 1))];
        int[] range = partition(arr, left, right, pivot);
        // index在等于区域内
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            // index在小于区域内
            return process2(arr, left, range[0] - 1, index);
        } else {
            // index在大于区域内
            return process2(arr, range[1] + 1, right, index);
        }
    }

    // 按照小于、等于、大于区域交换
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

    // bfprt算法 时间复杂度O(N)
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
            // left ... left + 4
            // left + 5 ... left + 9
            // left + 10....left + 14
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
                CommonUtil.swap(arr, j, j + 1);
            }
        }
    }

    // minKth2改成迭代版本
    public static int minKth4(int[] array, int index) {
        int[] arr = new int[array.length];
        System.arraycopy(array, 0, arr, 0, arr.length);
        int left = 0;
        int right = arr.length - 1;
        int pivot = 0;
        int[] range = null;
        while (left < right) {
            pivot = arr[left + (int) (Math.random() * (right - left + 1))];
            range = partition(arr, left, right, pivot);
            // index在等于区域内
            if (index < range[0]) {
                right = range[0] - 1;
            } else if (index > range[1]) {
                // index在小于区域内
                left = range[1] + 1;
            } else {
                return pivot;
            }
        }
        return arr[left];
    }
}

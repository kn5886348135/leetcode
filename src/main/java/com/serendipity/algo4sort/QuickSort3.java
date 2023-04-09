package com.serendipity.algo4sort;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 随机快排
 * @date 2023/04/09/1:33
 */
public class QuickSort3 {

    public static void main(String[] args) {
        int maxSize = 30;
        int maxValue = 200;
        int testTimes = 50000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue);
            int[] arr1 = new int[arr.length];
            System.arraycopy(arr, 0, arr1, 0, arr.length);
            int[] arr2 = new int[arr.length];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            int[] arr3 = new int[arr.length];
            System.arraycopy(arr, 0, arr3, 0, arr.length);
            int[] arr4 = new int[arr.length];
            System.arraycopy(arr, 0, arr4, 0, arr.length);
            int[] arr5 = new int[arr.length];
            System.arraycopy(arr, 0, arr5, 0, arr.length);
            Arrays.sort(arr);
            quickSort1(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            quickSort4(arr4);
            Arrays.sort(arr5);
            if (!CommonUtil.isEqual(arr, arr1) || !CommonUtil.isEqual(arr, arr2) || !CommonUtil.isEqual(arr, arr2)) {
                System.out.println("quickSort failed");
                System.out.println("quickSort1 result");
                CommonUtil.printArray(arr1);
                System.out.println("quickSort2 result");
                CommonUtil.printArray(arr2);
                System.out.println("quickSort3 result");
                CommonUtil.printArray(arr3);
                System.out.println("quickSort4 result");
                CommonUtil.printArray(arr4);
                System.out.println("quickSort5 result");
                CommonUtil.printArray(arr5);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "false");
    }

    // 快速排序1.0
    // 在arr[left..right]范围上，进行快速排序的过程：
    // 1、用arr[right]对该范围做partition，<= arr[right]的数在左部分并且保证arr[right]最后来到
    //    左部分的最后一个位置，记为middle， > arr[right]的数在右部分（arr[middle+1..right]）
    // 2、对arr[left..middle-1]进行快速排序(递归)
    // 3、对arr[middle+1..right]进行快速排序(递归)
    // 因为每一次partition都会搞定一个数的位置且不会再变动，所以排序能完成
    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process1(arr, 0, arr.length - 1);
    }

    // 在arr[left..right]范围快速排序
    private static void process1(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = partition(arr, left, right);
        process1(arr, left, middle - 1);
        process1(arr, middle + 1, right);
    }

    // 用arr[right]对该范围做partition，<= arr[right]的数在左部分并且保证arr[right]最后来到
    // 左部分的最后一个位置，记为middle， > arr[right]的数在右部分（arr[middle+1...right]）
    public static int partition(int[] arr, int left, int right) {
        if (left > right) {
            return -1;
        }
        if (left == right) {
            return left;
        }
        // 最后一个 <= arr[right]的位置
        int pre = left - 1;
        // left...right的任意一个位置
        int index = left;
        while (index < right) {
            // 交换++pre和index
            if (arr[index] <= arr[right]) {
                CommonUtil.swap(arr, index, ++pre);
            }
            index++;
        }
        // arr[right]交换到pre后一个位置
        CommonUtil.swap(arr, ++pre, right);
        return pre;
    }

    // 快速排序2.0
    // 在arr[left...right]范围进行快速排序
    // 1）用arr[right]对该范围做partition，< arr[right]的数在左部分，== arr[right]的数中间，>arr[right]的数在右部分。
    //    假设== arr[right]的数所在范围是[a,b]
    // 2）对arr[left...a-1]进行快速排序(递归)
    // 3）对arr[b+1...right]进行快速排序(递归)
    // 因为每一次partition都会搞定一批数的位置且不会再变动，所以排序能完成
    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process2(arr, 0, arr.length - 1);
    }

    // 在arr[left..right]范围快速排序
    private static void process2(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int[] scope = netherlandsFlag(arr, left, right);
        process2(arr, left, scope[0] - 1);
        process2(arr, scope[1] + 1, right);
    }

    // 用arr[right]对该范围做partition，< arr[right]的数在左部分，== arr[right]的数中间，>arr[right]的数在右部分。
    // 假设== arr[right]的数所在范围是[a,b]
    public static int[] netherlandsFlag(int[] arr, int left, int right) {
        if (left > right) {
            return new int[]{-1, -1};
        }

        if (left == right) {
            return new int[]{left, right};
        }
        // = arr[right]的位置
        int scopeLeft = left - 1;
        int scopeRight = right;
        // left...right的任意一个位置
        int index = left;
        while (index < scopeRight) {
            // index右移
            if (arr[index] == arr[right]) {
                index++;
            } else if (arr[index] < arr[right]) {
                // < arr[right] 交换到左边
                // scopeLeft...index-1都是=arr[right]
                CommonUtil.swap(arr, index++, ++scopeLeft);
            } else {
                // > arr[right]交换到右边
                // arr[--scopeRight]的大小不确定，所以index不动
                CommonUtil.swap(arr, index, --scopeRight);
            }
        }
        CommonUtil.swap(arr, scopeRight, right);
        return new int[]{scopeLeft + 1, scopeRight};
    }

    // 快速排序3.0
    // 随机快排+荷兰国旗技巧优化
    // 在arr[left...right]范围上，进行快速排序的过程：
    //1）在这个范围上，随机选一个数记为num，
    //1）用num对该范围做partition，< num的数在左部分，== num的数中间，>num的数在右部分。假设== num的数所在范围是[a,b]
    //2）对arr[left..a-1]进行快速排序(递归)
    //3）对arr[b+1..right]进行快速排序(递归)
    //因为每一次partition都会搞定一批数的位置且不会再变动，所以排序能完成
    public static void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process3(arr, 0, arr.length - 1);
    }

    private static void process3(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        // 将随机位置交换到right，变成荷兰国旗问题
        CommonUtil.swap(arr, left + (int) Math.random() * (right - left + 1), right);
        int[] scope = netherlandsFlag(arr, left, right);
        process3(arr, left, scope[0] - 1);
        process3(arr, scope[1] + 1, right);
    }

    public static void quickSort4(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
    }

    public static void quickSort5(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
    }

}

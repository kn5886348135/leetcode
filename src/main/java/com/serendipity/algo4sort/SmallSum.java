package com.serendipity.algo4sort;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description 在一个数组中，一个数左边比它小的数的总和，叫数的小和，所有数的小和累加起来，叫数组小和。求数组小和。
 *              例子： [1,3,4,2,5]
 *              1左边比1小的数：没有
 *              3左边比3小的数：1
 *              4左边比4小的数：1、3
 *              2左边比2小的数：1
 *              5左边比5小的数：1、3、4、 2
 *              所以数组的小和为1+1+3+1+1+3+4+2=16
 * @date 2023/03/30/15:14
 */
public class SmallSum {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = CommonUtil.generateRandomArray(maxSize, maxValue, false);
            int[] arr2 = new int[arr1.length];
            System.arraycopy(arr1, 0, arr2, 0, arr1.length);
            if (smallSum(arr1) != verifySmallSum(arr2)) {
                succeed = false;
                CommonUtil.printArray(arr1);
                CommonUtil.printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    // 小和问题等价于每一个数p，右边大于它的个数n * p的累加和
    public static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }
        int middle = left + ((right - left) >> 1);
        return process(arr, left, middle) + process(arr, middle + 1, right) + merge(arr, left, middle, right);
    }

    // left...middle有序
    // middle+1...right有序
    // 不重复不遗漏
    public static int merge(int[] arr, int left, int middle, int right) {
        int[] help = new int[right - left + 1];
        int index = 0;
        int p = left;
        int q = middle + 1;
        int ans = 0;
        // 左侧的每一个数都和右侧的每一个数比较
        while (p <= middle && q <= right) {
            ans += arr[p] < arr[q] ? (right - q + 1) * arr[p] : 0;
            help[index++] = arr[p] < arr[q] ? arr[p++] : arr[q++];
        }
        // 越界的部分只需要复制，进行排序
        while (p <= middle) {
            help[index++] = arr[p++];
        }
        while (q <= right) {
            help[index++] = arr[q++];
        }
        System.arraycopy(help, 0, arr, left, right - left + 1);
        return ans;
    }

    // 对数器
    public static int verifySmallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                res += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return res;
    }
}

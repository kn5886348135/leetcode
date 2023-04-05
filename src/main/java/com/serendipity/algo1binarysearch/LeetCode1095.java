package com.serendipity.algo1binarysearch;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 给你一个 山脉数组 mountainArr，请你返回能够使得 mountainArr.get(index) 等于 target 最小 的下标 index 值。
 *              如果不存在这样的下标 index，就请返回 -1。
 *              何为山脉数组？如果数组 A 是一个山脉数组的话，那它满足如下条件：
 *              首先，A.length >= 3
 *              其次，在 0 < i < A.length - 1 条件下，存在 i 使得：
 *              A[0] < A[1] < ... A[i-1] < A[i]
 *              A[i] > A[i+1] > ... > A[A.length - 1]
 *              你将 不能直接访问该山脉数组，必须通过 MountainArray 接口来获取数据：
 *              MountainArray.get(k) - 会返回数组中索引为k 的元素（下标从 0 开始）
 *              MountainArray.length() - 会返回该数组的长度
 * @date 2023/04/04/23:59
 */
public class LeetCode1095 {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;

        for (int i = 0; i < testTime; i++) {
            MountainArray mountainArr = new MountainArrayImpl(maxSize, maxValue);
            int target = (int) Math.random() * (maxValue + maxValue);
            if (verifyFindInMountainArray(target, mountainArr) != findInMountainArray(target, mountainArr)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    public static int findInMountainArray(int target, MountainArray mountainArr) {
        if (mountainArr.length() < 3) {
            return -1;
        }
        int index = peakIndexInMountainArray(mountainArr);
        int left = 0;
        int right = index;
        int middle;
        int ans = -1;
        // 数组上升段
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            int cur = mountainArr.get(middle);
            if (cur > target) {
                right = middle - 1;
            } else if (cur < target) {
                left = middle + 1;
            } else {
                ans = middle;
                break;
            }
        }

        // 数组下降段
        left = index;
        right = mountainArr.length() - 1;
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            int cur = mountainArr.get(middle);
            if (cur > target) {
                left = middle + 1;
            } else if (cur < target) {
                right = middle - 1;
            } else {
                ans = ans == -1 ? middle : Math.min(ans, middle);
                break;
            }
        }
        return ans;
    }

    public static int peakIndexInMountainArray(MountainArray mountainArr) {
        // 边界条件
        if (mountainArr == null || mountainArr.length() < 3) {
            return -1;
        }
        // 避免溢出
        int left = 1;
        int right = mountainArr.length() - 2;
        int middle;
        // 开始二分
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            int cur = mountainArr.get(middle);
            if (cur > mountainArr.get(middle - 1) && cur > mountainArr.get(middle + 1)) {
                return middle;
            } else if (cur > mountainArr.get(middle - 1)) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return -1;
    }

    public static int verifyFindInMountainArray(int target, MountainArray mountainArr) {
        int len = mountainArr.length();
        for (int i = 0; i < len; i++) {
            if (mountainArr.get(i) == target) {
                return i;
            }
        }
        return -1;
    }

    interface MountainArray {
        public int get(int index);
        public int length();
    }

    static class MountainArrayImpl implements MountainArray {

        private int[] arr;

        public MountainArrayImpl(int maxSize, int maxValue) {
            this.arr = generateMountainArray(maxSize, maxValue);
        }

        @Override
        public int get(int index) {
            return arr[index];
        }

        @Override
        public int length() {
            return arr.length;
        }

        private int[] generateMountainArray(int maxSize, int maxValue) {
            int[] arr1 = CommonUtil.generateRandomDiffArray(maxSize, maxValue);
            int[] arr2 = CommonUtil.generateRandomDiffArray(maxSize, maxValue);
            Arrays.sort(arr1);
            Arrays.sort(arr2);
            // 避免拼接后相邻位置相同
            if (arr1[arr1.length - 1] == arr2[arr2.length - 1]) {
                arr1[arr1.length - 1] = arr1[arr1.length - 1] + 1;
            }

            int[] ans = new int[arr1.length + arr2.length];
            System.arraycopy(arr1, 0, ans, 0, arr1.length);
            for (int i = arr1.length; i < ans.length; i++) {
                ans[i] = arr2[ans.length - i - 1];
            }
            return ans;
        }
    }
}

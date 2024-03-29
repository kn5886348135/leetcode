package com.serendipity.skills.subseqlen;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.TreeMap;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个数组arr，给定一个值v
 *              求子数组平均值小于等于v的最长子数组长度
 * @date 2023/03/25/20:44
 */
public class AvgLessEqualValueLongestSubarray {

    public static void main(String[] args) {
        int maxSize = 20;
        int maxValue = 100;
        int testTime = 500000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int value = (int) (Math.random() * maxValue);
            int[] arr1 = new int[arr.length];
            System.arraycopy(arr, 0, arr1, 0, arr.length);
            int[] arr2 = new int[arr.length];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            int[] arr3 = new int[arr.length];
            System.arraycopy(arr, 0, arr3, 0, arr.length);
            int ans1 = ways1(arr1, value);
            int ans2 = ways2(arr2, value);
            int ans3 = ways3(arr3, value);
            if (ans1 != ans2 || ans1 != ans3) {
                CommonUtil.printArray(arr);
                System.out.println(
                        MessageFormat.format("longest sub sequence failed, avg value {0}, ans1 {1}, ans2 {2}, ans3 {3}",
                                new String[]{String.valueOf(value), String.valueOf(ans1), String.valueOf(ans2), String.valueOf(ans3)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器 时间复杂度O(N^3)
    public static int ways1(int[] arr, int v) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int sum = 0;
                int len = j - i + 1;
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                }
                double avg = (double) sum / (double) len;
                if (avg <= v) {
                    ans = Math.max(ans, len);
                }
            }
        }
        return ans;
    }

    // 时间复杂度O(N*logN)
    public static int ways2(int[] arr, int v) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        TreeMap<Integer, Integer> origins = new TreeMap<>();
        int ans = 0;
        int modify = 0;
        for (int i = 0; i < arr.length; i++) {
            int p1 = arr[i] <= v ? 1 : 0;
            int p2 = 0;
            int query = -arr[i] - modify;
            if (origins.floorKey(query) != null) {
                p2 = i - origins.get(origins.floorKey(query)) + 1;
            }
            ans = Math.max(ans, Math.max(p1, p2));
            int curOrigin = -modify - v;
            if (origins.floorKey(curOrigin) == null) {
                origins.put(curOrigin, i);
            }
            modify += arr[i] - v;
        }
        return ans;
    }

    // 时间复杂度O(N)
    // arr中每个数减去v，问题变成子数组累加和小于等于0的问题
    public static int ways3(int[] arr, int v) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] -= v;
        }
        return maxLengthAwesome(arr, 0);
    }

    // 找到数组中累加和<=k的最长子数组
    public static int maxLengthAwesome(int[] arr, int k) {
        int len = arr.length;
        int[] sums = new int[len];
        int[] ends = new int[len];
        sums[len - 1] = arr[len - 1];
        ends[len - 1] = len - 1;
        for (int i = len - 2; i >= 0; i--) {
            if (sums[i + 1] < 0) {
                sums[i] = arr[i] + sums[i + 1];
                ends[i] = ends[i + 1];
            } else {
                sums[i] = arr[i];
                ends[i] = i;
            }
        }
        int end = 0;
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < len; i++) {
            while (end < len && sum + sums[end] <= k) {
                sum += sums[end];
                end = ends[end] + 1;
            }
            ans = Math.max(ans, end - i);
            if (end > i) {
                sum -= arr[i];
            } else {
                end = i + 1;
            }
        }
        return ans;
    }
}

package com.serendipity.leetcode.middle;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jack
 * @version 1.0
 * @description 找到 K 个最接近的元素
 *              给定一个 排序好 的数组 arr ，两个整数 k 和 x ，从数组中找到最靠近 x（两数之差最小）的 k 个数。返回的结果必须要是按升序排好的。
 *
 *              整数 a 比整数 b 更接近 x 需要满足：
 *
 *              |a - x| < |b - x| 或者
 *              |a - x| == |b - x| 且 a < b
 * @date 2023/06/28/18:04
 */
public class LeetCode658 {

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 500;
        int maxK = 50;
        int maxX = 700;
        int testTimes = 2000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxLen, maxValue, true);
            Arrays.sort(arr);
            int k = (int) (Math.random() * maxK) + 5;
            while (k >= maxLen || k > arr.length) {
                k = (int) (Math.random() * maxK) + 5;
            }
            int x = (int) (Math.random() * maxX);
            if (Math.random() > 0.5) {
                x *= -1;
            }
            List<Integer> list1 = findClosestElements1(arr, k, x);
            List<Integer> list2 = findClosestElements2(arr, k, x);
            List<Integer> list3 = findClosestElements3(arr, k, x);
            Collections.sort(list1);
            Collections.sort(list2);
            Collections.sort(list3);

            if (!list1.equals(list2) || !list1.equals(list3)) {
                CommonUtil.printArray(arr);
                System.out.println(MessageFormat.format(
                        "findClosestElements failed, \r\n ans1 {0}, \r\n ans2 {1}, \r\n ans3 {2}",
                        new String[]{list1.stream().map(String::valueOf).collect(Collectors.joining("\t")),
                                list2.stream().map(String::valueOf).collect(Collectors.joining("\t")),
                                list3.stream().map(String::valueOf).collect(Collectors.joining("\t"))}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 用比较器排序
    public static List<Integer> findClosestElements1(int[] arr, int k, int x) {
        if (arr == null || arr.length == 0) {
            return new ArrayList<>();
        }
        if (k < 0 || k > arr.length) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();
        for (int item : arr) {
            list.add(item);
        }
        Collections.sort(list, (o1, o2) -> {
            if (Math.abs(o1 - x) != Math.abs(o2 - x)) {
                return Math.abs(o1 - x) - Math.abs(o2 - x);
            } else {
                return o1 - o2;
            }
        });
        List<Integer> ans = list.subList(0, k);
        Collections.sort(ans);
        return ans;
    }

    // 二分查找 双指针
    public static List<Integer> findClosestElements2(int[] arr, int k, int x) {
        if (arr == null || arr.length == 0) {
            return new ArrayList<>();
        }
        if (k < 0 || k > arr.length) {
            return new ArrayList<>();
        }
        int right = binarySearch(arr, x);

        int left = right - 1;
        while (k-- > 0) {
            if (left < 0) {
                right++;
            } else if (right >= arr.length) {
                left--;
            } else if (x - arr[left] <= arr[right] - x) {
                left--;
            } else {
                right++;
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = left + 1; i < right; i++) {
            ans.add(arr[i]);
        }
        return ans;
    }

    // 二分查找得到大于x最左的位置
    private static int binarySearch(int[] arr, int x) {
        int low = 0;
        int high = arr.length - 1;
        while (low < high) {
            int mid = low + ((high - low) >> 1);
            if (arr[mid] >= x) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    // 纯二分
    // arr[mid...mid+k]区间的两个端点，将离x较远的端点向x移动
    public static List<Integer> findClosestElements3(int[] arr, int k, int x) {
        if (arr == null || arr.length == 0) {
            return new ArrayList<>();
        }
        if (k < 0 || k > arr.length) {
            return new ArrayList<>();
        }

        int low = 0;
        int high = arr.length - k;
        while (low < high) {
            int mid = low + ((high - low) >> 1);
            //当左端点严格大于右端点才右移
            if (x - arr[mid] > arr[mid + k] - x) {
                //已经出现比arr[m]差值小的数所以不用考虑了
                low = mid + 1;
            } else {
                //有可能相等所以不能马上排除
                high = mid;
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = low; i < low + k; i++) {
            ans.add(arr[i]);
        }
        return ans;
    }
}
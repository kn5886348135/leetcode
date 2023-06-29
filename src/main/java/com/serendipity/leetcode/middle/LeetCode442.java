package com.serendipity.leetcode.middle;

import com.serendipity.common.CommonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author jack
 * @version 1.0
 * @description 数组中重复的数据
 *              给你一个长度为 n 的整数数组 nums ，其中 nums 的所有整数都在范围 [1, n] 内，且每个整数出现 一次 或 两次 。
 *              请你找出所有出现 两次 的整数，并以数组形式返回。
 *
 *              你必须设计并实现一个时间复杂度为 O(n) 且仅使用常量额外空间的算法解决此问题。
 * @date 2023/06/28/16:34
 */
public class LeetCode442 {

    public static void main(String[] args) {
        int maxLen = 100;
        int m = 50;
        int testTimes = 100000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateNums(maxLen, m);

            int[] nums1 = new int[arr.length];
            int[] nums2 = new int[arr.length];
            int[] nums3 = new int[arr.length];
            System.arraycopy(arr, 0, nums1, 0, arr.length);
            System.arraycopy(arr, 0, nums2, 0, arr.length);
            System.arraycopy(arr, 0, nums3, 0, arr.length);
            List<Integer> list1 = validateFindDuplicates(nums1);
            List<Integer> list2 = findDuplicates1(nums2);
            List<Integer> list3 = findDuplicates2(nums3);
            if (list1.size() != list2.size() || list1.size() != list3.size()) {
                System.out.println("findDuplicates size failed");
                Arrays.stream(arr).forEach(item -> System.out.print(item + "\t"));
                System.out.println();
                list1.forEach(item -> System.out.print(item + "\t"));
                System.out.println();
                list2.forEach(item -> System.out.print(item + "\t"));
                System.out.println();
                list3.forEach(item -> System.out.print(item + "\t"));
                success = false;
                break;
            }
            list1.sort(Comparator.comparingInt(a -> a));
            list2.sort(Comparator.comparingInt(a -> a));
            list3.sort(Comparator.comparingInt(a -> a));
            if (!list1.equals(list2) || !list1.equals(list3)) {
                System.out.println("findDuplicates failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器
    public static List<Integer> validateFindDuplicates(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if ((entry.getValue() & 1) == 0) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    // 原地hash
    // 将所有元素交换到值和下标对应的位置
    public static List<Integer> findDuplicates1(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            while (nums[i] != nums[nums[i] - 1]) {
                CommonUtil.swap(nums, i, nums[i] - 1);
            }
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            if (nums[i] != i + 1) {
                list.add(nums[i]);
            }
        }
        return list;
    }

    // 将nums[nums[i] - 1]变为负数，来到索引为nums[i]-1的位置取绝对值
    // 如nums[nums[j] - 1]为负数，证明已经有num[i]访问过，则nums[j]是重复值
    public static List<Integer> findDuplicates2(int[] nums) {
        int len = nums.length;
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            int x = Math.abs(nums[i]);
            if (nums[x - 1] > 0) {
                nums[x - 1] = -nums[x - 1];
            } else {
                ans.add(x);
            }
        }
        return ans;
    }

    // 生成只有1-n的整数，长度为n的数组，每个整数只出现一次或者两次，出现两次的整数有m个
    private static int[] generateNums(int n, int m) {
        int len = (int) (Math.random() * n) + 1;
        while (len < 10) {
            len = (int) (Math.random() * n) + 1;
        }
        int dupn = (int) (Math.random() * m) + 1;
        while (2 * dupn + 2 >= len) {
            dupn = (int) (Math.random() * m) + 1;
        }

        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            nums.add(i + 1);
        }
        Collections.shuffle(nums);

        List<Integer> list = new ArrayList<>();
        int item;
        while (list.size() < dupn) {
            item = (int) (Math.random() * len) + 1;
            if (!list.contains(item)) {
                list.add(item);
            }
        }

        Iterator<Integer> iter = list.iterator();
        for (int i = 0; i < len; i++) {
            while (iter.hasNext()) {
                Integer next = iter.next();
                if (!next.equals(nums.get(i))) {
                    nums.set(i, next);
                    iter.remove();
                    break;
                }
            }
        }

        int[] ans = new int[nums.size()];
        for (int i = 0; i < nums.size(); i++) {
            ans[i] =nums.get(i);
        }
        return ans;
    }
}
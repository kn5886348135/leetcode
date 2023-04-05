package com.serendipity.algo2bitoperation;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jack
 * @version 1.0
 * @description 给你一个非空整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 *              你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。
 * @date 2023/04/05/22:48
 */
public class LeetCode136 {

    public static void main(String[] args) {
        int maxLen = 15;
        int maxValue = 50;
        int testTimes = 50000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomArr(maxLen, maxValue);
            if (verifySingleNumber(arr) != singleNumber(arr)) {
                System.out.println("failed");
                CommonUtil.printArray(arr);
                break;
            }
        }
        System.out.println("success");
    }

    // 运用异或运算的交换律和结合律，数组所有元素异或的结果就是答案
    // 0 ^ N == N
    // N ^ N == 0
    public static int singleNumber(int[] nums) {
        int ans = 0;
        for (int num : nums) {
            ans ^= num;
        }
        return ans;
    }

    // 对数器
    public static int verifySingleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        int res = Integer.MAX_VALUE;
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int value = entry.getValue();
            if ((value & 1) != 0) {
                res = entry.getKey();
                count++;
            }
        }
        if (count > 1) {
            res = Integer.MAX_VALUE;
        }
        return res;
    }

    // 生成只有一个元素出现奇数次的数组
    private static int[] randomArr(int maxLen, int maxValue) {
        int length = (int) (Math.random() * maxLen);
        while (length < 2) {
            length = (int) (Math.random() * maxLen);
        }
        int[] arr = new int[(length << 1) + 1];
        for (int i = 0; i < length; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        for (int i = length; i < length << 1; i++) {
            arr[i] = arr[i - length];
        }
        int[] arr1 = new int[(length << 1) + 1];
        System.arraycopy(arr1, 0, arr, 0, arr.length);
        Arrays.sort(arr1);
        arr[length << 1] = arr1[(length << 1)] + 10;
        return arr;
    }
}

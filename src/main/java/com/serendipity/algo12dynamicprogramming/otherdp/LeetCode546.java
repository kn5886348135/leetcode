package com.serendipity.algo12dynamicprogramming.otherdp;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author jack
 * @version 1.0
 * @description 给出一些不同颜色的盒子 boxes ，盒子的颜色由不同的正数表示。你将经过若干轮操作去去
 *              掉盒子，直到所有的盒子都去掉为止。每一轮你可以移除具有相同颜色的连续 k 个盒子
 *              （k >= 1），这样一轮之后你将得到 k * k 个积分。
 *              返回 你能获得的最大积分和 。
 * @date 2023/03/28/20:35
 */
public class LeetCode546 {

    public static void main(String[] args) {
        int maxSize = 50;
        int maxValue = 300;
        int testTime = 1000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int[] arr1 = new int[arr.length];
            System.arraycopy(arr, 0, arr1, 0, arr.length);
            int[] arr2 = new int[arr.length];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            int[] arr3 = new int[arr.length];
            System.arraycopy(arr, 0, arr3, 0, arr.length);
            // int ans1 = func1(arr1, 0, arr.length, k);
            // TODO func1在哪儿被调用
            int ans2 = removeBoxes1(arr2);
            int ans3 = removeBoxes2(arr3);
            if (ans2 != ans3) {
                System.out.println(MessageFormat.format("remove boxed failed, arr {0}, ans2 {2}, ans3 {3}",
                        new String[]{Arrays.stream(arr).boxed().map(String::valueOf).collect(Collectors.joining(" ")),
                                String.valueOf(ans2), String.valueOf(ans3)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 消除arr[left...right]并且前面有连续k个arr[left]
    public static int func1(int[] arr, int left, int right, int k) {
        if (left > right) {
            return 0;
        }
        int ans = func1(arr, left + 1, right, 0) + (k + 1) * (k + 1);

        // 前面的K个X，和arr[L]数，合在一起了，现在有K+1个arr[L]位置的数
        for (int i = left + 1; i <= right; i++) {
            if (arr[i] == arr[left]) {
                ans = Math.max(ans, func1(arr, left + 1, i - 1, 0) + func1(arr, i, right, k + 1));
            }
        }
        return ans;
    }

    // 动态规划
    public static int removeBoxes1(int[] boxes) {
        int len = boxes.length;
        int[][][] dp = new int[len][len][len];
        int ans = process1(boxes, 0, len - 1, 0, dp);
        return ans;
    }

    public static int process1(int[] boxes, int left, int right, int k, int[][][] dp) {
        if (left > right) {
            return 0;
        }
        if (dp[left][right][k] > 0) {
            return dp[left][right][k];
        }
        int ans = process1(boxes, left + 1, right, 0, dp) + (k + 1) * (k + 1);
        for (int i = left + 1; i <= right; i++) {
            if (boxes[i] == boxes[left]) {
                ans = Math.max(ans, process1(boxes, left + 1, i - 1, 0, dp) + process1(boxes, i, right, k + 1, dp));
            }
        }
        dp[left][right][k] = ans;
        return ans;
    }

    public static int removeBoxes2(int[] boxes) {
        int len = boxes.length;
        int[][][] dp = new int[len][len][len];
        int ans = process2(boxes, 0, len - 1, 0, dp);
        return ans;
    }

    // 动态规划的常数项优化
    public static int process2(int[] boxes, int left, int right, int k, int[][][] dp) {
        if (left > right) {
            return 0;
        }
        if (dp[left][right][k] > 0) {
            return dp[left][right][k];
        }
        // 找到开头，
        // 1,1,1,1,1,5
        // 3 4 5 6 7 8
        //         !
        int last = left;
        while (last + 1 <= right && boxes[last + 1] == boxes[left]) {
            last++;
        }
        // K个1     (K + last - L) last
        int pre = k + last - left;
        int ans = (pre + 1) * (pre + 1) + process2(boxes, last + 1, right, 0, dp);
        for (int i = last + 2; i <= right; i++) {
            if (boxes[i] == boxes[left] && boxes[i - 1] != boxes[left]) {
                ans = Math.max(ans, process2(boxes, last + 1, i - 1, 0, dp) + process2(boxes, i, right, pre + 1, dp));
            }
        }
        dp[left][right][k] = ans;
        return ans;
    }
}

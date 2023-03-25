package com.serendipity.skills.quadrilateralinequalitytrick1;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个非负数组arr，长度为N，
 *              那么有N-1种方案可以把arr切成左右两部分
 *              每一种方案都有，min{左部分累加和，右部分累加和}
 *              min{左部分累加和，右部分累加和}，定义为S(N-1)，也就是说：
 *              S(N-1)：在arr[0…N-1]范围上，做最优划分所得到的min{左部分累加和，右部分累加和}的最大值
 *              现在要求返回一个长度为N的s数组，
 *              s[i] =在arr[0…i]范围上，做最优划分所得到的min{左部分累加和，右部分累加和}的最大值
 *              得到整个s数组的过程，做到时间复杂度O(N)
 * @date 2023/03/25/23:32
 */
public class BestSplitForEveryPosition {

    public static void main(String[] args) {
        int n = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n);
            int[] arr = randomArray(len, max);
            int[] ans1 = bestSplit1(arr);
            int[] ans2 = bestSplit2(arr);
            int[] ans3 = bestSplit3(arr);
            if (!isSameArray(ans1, ans2) || !isSameArray(ans1, ans3)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }

    // 对数器 时间复杂度O(N3)
    public static int[] bestSplit1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int len = arr.length;
        int[] ans = new int[len];
        ans[0] = 0;
        for (int range = 1; range < len; range++) {
            for (int s = 0; s < range; s++) {
                int sumL = 0;
                for (int left = 0; left <= s; left++) {
                    sumL += arr[left];
                }
                int sumR = 0;
                for (int right = s + 1; right <= range; right++) {
                    sumL += arr[right];
                }
                ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
            }
        }
        return ans;
    }

    // 求数组arr中下标left到right的累加和
    // sum是前缀和数组
    public static int sum(int[] sum, int left, int right) {
        return sum[right + 1] - sum[left];
    }

    // 利用前缀和 时间复杂度O(N2)
    public static int[] bestSplit2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int len = arr.length;
        int[] ans = new int[len];
        ans[0] = 0;
        int[] sum = new int[len + 1];
        for (int i = 0; i < len; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }

        for (int range = 1; range < len; range++) {
            for (int s = 0; s < range; s++) {
                int sumL = sum(sum, 0, s);
                int sumR = sum(sum, s + 1, range);
                ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
            }
        }
        return ans;
    }

    // 假设在arr[i]数组上，min{left,right}，left部分的下标index
    // 由i位置得到i+1位置的结果，怎么证明index不向左移动?
    // a、i位置left是小值，因为是非负数组，right加上arr[i+i]后left依然是小值，index不需要左移
    // b1、i位置right是小值，加上arr[i+1]后left是小值，如果index往左移动则left会变小，不符合要求
    // b2、i位置right是小值，加上arr[i+1]后right仍然是小值，index不能往左移动，使用反正法。
    // 如果index可以左移并保证right是小值，则在i位置的划分应该是index-1而不是index。
    // 只需要考虑right+arr[i+1]后index如何右移的问题
    public static int[] bestSplit3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int len = arr.length;
        int[] ans = new int[len];
        ans[0] = 0;
        int[] sum = new int[len + 1];
        for (int i = 0; i < len; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }

        // 0~range-1上，最优化分是左边0~best，右边best+1~range-1
        int best = 0;
        for (int range = 1; range < len; range++) {
            // best不回退
            while (best + 1 < range) {
                int before = Math.min(sum(sum, 0, best), sum(sum, best + 1, range));
                int after = Math.min(sum(sum, 0, best + 1), sum(sum, best + 2, range));
                // 如果没有=，并且before和after都取右边，则best永远也不会右移
                if (after >= before) {
                    best++;
                } else {
                    break;
                }
            }
            ans[range] = Math.min(sum(sum, 0, best), sum(sum, best + 1, range));
        }
        return ans;
    }

    public static int[] randomArray(int len, int max) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }

    public static boolean isSameArray(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        int N = arr1.length;
        for (int i = 0; i < N; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
}

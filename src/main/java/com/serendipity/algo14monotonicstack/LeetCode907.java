package com.serendipity.algo14monotonicstack;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个数组arr，
 *              返回所有子数组最小值的累加和
 * @date 2023/03/17/12:29
 */
public class LeetCode907 {

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 50;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = generateRandomArr(len, maxValue);
            int ans1 = subArrayMinSum1(arr);
            int ans2 = subArrayMinSum2(arr);
            int ans3 = subArrayMinSum3(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                printArr(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("出错了！");
                break;
            }
        }
    }

    // 对数器
    // 暴力解
    public static int subArrayMinSum1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int min = arr[i];
                for (int k = i + 1; k <= j; k++) {
                    min = Math.min(min, arr[k]);
                }
                ans += min;
            }
        }
        return ans;
    }

    // 不用单调栈，没有取模
    // 当前位置index作为子数组的最小值，找到子数组的个数
    // index左边的个数 * index右边的个数 = 子数组的个数
    public static int subArrayMinSum2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // arr[index]左边，小于等于arr[index]且离index最近的元素
        int[] left = leftNearLessEqual2(arr);
        // arr[index]右边，大于arr[index]且离index最近的元素
        int[] right = rightNearLess2(arr);
        // left、right数组确定以index作为最小值的子数组范围
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            int start = i - left[i];
            int end = right[i] - i;
            ans += start * end * arr[i];
        }
        return ans;
    }

    // index左边 <= arr[index] 并且最近的元素，包含最左边重复的元素
    public static int[] leftNearLessEqual2(int[] arr) {
        int len = arr.length;
        int[] left = new int[len];
        for (int i = 0; i < len; i++) {
            int ans = -1;
            for (int j = i - 1; j >= 0; j--) {
                // 重复值会记录到最左边的重复元素
                if (arr[j] <= arr[i]) {
                    ans = j;
                    break;
                }
            }
            left[i] = ans;
        }
        return left;
    }

    // index右边小于arr[index]最近的位置
    public static int[] rightNearLess2(int[] arr) {
        int len = arr.length;
        int[] right = new int[len];
        for (int i = 0; i < len; i++) {
            int ans = len;
            for (int j = i + 1; j < len; j++) {
                // 不包括右边的重复值
                if (arr[i] > arr[j]) {
                    ans = j;
                    break;
                }
            }
            right[i] = ans;
        }
        return right;
    }

    // 数组实现的单调栈 时间复杂度 O(n)
    public static int subArrayMinSum3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // 数组实现单调栈
        int[] stack = new int[arr.length];
        int[] left = leftNearLessEqual1(arr, stack);
        int[] right = rightNearLess1(arr, stack);
        long ans = 0;
        for (int i = 0; i < arr.length; i++) {
            long start = i - left[i];
            long end = right[i] - i;
            ans += start * end * (long) arr[i];
            ans %= 1000000007;
        }
        return (int) ans;
    }

    // 单调栈stack记录index位置左边 <= arr[index]最近的位置
    public static int[] leftNearLessEqual1(int[] arr, int[] stack) {
        int len = arr.length;
        int[] left = new int[len];
        int index = 0;
        for (int i = len - 1; i >= 0; i--) {
            // 出现小于栈顶元素时记录left数组
            while (index != 0 && arr[i] <= arr[stack[index - 1]]) {
                left[stack[--index]] = i;
            }
            // 压栈
            stack[index++] = i;
        }

        while (index != 0) {
            left[stack[--index]] = -1;
        }
        return left;
    }

    // 单调栈记录index右边 < arr[index]最近的位置
    public static int[] rightNearLess1(int[] arr, int[] stack) {
        int len = arr.length;
        int[] right = new int[len];
        int index = 0;
        for (int i = 0; i < len; i++) {
            // 出现小于栈顶元素时记录left数组
            while (index != 0 && arr[stack[index - 1]] > arr[i]) {
                right[stack[--index]] = i;
            }
            // 压栈
            stack[index++] = i;
        }

        while (index != 0) {
            right[stack[--index]] = len;
        }
        return right;
    }

    public static int[] generateRandomArr(int len, int maxValue) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue) + 1;
        }
        return ans;
    }

    public static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}

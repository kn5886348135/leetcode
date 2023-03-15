package com.serendipity.dynamicprogramming.recursion6;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个正数数组arr，请把arr中所有的数分成两个集合
 *              如果arr长度为偶数，两个集合包含数的个数要一样多
 *              如果arr长度为奇数，两个集合包含数的个数必须只差一个
 *              请尽量让两个集合的累加和接近
 *              返回：
 *              最接近的情况下，较小集合的累加和
 * @date 2023/03/15/11:52
 */
public class SplitSumClosedSizeHalf {

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 50;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = generateRandomArr(len, maxValue);
            int ans1 = splitArray(arr);
            int ans2 = dp1(arr);
            int ans3 = dp2(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                printArr(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("Oops!");
                break;
            }
        }
    }

    public static int splitArray(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        if ((arr.length & 1) == 0) {
            return process(arr, 0, arr.length >> 1, sum >> 1);
        } else {
            return Math.max(process(arr, 0, arr.length >> 1, sum >> 1), process(arr, 0, (arr.length >> 1) + 1, sum >> 1));
        }
    }

    // arr的index及以后位置挑选picks个，累加和小于并最接近rest
    public static int process(int[] arr, int index, int picks, int rest) {
        if (index == arr.length) {
            return picks == 0 ? 0 : -1;
        }
        // 不选择index
        int ans1 = process(arr, index + 1, picks, rest);
        int ans2 = -1;
        int next = -1;
        if (arr[index] <= rest) {
            next = process(arr, index + 1, picks - 1, rest - arr[index]);
        }
        if (next != -1) {
            ans2 = arr[index] + next;
        }
        return Math.max(ans1, ans2);
    }

    // 动态规划
    public static int dp1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum = sum >> 1;
        int len = arr.length;
        int m = (len + 1) >> 1;
        int[][][] dp = new int[len + 1][m + 1][sum + 1];
        for (int i = 0; i <= len; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }

        for (int rest = 0; rest <= sum; rest++) {
            dp[len][0][rest] = 0;
        }

        for (int i = len - 1; i >= 0; i--) {
            for (int picks = 0; picks <= m; picks++) {
                for (int rest = 0; rest <= sum; rest++) {
                    int ans1 = dp[i + 1][picks][rest];

                    int ans2 = -1;
                    int next = -1;
                    if (picks - 1 >= 0 && arr[i] <= rest) {
                        next = dp[i + 1][picks - 1][rest - arr[i]];
                    }
                    if (next != -1) {
                        ans2 = arr[i] + next;
                    }
                    dp[i][picks][rest] = Math.max(ans1, ans2);
                }
            }
        }
        if ((arr.length & 1) == 0) {
            return dp[0][arr.length >> 1][sum];
        } else {
            return Math.max(dp[0][arr.length >> 1][sum], dp[0][(arr.length >> 1) + 1][sum]);
        }
    }

    public static int dp2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum = sum >> 1;
        int len = arr.length;
        int m = (len + 1) >> 1;
        int[][][] dp = new int[len][m + 1][sum + 1];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }

        for (int i = 0; i < len; i++) {
            for (int k = 0; k <= sum; k++) {
                dp[i][0][k] = 0;
            }
        }

        for (int k = 0; k <= sum; k++) {
            dp[0][1][k] = arr[0] <= k ? arr[0] : Integer.MIN_VALUE;
        }

        for (int i = 1; i < len; i++) {
            for (int j = 1; j <= Math.min(i + 1, m); j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = dp[i - 1][j][k];
                    if (k - arr[i] >= 0) {
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - 1][k - arr[i]] + arr[i]);
                    }
                }
            }
        }
        return Math.max(dp[len - 1][m][sum], dp[len - 1][len - m][sum]);
    }

    public static int[] generateRandomArr(int length, int value) {
        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    public static void printArr(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}

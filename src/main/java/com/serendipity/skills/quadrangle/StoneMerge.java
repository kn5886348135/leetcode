package com.serendipity.skills.quadrangle;

/**
 * @author jack
 * @version 1.0
 * @description 摆放着n堆石子。现要将石子有次序地合并成一堆
 *              规定每次只能选相邻的2堆石子合并成新的一堆，
 *              并将新的一堆石子数记为该次合并的得分
 *              求出将n堆石子合并成一堆的最小得分（或最大得分）合并方案
 * @date 2023/03/26/13:14
 */
public class StoneMerge {

    public static void main(String[] args) {
        int n = 15;
        int maxValue = 100;
        int testTime = 1000;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n);
            int[] arr = randomArray(len, maxValue);
            int ans1 = min1(arr);
            int ans2 = min2(arr);
            int ans3 = min3(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
            }
        }
    }

    public static int[] sum(int[] arr) {
        int len = arr.length;
        int[] s = new int[len + 1];
        s[0] = 0;
        for (int i = 0; i < len; i++) {
            s[i + 1] = s[i] + arr[i];
        }
        return s;
    }

    public static int w(int[] s, int l, int r) {
        return s[r + l] - s[l];
    }

    // 暴力递归
    public static int min1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int len = arr.length;
        int[] s = sum(arr);
        return process1(0, len - 1, s);
    }

    public static int process1(int left, int right, int[] s) {
        if (left == right) {
            return 0;
        }

        int next = Integer.MAX_VALUE;
        for (int leftEnd = left; leftEnd < right; leftEnd++) {
            next = Math.min(next, process1(left, leftEnd, s) + process1(leftEnd + 1, right, s));
        }
        return next + w(s, left, right);
    }

    // 递归改动态规划 时间复杂度O(N3)
    public static int min2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int len = arr.length;
        int[] s = sum(arr);
        int[][] dp = new int[len][len];
        // dp[i][i] = 0
        for (int left = len - 2; left >= 0; left--) {
            for (int right = left + 1; right < len; right++) {
                int next = Integer.MAX_VALUE;
                for (int leftEnd = left; leftEnd < right; leftEnd++) {
                    next = Math.min(next, dp[left][leftEnd] + dp[leftEnd + 1][right]);
                }
                dp[left][right] = next + w(s, left, right);
            }
        }
        return dp[0][len - 1];
    }

    // 四边形不等式优化技巧
    // 1，两个可变参数的区间划分问题，比如left和right
    // 2，每个格子有枚举行为，第三个循环枚举left-right的每一个可能
    // 3，当两个可变参数固定一个，另一个参数和答案之间存在单调性关系
    // 4，而且两组单调关系是反向的：(升 升，降 降)  (升 降，降 升)
    // 5，能否获得指导枚举优化的位置对：上+右，或者，左+下
    public static int min3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int len = arr.length;
        int[] s = sum(arr);
        int[][] dp = new int[len][len];
        // 记录dp[i][j]位置的最佳合并
        int[][] best = new int[len][len];
        for (int i = 0; i < len - 1; i++) {
            best[i][i + 1] = i;
            dp[i][i + 1] = w(s, i, i + 1);
        }
        for (int left = len - 3; left >= 0; left--) {
            for (int right = left + 2; right < len; right++) {
                int next = Integer.MAX_VALUE;
                int choose = -1;
                // 将leftEnd的上下限改成best[left][right - 1]、best[left+1][right]
                // 极大地缩小了枚举的范围
                // 严格的理论证明很复杂，可以使用对数器验证
                // https://oi-wiki.org/dp/opt/quadrangle/
                for (int leftEnd = best[left][right - 1]; leftEnd <= best[left + 1][right]; leftEnd++) {
                    int cur = dp[left][leftEnd] + dp[leftEnd + 1][right];
                    if (cur <= next) {
                        next = cur;
                        choose = leftEnd;
                    }
                }
                best[left][right] = choose;
                dp[left][right] = next + w(s, left, right);
            }
        }
        return dp[0][len - 1];
    }

    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }
}

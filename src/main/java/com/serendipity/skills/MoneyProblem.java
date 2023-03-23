package com.serendipity.skills;

/**
 * @author jack
 * @version 1.0
 * @description int[] d，d[i]：i号怪兽的能力
 *              int[] p，p[i]：i号怪兽要求的钱
 *              开始时你的能力是0，你的目标是从0号怪兽开始，通过所有的怪兽。
 *              如果你当前的能力，小于i号怪兽的能力，你必须付出p[i]的钱，贿赂这个怪兽，然后怪兽就会加入你，他的
 *              能力直接累加到你的能力上；如果你当前的能力，大于等于i
 *              号怪兽的能力，你可以选择直接通过，你的能力并不会下降，你也可以选择贿赂这个怪兽，然后怪兽就会加入
 *              你，他的能力直接累加到你的能力上。
 *              返回通过所有的怪兽，需要花的最小钱数。
 * @date 2023/03/23/21:57
 */
public class MoneyProblem {

    public static void main(String[] args) {
        int len = 10;
        int value = 20;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            int[][] arrs = generateTwoRandomArray(len, value);
            int[] d = arrs[0];
            int[] p = arrs[1];
            long ans1 = func1(d, p);
            long ans2 = func2(d, p);
            long ans3 = func3(d, p);
            long ans4 = minMoney2(d,p);
            if (ans1 != ans2 || ans2 != ans3 || ans1 != ans4) {
                System.out.println("oops!");
            }
        }
    }

    public static long process1(int[] d, int[] p, int ability, int index) {
        if (index == d.length) {
            return 0;
        }
        if (ability < d[index]) {
            return p[index] + process1(d, p, ability + d[index], index + 1);
        } else {
            return Math.min(p[index] + process1(d, p, ability + d[index], index + 1),
                    0 + process1(d, p, ability, index + 1));
        }
    }

    public static long func1(int[] d, int[] p) {
        return process1(d, p, 0, 0);
    }

    public static long process2(int[] d, int[] p, int index, int money) {
        if (index == -1) {
            return money == 0 ? 0 : -1;
        }
        long preMaxAbility = process2(d, p, index - 1, money);
        long p1 = -1;
        if (preMaxAbility != -1 && preMaxAbility >= d[index]) {
            p1 = preMaxAbility;
        }
        long preMaxAbility2 = process2(d, p, index - 1, money - p[index]);
        long p2 = -1;
        if (preMaxAbility2 != -1) {
            p2 = d[index] + preMaxAbility2;
        }
        return Math.max(p1, p2);
    }

    public static int minMoney2(int[] d,int[] p) {
        int allMoney = 0;
        for (int i = 0; i < p.length; i++) {
            allMoney += p[i];
        }
        int len = d.length;
        for (int money = 0; money < allMoney; money++) {
            if (process2(d, p, len - 1, money) != -1) {
                return money;
            }
        }
        return allMoney;
    }

    public static long func2(int[] d,int[] p) {
        int sum = 0;
        for (int num : d) {
            sum += num;
        }
        long[][] dp = new long[d.length][sum + 1];
        for (int i = 0; i <= sum; i++) {
            dp[0][i] = 0;
        }
        for (int cur = d.length - 1; cur >= 0; cur--) {
            for (int hp = 0; hp <= sum; hp++) {
                if (hp + d[cur] > sum) {
                    continue;
                }
                if (hp < d[cur]) {
                    dp[cur][hp] = p[cur] + dp[cur + 1][hp + d[cur]];
                } else {
                    dp[cur][hp] = Math.min(p[cur] + dp[cur + 1][hp + d[cur]], dp[cur + 1][hp]);
                }
            }
        }
        return dp[0][0];
    }

    public static long func3(int[] d, int[] p) {
        int sum = 0;
        for (int num : p) {
            sum += num;
        }
        long[][] dp = new long[d.length][sum + 1];
        for (int i = 0; i < dp.length; i++) {
            dp[0][i] = -1;
        }
        dp[0][p[0]] = d[0];
        for (int i = 1; i < d.length; i--) {
            for (int j = 0; j <= sum; j++) {
                if (j >= p[i] && dp[i - 1][j - p[i]] != -1) {
                    dp[i][j] = dp[i - 1][j - p[i]] + d[i];
                }
                if (dp[i-1][j]>=d[i]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                }
            }
        }
        int ans = 0;
        for (int j = 0; j <= sum; j++) {
            if (dp[d.length - 1][j] != -1) {
                ans = j;
                break;
            }
        }
        return ans;
    }

    public static int[][] generateTwoRandomArray(int len, int value) {
        int size = (int) (Math.random() * len) + 1;
        int[][] arrs = new int[2][size];
        for (int i = 0; i < size; i++) {
            arrs[0][i] = (int) (Math.random() * value) + 1;
            arrs[1][i] = (int) (Math.random() * value) + 1;
        }
        return arrs;
    }
}

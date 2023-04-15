package com.serendipity.algo12dynamicprogramming.recursion2;

/**
 * @author jack
 * @version 1.0
 * @description 规定1和A对应、2和B对应、3和C对应...26和Z对应。那么一个数字字符串比如"111”就可以转化为:"AAA"、"KA"和"AK"。
 *              给定一个只有数字字符组成的字符串str，返回有多少种转化结果？
 * @date 2022/12/20/12:41
 */
public class ConvertToLetterString {

    // str只含有数字字符0~9
    // 返回多少种转化方案
    public static int number(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return process(str.toCharArray(), 0);
    }

    // str[0..i-1]转化无需过问
    // str[i.....]去转化，返回有多少种转化方法
    public static int process(char[] chs, int i) {
        if (i == chs.length) {
            return 1;
        }
        // i没到最后，说明有字符
        if (chs[i] == '0') { // 之前的决定有问题
            return 0;
        }
        // str[i] != '0'
        // 可能性一，i单转
        int ways = process(chs, i + 1);
        if (i + 1 < chs.length && (chs[i] - '0') * 10 + chs[i + 1] - '0' < 27) {
            ways += process(chs, i + 2);
        }
        return ways;
    }

    // 从右往左的动态规划
    // 就是上面方法的动态规划版本
    // dp[i]表示：str[i...]有多少种转化方式
    public static int dp1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chs = str.toCharArray();
        int len = chs.length;
        int[] dp = new int[len + 1];
        dp[len] = 1;
        for (int i = len - 1; i >= 0; i--) {
            if (chs[i] != '0') {
                int ways = dp[i + 1];
                if (i + 1 < chs.length && (chs[i] - '0') * 10 + chs[i + 1] - '0' < 27) {
                    ways += dp[i + 2];
                }
                dp[i] = ways;
            }
        }
        return dp[0];
    }

    // 从左往右的动态规划
    // dp[i]表示：str[0...i]有多少种转化方式
    public static int dp2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chs = str.toCharArray();
        int len = chs.length;
        if (chs[0] == '0') {
            return 0;
        }
        int[] dp = new int[len];
        dp[0] = 1;
        for (int i = 1; i < len; i++) {
            if (chs[i] == '0') {
                // 如果此时str[i]=='0'，那么他是一定要拉前一个字符(i-1的字符)一起拼的，
                // 那么就要求前一个字符，不能也是‘0’，否则拼不了。
                // 前一个字符不是‘0’就够了嘛？不够，还得要求拼完了要么是10，要么是20，如果更大的话，拼不了。
                // 这就够了嘛？还不够，你们拼完了，还得要求str[0...i-2]真的可以被分解！
                // 如果str[0...i-2]都不存在分解方案，那i和i-1拼成了也不行，因为之前的搞定不了。
                if (chs[i - 1] == '0' || chs[i - 1] > '2' || (i - 2 >= 0 && dp[i - 2] == 0)) {
                    return 0;
                } else {
                    dp[i] = i - 2 >= 0 ? dp[i - 2] : 1;
                }
            } else {
                dp[i] = dp[i - 1];
                if (chs[i - 1] != '0' && (chs[i - 1] - '0') * 10 + chs[i] - '0' <= 26) {
                    dp[i] += i - 2 >= 0 ? dp[i - 2] : 1;
                }
            }
        }
        return dp[len - 1];
    }

    public static String randomString(int len) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * 10) + '0');
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int n = 30;
        int testTime = 1000000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n);
            String s = randomString(len);
            int ans0 = number(s);
            int ans1 = dp1(s);
            int ans2 = dp2(s);
            if (ans0 != ans1 || ans0 != ans2) {
                System.out.println(s);
                System.out.println(ans0);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("convert to letter failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }
}

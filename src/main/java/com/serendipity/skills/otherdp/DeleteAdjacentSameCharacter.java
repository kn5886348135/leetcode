package com.serendipity.skills.otherdp;

/**
 * @author jack
 * @version 1.0
 * @description 如果一个字符相邻的位置没有相同字符，那么这个位置的字符出现不能被消掉。比如:"ab"，其中a和b都不能被消掉
 *              如果一个字符相邻的位置有相同字符，就可以一起消掉。比如:“abbbc”，中间一串的b是可以被消掉的，消除之后剩
 *              下“ac”。某些字符如果消掉了，剩下的字符认为重新靠在一起
 *              给定一个字符串，你可以决定每一步消除的顺序，目标是请尽可能多的消掉字符，返回最少的剩余字符数量
 *              比如："aacca", 如果先消掉最左侧的"aa"，那么将剩下"cca"，然后把"cc"消掉，剩下的"a"将无法再消除，返回1
 *              但是如果先消掉中间的"cc"，那么将剩下"aaa"，最后都消掉就一个字符也不剩了，返回0，这才是最优解。
 *              再比如："baaccabb"，如果先消除最左侧的两个a，剩下"bccabb"，如果再消除最左侧的两个c，剩下"babb"，
 *              最后消除最右侧的两个b，剩下"ba"无法再消除，返回2
 *              而最优策略是：先消除中间的两个c，剩下"baaabb"，再消除中间的三个a，剩下"bbb"，最后消除三个b，
 *              不留下任何字符，返回0，这才是最优解
 * @date 2023/03/28/20:59
 */
public class DeleteAdjacentSameCharacter {

    public static void main(String[] args) {
        int maxLen = 16;
        int variety = 3;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            String str = randomString(len, variety);
            int ans1 = restMin1(str);
            int ans2 = restMin2(str);
            int ans3 = restMin3(str);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(str);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

    // 暴力递归
    public static int restMin1(String str) {
        if (str == null) {
            return 0;
        }
        if (str.length() < 2) {
            return str.length();
        }
        int minLen = str.length();
        for (int left = 0; left < str.length(); left++) {
            for (int right = left + 1; right < str.length(); right++) {
                if (canDelete(str.substring(left, right + 1))) {
                    minLen = Math.min(minLen, restMin1(str.substring(0, left) + str.substring(right + 1)));
                }
            }
        }
        return minLen;
    }

    public static boolean canDelete(String str) {
        char[] chs = str.toCharArray();
        for (int i = 1; i < chs.length; i++) {
            if (chs[i - 1] != chs[i]) {
                return false;
            }
        }
        return true;
    }

    public static int restMin2(String str) {
        if (str == null) {
            return 0;
        }
        if (str.length() < 2) {
            return str.length();
        }
        char[] chs = str.toCharArray();
        return process(chs, 0, chs.length - 1, false);
    }

    public static int process(char[] chs, int left, int right, boolean has) {
        if (left > right) {
            return 0;
        }
        if (left == right) {
            return has ? 0 : 1;
        }
        int index = left;
        int k = has ? 1 : 0;
        while (index <= right && chs[index] == chs[left]) {
            k++;
            index++;
        }
        int ans1 = (k > 1 ? 0 : 1) + process(chs, index, right, false);
        int ans2 = Integer.MAX_VALUE;
        for (int split = index; split <= right; split++) {
            if (chs[split] == chs[left] && chs[split] != chs[split - 1]) {
                if (process(chs, index, split - 1, false) == 0) {
                    ans2 = Math.min(ans2, process(chs, split, right, k != 0));
                }
            }
        }
        return Math.min(ans1, ans2);
    }

    public static int restMin3(String str) {
        if (str == null) {
            return 0;
        }
        if (str.length() < 2) {
            return str.length();
        }
        char[] chs = str.toCharArray();
        int len = chs.length;
        int[][][] dp = new int[len][len][2];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                for (int k = 0; k < 2; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        return process3(chs, 0, len - 1, false, dp);
    }

    public static int process3(char[] chs, int left, int right, boolean has, int[][][] dp) {
        if (left > right) {
            return 0;
        }
        int k = has ? 1 : 0;
        if (dp[left][right][k] != -1) {
            return dp[left][right][k];
        }
        int ans = 0;
        if (left == right) {
            ans = (k == 0 ? 1 : 0);
        } else {
            int index = left;
            int all = k;
            while (index <= right && chs[index] == chs[left]) {
                all++;
                index++;
            }
            int ans1 = (all > 1 ? 0 : 1) + process3(chs, index, right, false, dp);
            int ans2 = Integer.MAX_VALUE;
            for (int split = index; split <= right; split++) {
                if (chs[split] == chs[left] && chs[split] != chs[split - 1]) {
                    if (process3(chs, index, split - 1, false, dp) == 0) {
                        ans2 = Math.min(ans2, process3(chs, split, right, all > 0, dp));
                    }
                }
            }
            ans = Math.min(ans1, ans2);
        }
        dp[left][right][k] = ans;
        return ans;
    }

    public static String randomString(int len, int variety) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * variety) + 'a');
        }
        return String.valueOf(str);
    }
}

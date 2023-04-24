package com.serendipity.algo17manacher;

/**
 * @author jack
 * @version 1.0
 * @description 在指定字符串后面拼接最少的字符，使整个字符串变成回文字符串
 * @date 2023/03/18/12:21
 */
public class AddShortestEnd {

    public static void main(String[] args) {
        String str1 = "abcd123321";
        System.out.println(shortestEnd(str1));
        // TODO 对数器
    }

    // 最右回文边界到达右边界时，补充的字符最少
    public static String shortestEnd(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        char[] chs = manacherString(str);
        int[] arr = new int[chs.length];
        int c = -1;
        int right = -1;
        int maxContainsEnd = -1;
        for (int i = 0; i < chs.length; i++) {
            arr[i] = right > i ? Math.min(arr[2 * c - i], right - i) : 1;
            while (i + arr[i] < chs.length && i - arr[i] > -1) {
                if (chs[i + arr[i]] == chs[i - arr[i]]) {
                    arr[i]++;
                } else {
                    break;
                }
            }

            if (i + arr[i] > right) {
                right = i + arr[i];
                c = i;
            }
            if (right == chs.length) {
                maxContainsEnd = arr[i];
                break;
            }
        }

        char[] ans = new char[str.length() - maxContainsEnd + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[ans.length - 1 - i] = chs[i * 2 + 1];
        }
        return String.valueOf(ans);
    }

    // 将原字符串插入特殊字符#
    public static char[] manacherString(String str) {
        char[] chs = str.toCharArray();
        char[] ans = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (i & 1) == 0 ? '#' : chs[index++];
        }
        return ans;
    }
}

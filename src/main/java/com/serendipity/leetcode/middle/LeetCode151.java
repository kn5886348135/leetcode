package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 反转字符串中的单词
 *              给你一个字符串 s ，请你反转字符串中 单词 的顺序。
 *
 *              单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
 *
 *              返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
 *
 *              注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间
 *              应当仅用单个空格分隔，且不包含任何额外的空格。
 * @date 2023/06/29/0:34
 */
public class LeetCode151 {

    public static void main(String[] args) {
        String str = "  hello world  ";
        System.out.println(reverseWords1(str));
    }

    // 使用API
    public static String reverseWords1(String s) {
        String[] arr = s.split(" ");
        if (arr == null || arr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i].trim().equals("")) {
                continue;
            }
            sb.append(arr[i].trim()).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    // 不使用API
    public static String reverseWords2(String s) {

        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    // 使用双端队列
    public static String reverseWords3(String s) {

        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    // 使用栈
    public static String reverseWords4(String s) {

        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

}

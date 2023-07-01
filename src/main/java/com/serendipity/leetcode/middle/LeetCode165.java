package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 比较版本号
 *              给你两个版本号 version1 和 version2 ，请你比较它们。
 *
 *              版本号由一个或多个修订号组成，各修订号由一个 '.' 连接。每个修订号由 多位数字 组成，可能包含 前导零 。每个版本号
 *              至少包含一个字符。修订号从左到右编号，下标从 0 开始，最左边的修订号下标为 0 ，下一个修订号下标为 1，以此类推。
 *              例如，2.5.33 和 0.1 都是有效的版本号。
 *
 *              比较版本号时，请按从左到右的顺序依次比较它们的修订号。比较修订号时，只需比较 忽略任何前导零后的整数值 。也就是说，
 *              修订号 1 和修订号 001 相等 。如果版本号没有指定某个下标处的修订号，则该修订号视为 0 。例如，版本 1.0 小于
 *              版本 1.1 ，因为它们下标为 0 的修订号相同，而下标为 1 的修订号分别为 0 和 1 ，0 < 1 。
 *
 * 返回规则如下：
 *
 *              如果 version1 > version2 返回 1，
 *              如果 version1 < version2 返回 -1，
 *              除此之外返回 0。
 * @date 2023/06/29/0:25
 */
public class LeetCode165 {

    public static void main(String[] args) {

    }

    // 遍历
    // 先比较较短长度的那部分，在处理较长那部分
    public int compareVersion1(String version1, String version2) {
        String[] arr1 = version1.split("\\.");
        String[] arr2 = version2.split("\\.");
        int len1 = arr1.length;
        int len2 = arr2.length;
        int len = Math.min(len1, len2);
        for (int i = 0; i < len; i++) {
            if (Integer.valueOf(arr1[i]) < Integer.valueOf(arr2[i])) {
                return -1;
            }
            if (Integer.valueOf(arr1[i]) > Integer.valueOf(arr2[i])) {
                return 1;
            }
        }
        if (len1 == len2) {
            return 0;
        }
        if (len1 > len) {
            for (int i = len; i < len1; i++) {
                if (Integer.valueOf(arr1[i]) > 0) {
                    return 1;
                }
            }
        }
        if (len2 > len) {
            for (int i = len; i < len2; i++) {
                if (Integer.valueOf(arr2[i]) > 0) {
                    return -1;
                }
            }
        }
        return 0;
    }

    // 同时遍历两个数组，长度不足的那部分用默认的0代替
    public int compareVersion2(String version1, String version2) {
        String[] arr1 = version1.split("\\.");
        String[] arr2 = version2.split("\\.");
        int len1 = arr1.length;
        int len2 = arr2.length;
        for (int i = 0; i < len1 || i < len2; i++) {
            int item1 = 0;
            int item2 = 0;
            if (i < len1) {
                item1 = Integer.parseInt(arr1[i]);
            }
            if (i < len2) {
                item2 = Integer.parseInt(arr2[i]);
            }
            if (item1 > item2) {
                return 1;
            }
            if (item1 < item2) {
                return -1;
            }
        }
        return 0;
    }

    // 双指针
    // 中间变量保存版本号，长度不足的那部分用0代替
    public int compareVersion3(String version1, String version2) {
        int len1 = version1.length();
        int len2 = version2.length();
        int i = 0;
        int j = 0;
        while (i < len1 || j < len2) {
            int x = 0;
            for (; i < len1 && version1.charAt(i) != '.'; i++) {
                x = x * 10 + version1.charAt(i) - '0';
            }
            i++;

            int y = 0;
            for (; j < len2 && version2.charAt(j) != '.'; j++) {
                y = y * 10 + version2.charAt(j) - '0';
            }
            j++;
            if (x != y) {
                return x > y ? 1 : -1;
            }
        }
        return 0;
    }

}
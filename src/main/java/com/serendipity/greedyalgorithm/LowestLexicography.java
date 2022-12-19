package com.serendipity.greedyalgorithm;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个由字符串组成的数组strs，必须把所有的字符串拼接起来，返回所有可能的拼接结果中，字典序最小的结果
 * 字典序比较的传递性，如果ab<ba，bc<cb，那么ac<ca
 * @date 2022/12/17/20:10
 */
public class LowestLexicography {
    public static String lowestString1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        TreeSet<String> result = process(strs);
        return result.size() == 0 ? "" : result.first();
    }

    // 将数组中的所有字符串全排列
    private static TreeSet<String> process(String[] strs) {
        TreeSet<String> result = new TreeSet<>();
        if (strs.length == 0) {
            result.add("");
            return result;
        }
        // 选取一个字符串拼接剩余字符串数组的全排列组合
        for (int i = 0; i < strs.length; i++) {
            String picked = strs[i];
            String[] unpicked = removeIndexString(strs, i);
            // 递归
            TreeSet<String> next = process(unpicked);
            for (String str : next) {
                result.add(picked + str);
            }
        }
        return result;
    }

    // 移除数组中下表为index的元素并返回数组
    private static String[] removeIndexString(String[] strs, int index) {
        int length = strs.length;
        String[] result = new String[length - 1];
        int idx = 0;
        for (int i = 0; i < length; i++) {
            if (idx != index) {
                result[idx++] = strs[i];
            }
        }
        return result;
    }

    public static String lowestString2(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        // 将所有字符串按照拼接后的字典序进行排序
        // 由a+b<b+a,b+c<c+b可以得到a+c<c+a，字典序比较的传递性
        Arrays.sort(strs, (stra, strb) -> (stra + strb).compareTo(strb + stra));
        String result = "";
        for (int i = 0; i < strs.length; i++) {
            result += strs[i];
        }
        return result;
    }

    // --------------------------------test-----------------------------------
    private static String generateRandomString(int length) {
        char[] result = new char[(int) (Math.random() * length) + 1];
        for (int i = 0; i < result.length; i++) {
            int value = (int) (Math.random() * 5);
            result[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
        }
        return String.valueOf(result);
    }

    private static String[] generateRandomStringArray(int arrlen, int strLen) {
        String[] result = new String[(int) (Math.random() * arrlen) + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = generateRandomString(strLen);
        }
        return result;
    }

    private static String[] copyStringArray(String[] arr) {
        String[] result = new String[arr.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = String.valueOf(arr[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 10000;
        System.out.println("-----------start------------");
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            if (!lowestString1(arr1).equals(lowestString2(arr2))) {
                for (String str : arr1) {
                    System.out.println(str + " , ");
                }
                System.out.println();
                System.out.println("failed");
            }
        }
        System.out.println("--------------------------finish--------------------------------------");
    }

}

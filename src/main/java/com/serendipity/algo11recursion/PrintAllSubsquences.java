package com.serendipity.algo11recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author jack
 * @version 1.0
 * @description 打印一个字符串的全部子序列
 * @date 2022/12/19/14:18
 */
public class PrintAllSubsquences {

    // 字符串的全部子序列，非连续
    public static List<String> subs(String str) {
        char[] chs = str.toCharArray();
        String path = "";
        List<String> result = new ArrayList<>();
        process1(chs, 0, result, path);
        return result;
    }

    // 递归的所有分支画成图就是一个完全二叉树
    // chs[0...index-1]已经处理过了，结果就是path
    private static void process1(char[] chs, int index, List<String> result, String path) {
        // 边界条件，递归终止
        if (index == chs.length) {
            result.add(path);
            return;
        }
        // 没有要index位置的字符
        process1(chs, index + 1, result, path);
        // 要了index位置的字符
        process1(chs, index + 1, result, path + chs[index]);
    }

    // 字符串的非连续全部子序列，set去重
    public static List<String> subsNoRepeat(String str) {
        char[] chs = str.toCharArray();
        String path = "";
        Set<String> set = new HashSet<>();
        process2(chs, 0, set, path);
        List<String> result = new ArrayList<>();
        result.addAll(set);
        return result;
    }

    private static void process2(char[] chs, int index, Set<String> set, String path) {
        if (index == chs.length) {
            set.add(path);
            return;
        }
        process2(chs, index + 1, set, path);
        process2(chs, index + 1, set, path + chs[index]);
    }

    public static void main(String[] args) {
        String test = "acccc";
        List<String> result1 = subs(test);
        List<String> result2 = subsNoRepeat(test);
        String str1 = result1.stream().collect(Collectors.joining(""));
        System.out.println(str1);
        System.out.println("=================");
        String str2 = result2.stream().collect(Collectors.joining(""));
        System.out.println(str2);
    }
}

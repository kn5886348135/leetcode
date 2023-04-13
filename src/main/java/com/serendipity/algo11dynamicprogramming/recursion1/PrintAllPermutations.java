package com.serendipity.algo11dynamicprogramming.recursion1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jack
 * @version 1.0
 * @description 打印字符串的全排列
 * @date 2022/12/19/14:34
 */
public class PrintAllPermutations {

    private static List<String> permutation1(String str) {
        List<String> result = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return result;
        }

        char[] chs = str.toCharArray();
        List<Character> unpicked = new ArrayList<>();
        for (char ch : chs) {
            unpicked.add(ch);
        }
        String path = "";
        func(unpicked, path, result);
        return result;
    }

    private static void func(List<Character> unpicked, String path, List<String> result) {
        // 递归终止条件
        if (unpicked.isEmpty()) {
            result.add(path);
        } else {
            int size = unpicked.size();
            // 遍历每一个没有被选中的字符，依次选中并拼接
            for (int i = 0; i < size; i++) {
                char ch = unpicked.get(i);
                unpicked.remove(i);
                func(unpicked, path + ch, result);
                // 还原被移除的字符
                unpicked.add(i, ch);
            }
        }
    }

    private static List<String> permutation2(String str) {
        List<String> result = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return result;
        }

        char[] chs = str.toCharArray();
        generate1(chs, 0, result);
        return result;
    }

    private static void generate1(char[] chs, int index, List<String> result) {
        if (index == chs.length) {
            result.add(String.valueOf(chs));
        } else {
            // 递归交换index和index后的每一个字符
            for (int i = index; i < chs.length; i++) {
                swap(chs, index, i);
                // 递归index + 1
                generate1(chs, index + 1, result);
                // 还原
                swap(chs, index, i);
            }
        }
    }

    // 加入去重逻辑
    private static List<String> permutation3(String str) {
        List<String> result = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return result;
        }

        char[] chs = str.toCharArray();
        generate2(chs, 0, result);
        return result;
    }

    private static void generate2(char[] chs, int index, List<String> result) {
        if (index == chs.length) {
            result.add(String.valueOf(chs));
        } else {
            boolean[] visited = new boolean[256];
            for (int i = index; i < chs.length; i++) {
                // 比如index=1，chs[2]和chs[5]字符相同，i =2 和 i = 5 交换后的字符数组是一样的，递归产生的结果相同
                if (!visited[chs[i]]) {
                    visited[chs[i]] = true;
                    swap(chs, index, i);
                    generate2(chs, index + 1, result);
                    swap(chs, index, i);
                }
            }
        }
    }

    private static void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }

    public static void main(String[] args) {
        String test = "acc";
        List<String> result1 = permutation1(test);
        String str1 = result1.stream().collect(Collectors.joining(""));
        System.out.println(str1);
        System.out.println("=================");
        List<String> result2 = permutation2(test);
        String str2 = result2.stream().collect(Collectors.joining(""));
        System.out.println(str2);
        System.out.println("=================");
        List<String> result3 = permutation3(test);
        String str3 = result3.stream().collect(Collectors.joining(""));
        System.out.println(str3);
        System.out.println("=================");
    }
}

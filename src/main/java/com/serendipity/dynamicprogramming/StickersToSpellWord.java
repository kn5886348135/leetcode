package com.serendipity.dynamicprogramming;

import java.util.HashMap;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个字符串str，给定一个字符串类型的数组arr，出现的字符都是小写英文。arr每一个字符串，代表一张
 * 贴纸，你可以把单个字符剪开使用，目的是拼出str来。返回需要至少多少张贴纸可以完成这个任务。
 * @date 2022/12/22/10:06
 */
public class StickersToSpellWord {

    public static int minStickers1(String[] stickers, String target) {
        int ans = process1(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // 所有贴纸stickers，每一张贴纸都有无数张
    public static int process1(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (String first : stickers) {
            String rest = minus(target, first);
            // first是有效的，开始递归
            if (rest.length() != target.length()) {
                min = Math.min(min, process1(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    // 删除目标字符串中的字符
    public static String minus(String target, String str) {
        char[] chs1 = target.toCharArray();
        char[] chs2 = str.toCharArray();
        int[] count = new int[26];
        for (char ch : chs1) {
            count[ch - 'a']++;
        }
        for (char ch : chs2) {
            count[ch - 'a']--;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            // 拼接剩余的字符，不关心count[i] <= 0的字符
            if (count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    builder.append((char) (i + 'a'));
                }
            }
        }
        return builder.toString();
    }

    public static int minStickers2(String[] stickers, String target) {
        int length = stickers.length;
        // 用词频代替贴纸数组，不关心字符的顺序
        int[][] counts = new int[length][26];
        for (int i = 0; i < length; i++) {
            char[] chs = stickers[i].toCharArray();
            for (char ch : chs) {
                counts[i][ch - 'a']++;
            }
        }
        int ans = process2(counts, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // stickers[i] 数组，原来i号贴纸的字符统计
    public static int process2(int[][] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        char[] chs = target.toCharArray();
        int[] count = new int[26];
        for (char ch : chs) {
            count[ch - 'a']++;
        }
        int length = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            int[] sticker = stickers[i];
            // 剪枝，贪心算法，减少重复操作
            // 消除target第一个字符的位置不影响最终答案
            // 处理包含第一个字符的贴纸
            // 所有贴纸都不包含target的第一个字符，则无法完成要求
            if (sticker[chs[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (count[j] > 0) {
                        int nums = count[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    public static int minStickers3(String[] stickers, String target) {
        int length = stickers.length;
        // 用词频代替贴纸数组，不关心字符的顺序
        int[][] counts = new int[length][26];
        for (int i = 0; i < length; i++) {
            char[] chs = stickers[i].toCharArray();
            for (char ch : chs) {
                counts[i][ch - 'a']++;
            }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        int ans = process3(counts, target, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // stickers[i] 数组，原来i号贴纸的字符统计
    // target的可能性不确定，无法像int参数可以变成数组，无法写成动态规划的数组
    public static int process3(int[][] stickers, String target, HashMap<String, Integer> dp) {
        if (dp.containsKey(target)) {
            return dp.get(target);
        }
        if (target.length() == 0) {
            return 0;
        }
        char[] chs = target.toCharArray();
        int[] count = new int[26];
        for (char ch : chs) {
            count[ch - 'a']++;
        }
        int length = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            int[] sticker = stickers[i];
            // 剪枝，贪心算法，减少重复操作
            // 消除target第一个字符的位置不影响最终答案
            // 处理包含第一个字符的贴纸
            // 所有贴纸都不包含target的第一个字符，则无法完成要求
            if (sticker[chs[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (count[j] > 0) {
                        int nums = count[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        dp.put(target, ans);
        return ans;
    }

}

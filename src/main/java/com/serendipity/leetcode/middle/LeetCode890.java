package com.serendipity.leetcode.middle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jack
 * @version 1.0
 * @description 查找和替换模式
 *              你有一个单词列表 words 和一个模式  pattern，你想知道 words 中的哪些单词与模式匹配。
 *
 *              如果存在字母的排列 p ，使得将模式中的每个字母 x 替换为 p(x) 之后，我们就得到了所需的单词，那么单词与模式是匹配的。
 *
 *              （回想一下，字母的排列是从字母到字母的双射：每个字母映射到另一个字母，没有两个字母映射到同一个字母。）
 *
 *              返回 words 中与给定模式匹配的单词列表。
 *
 *              你可以按任何顺序返回答案。
 * @date 2023/06/29/1:15
 */
public class LeetCode890 {

    public static void main(String[] args) {
        
    }

    public List<String> findAndReplacePattern(String[] words, String pattern) {
        List<String> ans = new ArrayList<>();
        for (String word : words) {
            if (matches(word, pattern)) {
                ans.add(word);
            }
        }
        return ans;
    }

    private static boolean matches(String word, String pattern) {
        if (word.length() != pattern.length()) {
            return false;
        }
        int len = word.length();
        Map<Character, Character> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            char ch1 = word.charAt(i);
            char ch2 = pattern.charAt(i);
            if (!map.containsKey(ch1)) {
                Collection<Character> list = map.values();
                if (list != null && list.contains(ch2)) {
                    return false;
                }
                map.put(ch1, ch2);
            } else if (map.get(ch1) != ch2) {
                return false;
            }
        }
        return true;
    }
}

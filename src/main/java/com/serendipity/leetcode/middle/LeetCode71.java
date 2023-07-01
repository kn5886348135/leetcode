package com.serendipity.leetcode.middle;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author jack
 * @version 1.0
 * @description 简化路径
 *              给你一个字符串 path ，表示指向某一文件或目录的 Unix 风格 绝对路径 （以 '/' 开头），请你将其转化为更加简洁的规范路径。
 *
 *              在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..） 表示将目录切换到上一级（指向父目录）；
 *              两者都可以是复杂相对路径的组成部分。任意多个连续的斜杠（即，'//'）都被视为单个斜杠 '/' 。
 *              对于此问题，任何其他格式的点（例如，'...'）均被视为文件/目录名称。
 *
 *              请注意，返回的 规范路径 必须遵循下述格式：
 *
 *              始终以斜杠 '/' 开头。
 *              两个目录名之间必须只有一个斜杠 '/' 。
 *              最后一个目录名（如果存在）不能 以 '/' 结尾。
 *              此外，路径仅包含从根目录到目标文件或目录的路径上的目录（即，不含 '.' 或 '..'）。
 *              返回简化后得到的 规范路径 。
 * @date 2023/06/29/0:54
 */
public class LeetCode71 {

    public static void main(String[] args) {

    }

    public String simplifyPath(String path) {
        if (path == null || path.length() == 0 || !path.startsWith("/")) {
            return "/";
        }
        String[] arr = path.split("/");
        Deque<String> stack = new ArrayDeque<>();
        for (int i = 0; i < arr.length; i++) {
            if ("..".equals(arr[i])) {
                if (!stack.isEmpty()) {
                    stack.pollLast();
                }
            } else if (arr[i].length() > 0 && !".".equals(arr[i])) {
                stack.offerLast(arr[i]);
            }
        }
        StringBuffer sb = new StringBuffer();
        if (stack.isEmpty()) {
            sb.append("/");
        } else {
            while (!stack.isEmpty()) {
                sb.append("/");
                sb.append(stack.pollFirst());
            }
        }
        return sb.toString();
    }
}

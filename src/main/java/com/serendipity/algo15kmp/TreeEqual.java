package com.serendipity.algo15kmp;


import java.util.ArrayList;
import java.util.List;

/**
 * @author jack
 * @version 1.0
 * @description 给定两棵二叉树的头节点head1和head2
 *              想知道head1中是否有某个子树的结构和head2完全一样
 * @date 2023/03/17/19:59
 */
public class TreeEqual {

    public static void main(String[] args) {
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testTimes = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node big = generateRandomBST(bigTreeLevel, nodeMaxValue);
            Node small = generateRandomBST(smallTreeLevel, nodeMaxValue);
            boolean ans1 = containsTree1(big, small);
            boolean ans2 = containsTree2(big, small);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");
    }

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    // 对数器 暴力递归
    public static boolean containsTree1(Node node1, Node node2) {
        if (node2 == null) {
            return true;
        }
        if (node1 == null) {
            return false;
        }
        if (isSameValueStructure(node1, node2)) {
            return true;
        }
        return containsTree1(node1.left, node2) || containsTree1(node1.right, node2);
    }

    // 递归判断二叉树的结构是否相同，判断值
    public static boolean isSameValueStructure(Node node1, Node node2) {
        if (node1 == null && node2 != null) {
            return false;
        }
        if (node1 != null && node2 == null) {
            return false;
        }
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1.value != node2.value) {
            return false;
        }
        return isSameValueStructure(node1.left, node2.left) && isSameValueStructure(node1.right, node2.right);
    }

    // 获取树的先序遍历组成字符串数组
    // 使用kmp算法匹配
    public static boolean containsTree2(Node node1, Node node2) {
        if (node2 == null) {
            return true;
        }
        if (node1 == null) {
            return false;
        }
        List<String> list1 = preSerial(node1);
        List<String> list2 = preSerial(node2);
        String[] str = new String[list1.size()];
        for (int i = 0; i < str.length; i++) {
            str[i] = list1.get(i);
        }

        String[] match = new String[list2.size()];
        for (int i = 0; i < match.length; i++) {
            match[i] = list2.get(i);
        }
        return getIndexOf(str, match) != -1;
    }

    public static List<String> preSerial(Node node) {
        List<String> ans = new ArrayList<>();
        pres(node, ans);
        return ans;
    }

    public static void pres(Node node, List<String> list) {
        if (node == null) {
            // 空节点一定要添加，表示节点结束
            list.add(null);
        } else {
            list.add(String.valueOf(node.value));
            pres(node.left, list);
            pres(node.right, list);
        }
    }

    public static int getIndexOf(String[] str1, String[] str2) {
        if (str1 == null || str2 == null || str1.length < 1 || str1.length < str2.length) {
            return -1;
        }
        int x = 0;
        int y = 0;
        int[] next = getNextArray(str2);
        while (x < str1.length && y < str2.length) {
            if (isEqual(str1[x], str2[y])) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == str2.length ? x - y : -1;
    }

    public static int[] getNextArray(String[] ms) {
        if (ms.length == 1) {
            return new int[]{ -1 };
        }
        int[] next = new int[ms.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < next.length) {
            if (isEqual(ms[i - 1], ms[cn])) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    public static boolean isEqual(String a, String b) {
        if (a == null && b == null) {
            return true;
        } else {
            if (a == null || b == null) {
                return false;
            } else {
                return a.equals(b);
            }
        }
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

}

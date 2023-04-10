package com.serendipity.algo6trietree;

import java.util.HashMap;
import java.util.Map;

public class TrieTree {

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                    right.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans2 = trie2.search(arr[j]);
                    int ans3 = right.search(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefixNumber(arr[j]);
                    int ans2 = trie2.prefixNumber(arr[j]);
                    int ans3 = right.prefixNumber(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
    }

    // 前缀树节点类型
    public static class Node1 {
        public int pass;
        public int end;
        public Node1[] nexts;

        public Node1() {
            this.pass = 0;
            this.end = 0;
            this.nexts = new Node1[26];
        }
    }

    public static class Trie1 {
        private Node1 root;

        public Trie1() {
            this.root = new Node1();
        }

        // 新增
        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chs = word.toCharArray();
            Node1 node = this.root;
            // 字符串非空，一定会经过root节点
            node.pass++;
            int index = 0;
            // 深度优先搜索遍历每一个字符
            for (int i = 0; i < chs.length; i++) {
                // 由字符，对应成走向哪条路
                index = chs[i] - 'a';
                // 不存在则创建
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node1();
                }
                // 下一层节点
                node = node.nexts[index];
                node.pass++;
            }
            node.end++;
        }

        // 删除
        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                Node1 node = this.root;
                // 字符串非空，一定会经过root节点
                node.pass--;
                int index = 0;
                // 深度优先搜索遍历每一个字符
                for (int i = 0; i < chs.length; i++) {
                    index = chs[i] - 'a';
                    // 最后一个则赋值null，并结束遍历
                    if (--node.nexts[index].pass == 0) {
                        node.nexts[index] = null;
                        return;
                    }
                    // 下一层节点
                    node = node.nexts[index];
                }
                node.end--;
            }
        }

        // 查找word这个单词之前加入过几次
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chs = word.toCharArray();
            Node1 node = this.root;
            int index = 0;
            // 深度优先搜索遍历每一个字符
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                // 还没有遍历完就出现空节点，证明不存在
                if (node.nexts[index] == null) {
                    return 0;
                }
                // 下一层节点
                node = node.nexts[index];
            }
            return node.end;
        }

        // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chs = pre.toCharArray();
            Node1 node = this.root;
            int index = 0;
            // 深度优先搜索遍历每一个字符
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                // 还没有遍历完就出现空节点，证明不存在
                if (node.nexts[index] == null) {
                    return 0;
                }
                // 下一层节点
                node = node.nexts[index];
            }
            return node.pass;
        }
    }

    public static class Node2 {
        public int pass;
        public int end;
        public Map<Integer, Node2> nexts;

        public Node2() {
            this.pass = 0;
            this.end = 0;
            this.nexts = new HashMap<>();
        }
    }

    public static class Trie2 {
        private Node2 root;

        public Trie2() {
            this.root = new Node2();
        }

        // 新增
        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chs = word.toCharArray();
            Node2 node = this.root;
            // 字符串非空，一定会经过root节点
            node.pass++;
            int index = 0;
            // 深度优先搜索遍历每一个字符
            for (int i = 0; i < chs.length; i++) {
                index = chs[i];
                // 不存在则创建
                if (!node.nexts.containsKey(index)) {
                    node.nexts.put(index, new Node2());
                }
                // 下一层节点
                node = node.nexts.get(index);
                node.pass++;
            }
            node.end++;
        }

        // 删除
        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                Node2 node = this.root;
                // 字符串非空，一定会经过root节点
                node.pass--;
                int index = 0;
                // 深度优先搜索遍历每一个字符
                for (int i = 0; i < chs.length; i++) {
                    index = chs[i];
                    // 最后一个则赋值null，并结束遍历
                    if (--node.nexts.get(index).pass == 0) {
                        node.nexts.remove(index);
                        return;
                    }
                    // 下一层节点
                    node = node.nexts.get(index);
                }
                node.end--;
            }
        }

        // 查找
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chs = word.toCharArray();
            Node2 node = this.root;
            int index = 0;
            // 深度优先搜索遍历每一个字符
            for (int i = 0; i < chs.length; i++) {
                index = chs[i];
                // 还没有遍历完就出现空节点，证明不存在
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                // 下一层节点
                node = node.nexts.get(index);
            }
            return node.end;
        }

        // 以pre作为前缀的字符串个数
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chs = pre.toCharArray();
            Node2 node = this.root;
            int index = 0;
            // 深度优先搜索遍历每一个字符
            for (int i = 0; i < chs.length; i++) {
                index = chs[i];
                // 还没有遍历完就出现空节点，证明不存在
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                // 下一层节点
                node = node.nexts.get(index);
            }
            return node.pass;
        }
    }

    public static class Right {
        private HashMap<String, Integer> box;

        public Right() {
            this.box = new HashMap<>();
        }

        public void insert(String word) {
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count += box.get(cur);
                }
            }
            return count;
        }
    }

    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }
}

package com.serendipity.indextree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author jack
 * @version 1.0
 * @description Aho–Corasick算法  AC自动机
 *              解决在一个大字符串中，找到多个候选字符串的问题
 *
 *              AC自动机算法核心
 *              1）把所有匹配串生成一棵前缀树
 *              2）前缀树节点增加fail指针
 *              3）fail指针的含义：如果必须以当前字符结尾，当前形成的路径是str，剩下哪一个字符串的前缀和str的后缀，拥有
 *              最大的匹配长度。fail指针就指向那个字符串的最后一个字符所对应的节点。（迷不迷？听讲述！）
 * @date 2023/03/19/21:58
 */
public class AhoCorasick {

    public static void main(String[] args) {
        AC ac = new AC();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("c");
        ac.build();
        System.out.println(ac.containNum("cdhe"));
    }

    public static class Node {
        // 有多少个字符串以该节点结尾
        public int end;
        public Node fail;
        public Node[] nexts;

        public Node() {
            this.end = 0;
            this.fail = null;
            // 因为英文只有26个字母
            nexts = new Node[26];
        }
    }

    public static class AC {
        private Node root;

        public AC() {
            this.root = new Node();
        }

        // 调用insert，加入匹配串
        public void insert(String str) {
            char[] chs = str.toCharArray();
            Node cur = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (cur.nexts[index] == null) {
                    Node next = new Node();
                    cur.nexts[index] = next;
                }
                cur = cur.nexts[index];
            }
            cur.end++;
        }

        // 构建fail指针
        public void build() {
            // 宽度优先搜索
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node fail = null;
            while (!queue.isEmpty()) {
                cur = queue.poll();
                for (int i = 0; i < 26; i++) {
                    if (cur.nexts[i] != null) {
                        cur.nexts[i].fail = root;
                        fail = cur.fail;
                        while (fail != null) {
                            if (fail.nexts[i] != null) {
                                cur.nexts[i].fail = fail.nexts[i];
                                break;
                            }
                            fail = fail.fail;
                        }
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        public int containNum(String content) {
            char[] chs = content.toCharArray();
            Node cur = root;
            Node follow = null;
            int index = 0;
            int ans = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;
                follow = cur;
                while (follow != root) {
                    if (follow.end == -1) {
                        break;
                    }
                    // 不同的需求，在这一段{ }之间修改
                    {
                        ans += follow.end;
                        follow.end = -1;
                    }
                    follow = follow.fail;
                }
            }
            return ans;
        }
    }
}

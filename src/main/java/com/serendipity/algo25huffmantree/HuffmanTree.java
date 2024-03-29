package com.serendipity.algo25huffmantree;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author jack
 * @version 1.0
 * @description 哈夫曼编码，没有处理字节转换、边界(字符串为空)
 * @date 2023/03/28/22:02
 */
public class HuffmanTree {

    public static void main(String[] args) {
        // 根据词频表生成哈夫曼编码表
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('A', 60);
        map.put('B', 45);
        map.put('C', 13);
        map.put('D', 69);
        map.put('E', 14);
        map.put('F', 5);
        map.put('G', 3);
        HashMap<Character, String> huffmanForm = huffmanForm(map);
        for (Map.Entry<Character, String> entry : huffmanForm.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("====================");
        // str是原始字符串
        String original = "CBBBAABBACAABDDEFBA";
        System.out.println(original);
        // countMap是根据str建立的词频表
        HashMap<Character, Integer> countMap = countMap(original);
        // hf是根据countMap生成的哈夫曼编码表
        HashMap<Character, String> hf = huffmanForm(countMap);
        // huffmanEncode是原始字符串转译后的哈夫曼编码
        String huffmanEncode = huffmanEncode(original, hf);
        System.out.println(huffmanEncode);
        // huffmanDecode是哈夫曼编码还原成的原始字符串
        String huffmanDecode = huffmanDecode(huffmanEncode, hf);
        System.out.println(huffmanDecode);
        System.out.println("====================");
        System.out.println("大样本随机测试开始");
        // 所含字符种类
        int possibilities = 26;
        // 字符串最大长度
        int maxSize = 500;
        // 随机测试进行的次数
        int testTime = 100000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            String str = CommonUtil.generateRandomString(possibilities, maxSize);
            HashMap<Character, Integer> counts = countMap(str);
            HashMap<Character, String> form = huffmanForm(counts);
            String encode = huffmanEncode(str, form);
            String decode = huffmanDecode(encode, form);
            if (!str.equals(decode)) {
                System.out.println(MessageFormat.format("huffman code failed, str {0}, encode {1}, decode {2}",
                        new String[]{str, encode, decode}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 根据str生成词频统计表
    public static HashMap<Character, Integer> countMap(String str) {
        HashMap<Character, Integer> ans = new HashMap<>();
        char[] chs = str.toCharArray();
        for (char ch : chs) {
            if (!ans.containsKey(ch)) {
                ans.put(ch, 1);
            } else {
                ans.put(ch, ans.get(ch) + 1);
            }
        }
        return ans;
    }

    public static class Node {
        public int count;
        public Node left;
        public Node right;

        public Node(int count) {
            this.count = count;
        }
    }

    // 根据由文章生成词频表countMap，生成哈夫曼编码表
    // key : 字符
    // value: 该字符编码后的二进制形式
    // 比如，频率表 A：60, B:45, C:13 D:69 E:14 F:5 G:3
    // A 10
    // B 01
    // C 0011
    // D 11
    // E 000
    // F 00101
    // G 00100
    public static HashMap<Character, String> huffmanForm(HashMap<Character, Integer> countMap) {
        HashMap<Character, String> ans = new HashMap<>();
        if (countMap.size() == 1) {
            for (char key : countMap.keySet()) {
                ans.put(key, "0");
            }
            return ans;
        }
        HashMap<Node, Character> nodes = new HashMap<>();
        PriorityQueue<Node> heap = new PriorityQueue<>(Comparator.comparingInt(o -> o.count));
        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            Node cur = new Node(entry.getValue());
            char ch = entry.getKey();
            nodes.put(cur, ch);
            heap.add(cur);
        }
        while (heap.size() != 1) {
            Node left = heap.poll();
            Node right = heap.poll();
            Node h = new Node(left.count + right.count);
            h.left = left;
            h.right = right;
            heap.add(h);
        }
        Node head = heap.poll();
        fillForm(head, "", nodes, ans);
        return ans;
    }

    public static void fillForm(Node head, String pre, HashMap<Node, Character> nodes, HashMap<Character, String> ans) {
        if (nodes.containsKey(head)) {
            ans.put(nodes.get(head), pre);
        } else {
            fillForm(head.left, pre + "0", nodes, ans);
            fillForm(head.right, pre + "1", nodes, ans);
        }
    }

    // 原始字符串str，根据哈夫曼编码表，转译成哈夫曼编码返回
    public static String huffmanEncode(String str, HashMap<Character, String> huffmanForm) {
        char[] chs = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char ch : chs) {
            builder.append(huffmanForm.get(ch));
        }
        return builder.toString();
    }

    // 原始字符串的哈夫曼编码huffmanEncode，根据哈夫曼编码表，还原成原始字符串
    public static String huffmanDecode(String huffmanEncode, HashMap<Character, String> huffmanForm) {
        TrieNode root = createTrie(huffmanForm);
        TrieNode cur = root;
        char[] encode = huffmanEncode.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < encode.length; i++) {
            int index = encode[i] == '0' ? 0 : 1;
            cur = cur.nexts[index];
            if (cur.nexts[0] == null && cur.nexts[1] == null) {
                builder.append(cur.value);
                cur = root;
            }
        }
        return builder.toString();
    }

    public static TrieNode createTrie(HashMap<Character, String> huffmanForm) {
        TrieNode root = new TrieNode();
        for (char key : huffmanForm.keySet()) {
            char[] path = huffmanForm.get(key).toCharArray();
            TrieNode cur = root;
            for (int i = 0; i < path.length; i++) {
                int index = path[i] == '0' ? 0 : 1;
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new TrieNode();
                }
                cur = cur.nexts[index];
            }
            cur.value = key;
        }
        return root;
    }

    public static class TrieNode {
        public char value;
        public TrieNode[] nexts;

        public TrieNode() {
            this.value = 0;
            this.nexts = new TrieNode[2];
        }
    }

}

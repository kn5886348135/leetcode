package com.serendipity.linkedlist;

import java.util.HashMap;

/**
 * @author jack
 * @version 1.0
 * @description 复制带随机指针的链表 LeetCode138
 *              https://leetcode.com/problems/copy-list-with-random-pointer/
 * @date 2022/12/22/17:12
 */
public class CopyListWithRandom {

    public static class Node {
        int value;
        Node next;
        Node random;

        public Node(int value) {
            this.value = value;
            this.next = null;
            this.random = null;
        }
    }

    public static Node copyRandomList1(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.value));
            cur = cur.next;
        }
        cur=head;
        while (cur != null) {
            // cur 老
            // map.get(cur) 新
            // 新.next -> cur.next克隆节点找到
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
        }
        return map.get(head);
    }

    public static Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node next = null;
        // 1 -> 2 -> 3 -> null
        // 1 -> 1' -> 2 -> 2' -> 3 -> 3'
        while (cur != null) {
            next = cur.next;
            cur.next = new Node(cur.value);
            cur.next.next = next;
            cur = next;
        }
        cur=head;
        Node copy = null;
        // 1 1' 2 2' 3 3'
        // 依次设置 1‘ 2’ 3‘ random指针
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            copy.random = cur.random != null ? cur.random.next : null;
            cur = next;
        }
        Node res = head.next;
        cur = head;
        // 老 新 混在一起，next方向上，random正确
        // next方向上，把新老链表分离
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            cur.next = next;
            copy.next = next != null ? next.next : null;
            cur = next;
        }
        return res;
    }
}

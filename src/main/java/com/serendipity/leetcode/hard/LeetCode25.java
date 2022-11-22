package com.serendipity.leetcode.hard;

/**
 * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 */
public class LeetCode25 {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(0);
        int n = 13;
        int k = 1;
        ListNode tmp = listNode;

        for (int i = 0; i < n; i++) {
            ListNode node = new ListNode(i + 1);
            tmp.next = node;
            tmp = node;
        }
        tmp = listNode;
        while (tmp != null) {
            System.out.println(tmp);
            tmp = tmp.next;
        }

        listNode = reverseKGroup(listNode, k);

        tmp = listNode;
        while (tmp != null) {
            System.out.println(tmp);
            tmp = tmp.next;
        }
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode start = head;
        ListNode end = getKGroupEnd(start, k);
        if (end == null) {
            return head;
        }
        head = end;

        reverseKGroup(start, end);
        ListNode lastEnd = start;
        while (lastEnd.next != null) {
            start = lastEnd.next;
            end = getKGroupEnd(start, k);
            if (end == null) {
                return head;
            }
            reverseKGroup(start, end);
            lastEnd.next = end;
            lastEnd = start;
        }
        return head;
    }

    public static void reverseKGroup(ListNode start, ListNode end) {
        end = end.next;
        ListNode pre = null;
        ListNode cur = start;
        ListNode next = null;
        while (cur != end) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        start.next = end;
    }

    public static ListNode getKGroupEnd(ListNode listNode, int k) {
        while (--k != 0 && listNode != null) {
            listNode = listNode.next;
        }
        return listNode;
    }

    static class ListNode {
        int value;
        ListNode next;

        public ListNode() {
        }

        public ListNode(int value) {
            this.value = value;
        }

        public ListNode(int value, ListNode next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "value=" + value +
                    '}';
        }
    }
}

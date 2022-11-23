package com.serendipity.leetcode.easy;

/**
 * 合并两个有序链表
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 */
public class LeetCode21 {
    public static void main(String[] args) {
        String str1 = "1223334567";
        String str2 = "02334455588";
        ListNode node1 = new ListNode(Integer.valueOf(String.valueOf(str1.charAt(0))));
        ListNode tmp = node1;
        for (int i = 1; i < str1.length(); i++) {
            ListNode node = new ListNode(Integer.valueOf(String.valueOf(str1.charAt(i))));
            tmp.next = node;
            tmp = tmp.next;
        }
        tmp = node1;
        while (tmp != null) {
            System.out.println(tmp.toString());
            tmp = tmp.next;
        }
        ListNode node2 = new ListNode(Integer.valueOf(String.valueOf(str2.charAt(0))));
        tmp = node2;
        for (int i = 1; i < str2.length(); i++) {
            ListNode node = new ListNode(Integer.valueOf(String.valueOf(str2.charAt(i))));
            tmp.next = node;
            tmp = tmp.next;
        }
        tmp = node2;
        while (tmp != null) {
            System.out.println(tmp.toString());
            tmp = tmp.next;
        }

        System.out.println("=================");
        ListNode result = mergeTwoSortedListNode(node1, node2);
        tmp = result;
        while (tmp != null) {
            System.out.println(tmp.toString());
            tmp = tmp.next;
        }
    }

    private static ListNode mergeTwoSortedListNode(ListNode list1, ListNode list2) {
        if (list1 == null || list2 == null) {
            return list1 == null ? list2 : list1;
        }

        ListNode head = list1.val <= list2.val ? list1 : list2;
        ListNode cur1 = head.next;
        ListNode cur2 = head == list1 ? list2 : list1;
        ListNode pre = head;
        while (cur1 != null && cur2 != null) {
            if (cur1.val <= cur2.val) {
                pre.next = cur1;
                cur1 = cur1.next;
            } else {
                pre.next = cur2;
                cur2 = cur2.next;
            }
            pre = pre.next;
        }
        pre.next = cur1 != null ? cur1 : cur2;
        return head;
    }

    static class ListNode {
        int val;
        ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    '}';
        }
    }
}

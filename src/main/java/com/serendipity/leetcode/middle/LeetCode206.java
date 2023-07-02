package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 反转链表
 *              给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 * @date 2023/07/02/15:20
 */
public class LeetCode206 {

    public static void main(String[] args) {

    }

    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode pre = null;
        ListNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}

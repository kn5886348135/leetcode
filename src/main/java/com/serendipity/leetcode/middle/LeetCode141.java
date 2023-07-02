package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 环形链表
 *              给你一个链表的头节点 head ，判断链表中是否有环。
 * @date 2023/07/02/13:29
 */
public class LeetCode141 {

    public static void main(String[] args) {

    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null) {
            fast = fast.next;
            if (fast != null) {
                fast = fast.next;
            }
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}

package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 链表的中间结点
 *              给你单链表的头结点 head ，请你找出并返回链表的中间结点。
 *
 *              如果有两个中间结点，则返回第二个中间结点。
 * @date 2023/06/28/18:07
 */
public class LeetCode876 {

    public static void main(String[] args) {

    }

    // 快慢指针
    // 上中点
    // 下中点，可以让fast先走一步
    public ListNode middleNode(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        // ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // return slow.next;
        return slow;
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

}



package com.serendipity.linkedlist;

public class DeleteGivenValue {

    public static void main(String[] args) {
    }

    private static ListNode deleteTargetNode(ListNode node, int target) {
        if (node == null) {
            return null;
        }
        // 找到第一个不需要删除的节点
        while (node.val != target) {
            node = node.next;
        }
        ListNode pre = node;
        ListNode cur = node;
        // 整个链表都是target的时候，node可能为空
        while (cur != null) {
            if (cur.val == target) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return node;
    }

    static class ListNode{
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    // head = removeValue(head, 2);
    public static Node removeValue(Node head, int num) {
        // head来到第一个不需要删的位置
        while (head != null) {
            if (head.value != num) {
                break;
            }
            head = head.next;
        }
        // 1 ) head == null
        // 2 ) head != null
        Node pre = head;
        Node cur = head;
        while (cur != null) {
            if (cur.value == num) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    // TODO 双链表删除节点
}

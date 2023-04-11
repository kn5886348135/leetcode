package com.serendipity.algo3linkedlist;

import com.serendipity.common.CommonLinkedListUtil;
import com.serendipity.common.Node;

import java.util.ArrayList;

/**
 * @author jack
 * @version 1.0
 * @description 快慢指针找链表的中点
 * @date 2022/12/22/18:09
 */
public class LinkedList3FastSlowPointer {

    public static void main(String[] args) {
        int maxSize = 30;
        int maxValue = 200;
        int testTimes = 50000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            Node node = CommonLinkedListUtil.generateRandomLinkedList(maxSize, maxValue);

            Node ans1 = midOrUpMidNode(node);
            Node ans2 = verifyMidOrUpMidNode(node);
            if (ans1 != ans2) {
                System.out.println("midOrUpMidNode failed");
                success = false;
                break;
            }

            ans1 = midOrDownMidNode(node);
            ans2 = verifyMidOrDownMidNode(node);
            if (ans1 != ans2) {
                System.out.println("midOrDownMidNode failed");
                success = false;
                break;
            }

            ans1 = midOrUpMidPreNode(node);
            ans2 = verifyMidOrUpMidPreNode(node);
            if (ans1 != ans2) {
                System.out.println("midOrUpMidPreNode failed");
                success = false;
                break;
            }

            ans1 = midOrDownMidPreNode(node);
            ans2 = verifyMidOrDownMidPreNode(node);
            if (ans1 != ans2) {
                System.out.println("midOrDownMidPreNode failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "false");
    }

    // head 头
    // 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
    public static Node midOrUpMidNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        // 链表有3个点或以上
        Node slow = head.next;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
    public static Node midOrDownMidNode(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node slow = head.next;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // 输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
    public static Node midOrUpMidPreNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
    public static Node midOrDownMidPreNode(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        if (head.next.next == null) {
            return head;
        }
        Node slow = head;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static Node verifyMidOrUpMidNode(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 1) / 2);
    }

    public static Node verifyMidOrDownMidNode(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get(arr.size() / 2);
    }

    public static Node verifyMidOrUpMidPreNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 3) / 2);
    }

    public static Node verifyMidOrDownMidPreNode(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 2) / 2);
    }
}

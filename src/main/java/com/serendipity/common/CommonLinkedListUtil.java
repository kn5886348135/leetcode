package com.serendipity.common;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/04/06/18:11
 */
public class CommonLinkedListUtil {

    // 生成单链表
    public static Node generateRandomLinkedList(int maxLen, int maxValue) {
        int size = (int) (Math.random() * (maxLen + 1));
        while (size == 0) {
            size = (int) (Math.random() * (maxLen + 1));
        }
        size--;
        Node head = new Node((int) (Math.random() * (maxValue + 1)));
        Node pre = head;
        while (size != 0) {
            Node cur = new Node((int) (Math.random() * (maxValue + 1)));
            pre.next = cur;
            pre = cur;
            size--;
        }
        return head;
    }

    // 生成双链表
    public static DoubleNode generateRandomDoubleList(int len, int value) {
        int size = (int) (Math.random() * (len + 1));
        if (size == 0) {
            return null;
        }
        size--;
        DoubleNode head = new DoubleNode((int) (Math.random() * (value + 1)));
        DoubleNode pre = head;
        while (size != 0) {
            DoubleNode cur = new DoubleNode((int) (Math.random() * (value + 1)));
            pre.next = cur;
            cur.last = pre;
            pre = cur;
            size--;
        }
        return head;
    }

    // 复制链表
    public static Node copySingleNode(Node node) {
        if (node == null) {
            return null;
        }
        Node res = new Node(node.value);
        Node pre = res;
        while (node != null && node.next != null) {
            pre.next = new Node(node.next.value);
            pre = pre.next;
            node = node.next;
        }
        return res;
    }

    // 复制双链表
    public static DoubleNode copyDoubleNode(DoubleNode node) {
        if (node == null) {
            return null;
        }
        DoubleNode res = new DoubleNode(node.value);
        DoubleNode pre = res;
        while (node != null && node.next != null) {
            DoubleNode cur = new DoubleNode(node.next.value);
            pre.next = cur;
            cur.last = pre;
            pre = pre.next;
            node = node.next;
        }
        return res;
    }

    // 打印链表
    public static void printSingleNode(Node node) {
        Node cur = copySingleNode(node);
        while (cur != null) {
            System.out.print(cur.value + "\t");
            cur = cur.next;
        }
        System.out.println();
    }

    // 打印链表
    public static boolean sameSingleNode(Node node1, Node node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        Node cur1 = copySingleNode(node1);
        Node cur2 = copySingleNode(node2);
        while (cur1 != null && cur2 != null) {
            if (cur1.value.equals(cur2.value)) {
                return false;
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        // 都遍历完了
        if ((cur1 == null && cur2 != null) || (cur1 != null && cur2 == null)) {
            return false;
        }
        return true;
    }
}

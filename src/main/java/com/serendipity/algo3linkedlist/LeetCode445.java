package com.serendipity.algo3linkedlist;

import com.serendipity.common.CommonLinkedListUtil;
import com.serendipity.common.Node;

/**
 * @author jack
 * @version 1.0
 * @description 给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
 *              你可以假设除了数字 0 之外，这两个数字都不会以零开头。
 * @date 2023/04/06/15:11
 */
public class LeetCode445 {

    public static void main(String[] args) {
        int maxSize = 9;
        int maxValue = 9;
        int testTimes = 5000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            Node<Integer> node1 = CommonLinkedListUtil.generateRandomLinkedList(maxSize, maxValue);

            // 确保链表头部不为0
            if (node1.value.equals(0)) {
                int value = (int) Math.random() * maxValue + 1;
                while (value == 0 || value > 9) {
                    value = (int) Math.random() * maxValue + 1;
                }
                node1.value = value;
            }

            Node<Integer> node2 = CommonLinkedListUtil.generateRandomLinkedList(maxSize, maxValue);

            // 确保链表头部不为0
            if (node2.value.equals(0)) {
                int value = (int) Math.random() * maxValue + 1;
                while (value == 0 || value > 9) {
                    value = (int) Math.random() * maxValue + 1;
                }
                node2.value = value;
            }

            Node<Integer> node3 = CommonLinkedListUtil.copySingleNode(node1);
            Node<Integer> node4 = CommonLinkedListUtil.copySingleNode(node2);
            Node<Integer> ans1 = addTwoNumbers(node1, node2);

            Node<Integer> ans2 = verifyAddTwoNumbers(node3, node4);
            if (!CommonLinkedListUtil.sameSingleNode(ans1, ans2)) {
                System.out.println("node1====================");
                CommonLinkedListUtil.printSingleNode(node1);
                System.out.println("node2====================");
                CommonLinkedListUtil.printSingleNode(node2);
                System.out.println("ans1====================");
                CommonLinkedListUtil.printSingleNode(ans1);
                System.out.println("ans2====================");
                CommonLinkedListUtil.printSingleNode(ans2);
                System.out.println("addTwoNumbers failed");
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "false");
    }

    public static Node<Integer> addTwoNumbers(Node<Integer> l1, Node<Integer> l2) {
        int len1 = getListNodeLength(l1);
        int len2 = getListNodeLength(l2);

        // 翻转链表，同LeeTcode2
        Node<Integer> head1 = reverseSingleNode(l1);
        Node<Integer> head2 = reverseSingleNode(l2);
        Node<Integer> longList = len1 > len2 ? head1 : head2;
        Node<Integer> shortList = longList == head1 ? head2 : head1;
        Node<Integer> cur1 = longList;
        Node<Integer> cur2 = shortList;
        Node<Integer> last = longList;

        int sum = 0;
        while (cur2 != null) {
            sum = sum + cur1.value + cur2.value;
            cur1.value = sum % 10;
            sum = sum / 10;
            last = cur1;
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        while (cur1 != null) {
            sum = sum + cur1.value;
            cur1.value = sum % 10;
            sum = sum / 10;
            last = cur1;
            cur1 = cur1.next;
        }
        if (sum != 0) {
            last.next = new Node<>(sum);
        }
        return reverseSingleNode(longList);
    }

    // 翻转链表
    private static Node<Integer> reverseSingleNode(Node<Integer> node) {
        Node<Integer> pre = null;
        Node<Integer> next = null;
        while (node != null) {
            next = node.next;
            node.next = pre;
            pre = node;
            node = next;
        }
        return pre;
    }

    private static int getListNodeLength(Node<Integer> node) {
        int ans = 0;
        while (node != null) {
            ans++;
            node = node.next;
        }
        return ans;
    }

    // 对数器
    public static Node<Integer> verifyAddTwoNumbers(Node<Integer> node1, Node<Integer> node2) {
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        while (node1 != null) {
            str1.append(node1.value);
            node1 = node1.next;
        }
        while (node2 != null) {
            str2.append(node2.value);
            node2 = node2.next;
        }
        int sum = Integer.parseInt(str1.toString()) + Integer.parseInt(str2.toString());
        String str = String.valueOf(sum);
        Node<Integer> head = null;
        Node<Integer> node = null;
        int len = 0;
        while (len < str.length()) {
            Node<Integer> cur = new Node(str.charAt(len));
            if (node == null) {
                node = cur;
                head = cur;
            } else {
                node.next = cur;
                node = node.next;
            }
            len++;
        }
        return head;
    }
}

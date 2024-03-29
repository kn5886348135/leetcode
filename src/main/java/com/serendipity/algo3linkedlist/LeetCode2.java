package com.serendipity.algo3linkedlist;

import com.serendipity.common.CommonLinkedListUtil;
import com.serendipity.common.Node;

/**
 * @author jack
 * @version 1.0
 * @description 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 *              请你将两个数相加，并以相同形式返回一个表示和的链表。
 *              你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * @date 2023/04/07/0:12
 */
public class LeetCode2 {

    public static void main(String[] args) {
        int maxSize = 9;
        int maxValue = 9;
        int testTimes = 5000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            Node<Integer> node1 = CommonLinkedListUtil.generateRandomLinkedList(maxSize, maxValue);

            // 确保链表尾部不为0
            Node<Integer> tmp = node1;
            while (tmp != null) {
                if (tmp.next == null && tmp.value.equals(0)) {
                    int value = (int) Math.random() * maxValue + 1;
                    while (value == 0 || value > 9) {
                        value = (int) Math.random() * maxValue + 1;
                    }
                    tmp.value = value;
                }
                tmp = tmp.next;
            }

            Node<Integer> node2 = CommonLinkedListUtil.generateRandomLinkedList(maxSize, maxValue);

            // 确保链表尾部不为0
            tmp = node2;
            while (tmp != null) {
                if (tmp.next == null && tmp.value.equals(0)) {
                    int value = (int) Math.random() * maxValue + 1;
                    while (value == 0 || value > 9) {
                        value = (int) Math.random() * maxValue + 1;
                    }
                    tmp.value = value;
                }
                tmp = tmp.next;
            }


            Node<Integer> node3 = CommonLinkedListUtil.copySingleNode(node1);
            Node<Integer> node4 = CommonLinkedListUtil.copySingleNode(node2);
            Node<Integer> ans1 = addTwoNumbers1(node1, node2);

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

    // 获取两个链表长度
    // 先处理短链表，再处理长链表剩余部分，最后处理进位
    public static Node<Integer> addTwoNumbers1(Node<Integer> head1, Node<Integer> head2) {
        int len1 = getListNodeLength(head1);
        int len2 = getListNodeLength(head2);
        Node<Integer> longList = len1 > len2 ? head1 : head2;
        Node<Integer> shortList = longList == head1 ? head2 : head1;
        Node<Integer> cur1 = longList;
        Node<Integer> cur2 = shortList;
        Node<Integer> last = cur1;

        int sum = 0;
        int curNum = 0;
        while (cur2 != null) {
            curNum = cur1.value + cur2.value + sum;
            cur1.value = curNum % 10;
            sum = curNum / 10;
            last = cur1;
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        while (cur1 != null) {
            curNum = cur1.value + sum;
            cur1.value = curNum % 10;
            sum = curNum / 10;
            last = cur1;
            cur1 = cur1.next;
        }
        if (sum != 0) {
            last.next = new Node<>(1);
        }
        return longList;
    }

    private static int getListNodeLength(Node<Integer> node) {
        int ans = 0;
        while (node != null) {
            ans++;
            node = node.next;
        }
        return ans;
    }

    public Node addTwoNumbers2(Node<Integer> l1, Node<Integer> l2) {
        Node pre = new Node(0);
        Node cur = pre;
        int carry = 0;
        while(l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.value;
            int y = l2 == null ? 0 : l2.value;
            int sum = x + y + carry;

            carry = sum / 10;
            sum = sum % 10;
            cur.next = new Node(sum);

            cur = cur.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if(carry == 1) {
            cur.next = new Node(carry);
        }
        return pre.next;
    }

    // 递归
    public Node addTwoNumbers3(Node<Integer> l1, Node<Integer> l2) {
        // 哨兵节点
        Node dummy = new Node(0);
        Node cur = dummy;
        int carry = 0;
        // 有一个不是空节点，或者还有进位，就继续迭代
        while (l1 != null || l2 != null || carry != 0) {
            if (l1 != null) {
                // 节点值和进位加在一起
                carry += l1.value;
            }
            if (l2 != null) {
                // 节点值和进位加在一起
                carry += l2.value;
            }
            // 每个节点保存一个数位
            cur = cur.next = new Node(carry % 10);
            // 新的进位
            carry /= 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        // 哨兵节点的下一个节点就是头节点
        return dummy.next;
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
        int sum = Integer.parseInt(str1.reverse().toString()) + Integer.parseInt(str2.reverse().toString());
        String str = new StringBuffer(String.valueOf(sum)).reverse().toString();
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

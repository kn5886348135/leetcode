package com.serendipity.algo3linkedlist;

import com.serendipity.common.CommonLinkedListUtil;
import com.serendipity.common.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 检测是否回文链表
 * @date 2022/12/22/17:53
 */
public class LinkedList4IsPalindromeList {

    public static void main(String[] args) {
        int maxSize = 30;
        int maxValue = 200;
        int testTimes = 50000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            Node node = generatePalindromeNode(maxSize, maxValue);
            Node head = CommonLinkedListUtil.copySingleNode(node);
            boolean ans1 = isPalindrome1(head);
            boolean ans2 = isPalindrome2(head);
            boolean ans3 = isPalindrome3(head);
            boolean ans4 = isPalindrome4(head);
            Set set = new HashSet();
            set.add(ans1);
            set.add(ans2);
            set.add(ans3);
            set.add(ans4);
            if (set.size() > 1) {
                System.out.println("isPalindrome failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // need n extra space
    // 辅助栈
    public static boolean isPalindrome1(Node head) {
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    // need n/2 extra space
    // 双指针，将中点后的单链表入栈，然后遍历单链表和栈
    public static boolean isPalindrome2(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node right = head.next;
        Node cur = head;
        while (cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }
        Stack<Node> stack = new Stack<>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }
        while (!stack.isEmpty()) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    // need O(1) extra space
    // 将链表的后半部分指针逆向，分别从头尾遍历对比，最后还原逆向的指针
    public static boolean isPalindrome3(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        // 拿到中点
        Node n1 = head;
        Node n2 = head;
        while (n2.next != null && n2.next.next != null) {
            // n1 -> mid
            n1 = n1.next;
            // n2 -> end
            n2 = n2.next.next;
        }

        // n2 右边第一个节点
        n2 = n1.next;
        // mid.next -> null 断开链表
        n1.next = null;
        Node n3 = null;
        // 右边逆序
        while (n2 != null) {
            // 保存右边第一个节点的next
            n3 = n2.next;
            // 右边第一个节点指向中点(尾结点)
            n2.next = n1;
            n1 = n2;
            n2 = n3;
        }
        // n3从null改为尾结点n1
        n3 = n1;
        // n2指向头结点
        n2 = head;
        boolean res = true;
        // 对向遍历单链表
        // 只遍历一半
        // while (n1.next != n2 && n1 != n2) { }
        // 遍历整个链表
        while (n1 != null && n2 != null) {
            if (n1.value != n2.value) {
                res = false;
                break;
            }
            n1 = n1.next;
            n2 = n2.next;
        }

        // 还原链表
        // n1指向尾结点的前一个
        n1 = n3.next;
        n3.next = null;
        // 还原链表，中点的next指向null，递归终止
        while (n1 != null) {
            n2 = n1.next;
            n1.next = n3;
            n3 = n1;
            n1 = n2;
        }
        return res;
    }

    // 用map做辅助
    public static boolean isPalindrome4(Node<Integer> head) {
        int index = 1;
        Map<Integer, Integer> map = new HashMap<>();
        while (head != null) {
            map.put(index, head.value);
            head = head.next;
            index++;
        }
        int left = 1;
        int right = map.size();
        while (left <= right) {
            if (!map.get(left).equals(map.get(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // 随机生成链表，链表长度随机为奇数偶数，大概率是回文链表
    public static Node generatePalindromeNode(int maxSize, int maxValue) {
        Node node = CommonLinkedListUtil.generateRandomLinkedList(maxSize, maxValue);
        Node head = CommonLinkedListUtil.copySingleNode(node);
        Node cur = head;
        Stack<Node> stack = new Stack<>();
        Node tmp = null;
        while (node != null) {
            tmp = node.next;
            node.next = null;
            stack.push(node);
            node = tmp;
            if (cur.next != null) {
                cur = cur.next;
            }
        }

        // 随机增加一个节点变成
        if (((int) (Math.random() * 5)) == 2) {
            Node mid = new Node(1);
            cur.next = mid;
            cur = mid;
        }
        while (!stack.isEmpty()) {
            cur.next = stack.pop();
            cur = cur.next;
        }
        // 随机破坏回文结构
        if (((int) (Math.random() * 10)) == 5) {
            cur.value = Integer.MAX_VALUE;
        }
        return head;
    }
}

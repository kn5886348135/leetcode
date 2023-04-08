package com.serendipity.algo4sort;

import java.util.ArrayList;

/**
 * @author jack
 * @version 1.0
 * @description 双向链表的随机快排
 * @date 2023/03/29/21:57
 */
public class DoubleLinkedListQuickSort {

    public static void main(String[] args) {
        int len = 500;
        int value = 500;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            int size = (int) (Math.random() * len);
            Node head1 = generateRandomDoubleLinkedList(size, value);
            Node head2 = cloneDoubleLinkedList(head1);
            Node sort1 = quickSort(head1);
            Node sort2 = sort(head2);
            if (!equal(sort1, sort2)) {
                System.out.println("出错了!");
                break;
            }
        }
    }

    public static class Node {
        public int value;
        public Node last;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node quickSort(Node node) {
        if (node == null) {
            return null;
        }
        int len = 0;
        Node c = node;
        Node e = null;
        while (c != null) {
            len++;
            e = c;
            c = c.next;
        }
        return process(node, e, len).head;
    }

    public static class HeadTail {
        public Node head;
        public Node tail;

        public HeadTail(Node head, Node tail) {
            this.head = head;
            this.tail = tail;
        }
    }

    // left...right是一个双向链表的头和尾，长度为len
    // left的last指针指向null，left左边没有节点，right的next指针指向null，right右边没有节点
    // 返回排好序之后的双向链表的头和尾(HeadTail)
    public static HeadTail process(Node left, Node right, int len) {
        if (left == null) {
            return null;
        }
        if (left == right) {
            return new HeadTail(left, right);
        }
        // left...right不止一个节点，拿到一个随机节点
        int randomIndex = (int) (Math.random() * len);
        // 根据随机下标得到随机节点
        Node randomNode = left;
        while (randomIndex-- != 0) {
            randomNode = randomNode.next;
        }
        // 把随机节点从原来的环境里分离出来
        // 比如 a(L) -> b -> c -> d(R), 如果randomNode = c，那么调整之后
        // a(L) -> b -> d(R), c会被挖出来，randomNode = c
        if (randomNode == left || randomNode == right) {
            if (randomNode == left) {
                left = randomNode.next;
                left.last = null;
            } else {
                randomNode.last.next = null;
            }
        } else {
            // randomNode一定是中间的节点
            randomNode.last.next = randomNode.next;
            randomNode.next.last = randomNode.last;
        }
        randomNode.last = null;
        randomNode.next = null;
        Info info = partition(left, randomNode);
        // <randomNode的部分去排序
        HeadTail lht = process(info.leftHead, info.leftTail, info.leftSize);
        // >randomNode的部分去排序
        HeadTail rht = process(info.rightHead, info.rightTail, info.rightSize);
        // 左部分排好序、右部分排好序
        // 把它们串在一起
        if (lht != null) {
            lht.tail.next = info.equalHead;
            info.equalHead.last = lht.tail;
        }
        if (rht != null) {
            info.equalTail.next = rht.head;
            rht.head.last = info.equalTail;
        }
        // 返回排好序之后总的头和总的尾
        Node head = lht != null ? lht.head : info.equalHead;
        Node tail = rht != null ? rht.tail : info.equalTail;
        return new HeadTail(head, tail);
    }

    public static class Info {
        public Node leftHead;
        public Node leftTail;
        public int leftSize;
        public Node rightHead;
        public Node rightTail;
        public int rightSize;
        public Node equalHead;
        public Node equalTail;

        public Info(Node leftHead, Node leftTail, int leftSize, Node rightHead, Node rightTail, int rightSize, Node equalHead, Node equalTail) {
            this.leftHead = leftHead;
            this.leftTail = leftTail;
            this.leftSize = leftSize;
            this.rightHead = rightHead;
            this.rightTail = rightTail;
            this.rightSize = rightSize;
            this.equalHead = equalHead;
            this.equalTail = equalTail;
        }
    }

    // (L....一直到空)，是一个双向链表
    // pivot是一个不在(L....一直到空)的独立节点，它作为划分值
    // 根据荷兰国旗问题的划分方式，把(L....一直到空)划分成:
    // <pivot 、 =pivot 、 >pivot 三个部分，然后把pivot融进=pivot的部分
    // 比如 4(L)->6->7->1->5->0->9->null pivot=5(这个5和链表中的5，是不同的节点)
    // 调整完成后:
    // 4->1->0 小于的部分
    // 5->5 等于的部分
    // 6->7->9 大于的部分
    // 三个部分是断开的
    // 然后返回Info：
    // 小于部分的头、尾、节点个数 : lh,lt,ls
    // 大于部分的头、尾、节点个数 : rh,rt,rs
    // 等于部分的头、尾 : eh,et
    public static Info partition(Node left, Node pivot) {
        Node leftHead = null;
        Node leftTail = null;
        int leftSize = 0;
        Node rightHead = null;
        Node rightTail = null;
        int rightSize = 0;
        Node equalHead = pivot;
        Node equalTail = pivot;
        Node tmp = null;
        while (left != null) {
            tmp = left.next;
            left.next = null;
            left.last = null;
            if (left.value < pivot.value) {
                leftSize++;
                if (leftHead == null) {
                    leftHead = left;
                    leftTail = left;
                } else {
                    leftTail.next = left;
                    left.last = leftTail;
                    leftTail = left;
                }
            } else if (left.value > pivot.value) {
                rightSize++;
                if (rightHead == null) {
                    rightHead = left;
                    rightTail = left;
                } else {
                    rightTail.next = left;
                    left.last = rightTail;
                    rightTail = left;
                }
            } else {
                equalTail.next = left;
                left.last = equalTail;
                equalTail = left;
            }
            left = tmp;
        }
        return new Info(leftHead, leftTail, leftSize, rightHead, rightTail, rightSize, equalHead, equalTail);
    }

    public static Node sort(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        while (head != null) {
            arr.add(head);
            head = head.next;
        }
        arr.sort((o1, o2) -> o1.value - o2.value);
        Node h = arr.get(0);
        h.last = null;
        Node p = h;
        for (int i = 1; i < arr.size(); i++) {
            Node c = arr.get(i);
            p.next = c;
            c.last = p;
            c.next = null;
            p = c;
        }
        return h;
    }

    public static Node generateRandomDoubleLinkedList(int n, int v) {
        if (n == 0) {
            return null;
        }
        Node[] arr = new Node[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new Node((int) (Math.random() * v));
        }
        Node head = arr[0];
        Node pre = head;
        for (int i = 1; i < n; i++) {
            pre.next = arr[i];
            arr[i].last = pre;
            pre = arr[i];
        }
        return head;
    }

    public static Node cloneDoubleLinkedList(Node head) {
        if (head == null) {
            return null;
        }
        Node h = new Node(head.value);
        Node p = h;
        head = head.next;
        while (head != null) {
            Node c = new Node(head.value);
            p.next = c;
            c.last = p;
            p = c;
            head = head.next;
        }
        return h;
    }

    public static boolean equal(Node h1, Node h2) {
        return doubleLinkedListToString(h1).equals(doubleLinkedListToString(h2));
    }

    public static String doubleLinkedListToString(Node head) {
        Node cur = head;
        Node end = null;
        StringBuilder builder = new StringBuilder();
        while (cur != null) {
            builder.append(cur.value + " ");
            end = cur;
            cur = cur.next;
        }
        builder.append("| ");
        while (end != null) {
            builder.append(end.value + " ");
            end = end.last;
        }
        return builder.toString();
    }
}

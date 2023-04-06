package com.serendipity.algo3linkedlist;

public class LinkedList {
    private static Node head = new Node(0);

    public static void main(String[] args) {
        Node tmp = head;
        for (int i = 0; i < 5; i++) {
            Node node = new Node(i + 1);
            tmp.setNext(node);
            tmp = node;
        }

        tmp = head;
        while (tmp != null) {
            System.out.println(tmp);
            tmp = tmp.getNext();
        }
        System.out.println("=========================");

        head = reverseLinkedList(head);
        tmp = head;
        while (tmp != null) {
            System.out.println(tmp);
            tmp = tmp.getNext();
        }
    }

    private static Node reverseLinkedList(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.getNext();
            head.setNext(pre);
            pre = head;
            head = next;
        }
        return pre;
    }

}

package com.serendipity.algo3linkedlist;

import java.util.Objects;

public class DoubleLinkedList {


    public static void main(String[] args) {

    }

    private static Node reverDoubleLinkedList(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.getLast();
            head.setLast(pre);
            head.setPre(next);
            pre = head;
            head = next;
        }
        return pre;
    }

    private class Node {
        private int a;
        private Node pre;
        private Node last;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public Node getPre() {
            return pre;
        }

        public void setPre(Node pre) {
            this.pre = pre;
        }

        public Node getLast() {
            return last;
        }

        public void setLast(Node last) {
            this.last = last;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return a == node.a &&
                    Objects.equals(pre, node.pre) &&
                    Objects.equals(last, node.last);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, pre, last);
        }
    }
}

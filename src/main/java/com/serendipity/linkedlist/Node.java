package com.serendipity.linkedlist;

import java.util.Objects;

public class Node {
    private int a;

    private Node next;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node(int a) {
        this.a = a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return a == node.a;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a);
    }

    @Override
    public String toString() {
        return "Node{" +
                "a=" + a +
                '}';
    }
}

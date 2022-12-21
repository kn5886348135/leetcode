package com.serendipity.linkedlist;

import java.util.Objects;

public class CustomQueue<V> {

    private Node<V> head;
    private Node<V> tail;
    private int size;

    public boolean isEmpty(){
        return size == 0;
    }

    public void offer(V value) {
        Node<V> node = new Node<>(value);
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            tail.setNext(node);
            tail = node;
        }
        size++;
    }

    public V poll(){
        if (head == null) {
            return null;
        }
        V ans = head.getValue();
        head = head.getNext();
        size--;
        return ans;
    }

    public V peek() {
        if (head == null) {
            return null;
        }
        return head.getValue();
    }

    private class Node<V> {
        private V value;
        private Node<V> next;

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Node<V> getNext() {
            return next;
        }

        public void setNext(Node<V> next) {
            this.next = next;
        }

        public Node(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(value, node.value) &&
                    Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, next);
        }
    }
}

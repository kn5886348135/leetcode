package com.serendipity.linkedlist;

import java.util.Objects;

public class CustomDeque<V> {
    private Node<V> head;
    private Node<V> tail;
    private int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public void pushHead(V value){
        Node<V> node = new Node<>(value);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.setLast(head);
            head.setPre(node);
            head = node;
        }
        size++;
    }

    public void pushTail(V value){
        Node<V> node = new Node<>(value);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.setLast(node);
            node.setPre(tail);
            tail = node;
        }
        size++;
    }

    public V pollHead(){
        if (head == null) {
            return null;
        }
        V ans = head.getValue();
        if (head.equals(tail)) {
            head = null;
            tail = null;
        } else {
            head = head.getLast();
            head.setPre(null);
        }
        size--;
        return ans;
    }

    public V pollTail(){
        if (tail == null) {
            return null;
        }
        V ans = tail.getValue();
        if (head.equals(tail)) {
            head = null;
            tail = null;
        } else {
            tail = tail.getPre();
            tail.setLast(null);
        }
        size--;
        return ans;
    }

    public V peekHead(){
        return head == null ? null : head.getValue();
    }

    public V peekTail(){
        return tail == null ? null : tail.getValue();
    }

    class Node<V>{
        private V value;
        private Node<V> last;
        private Node<V> pre;

        public Node(V value) {
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Node<V> getLast() {
            return last;
        }

        public void setLast(Node<V> last) {
            this.last = last;
        }

        public Node<V> getPre() {
            return pre;
        }

        public void setPre(Node<V> pre) {
            this.pre = pre;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}

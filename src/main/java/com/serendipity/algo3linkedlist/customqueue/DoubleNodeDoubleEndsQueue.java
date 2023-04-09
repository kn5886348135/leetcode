package com.serendipity.algo3linkedlist.customqueue;

import com.serendipity.common.DoubleNode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 双链表实现双端队列
 * @date 2023/04/06/23:26
 */
public class DoubleNodeDoubleEndsQueue {

    public static void main(String[] args) {
        int oneTestDataNum = 100;
        int value = 10000;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            DoubleEndsQueueStack<Integer> doubleEndsQueueStack = new DoubleEndsQueueStack<>();
            DoubleEndsQueueQueue<Integer> doubleEndsQueueQueue = new DoubleEndsQueueQueue<>();
            Stack<Integer> stack = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < oneTestDataNum; j++) {
                int nums = (int) (Math.random() * value);
                if (stack.isEmpty()) {
                    doubleEndsQueueStack.push(nums);
                    stack.push(nums);
                } else {
                    if (Math.random() < 0.5) {
                        doubleEndsQueueStack.push(nums);
                        stack.push(nums);
                    } else {
                        if (!doubleEndsQueueStack.pop().equals(stack.pop())) {
                            System.out.println("oops!");
                        }
                    }
                }
                int numq = (int) (Math.random() * value);
                if (queue.isEmpty()) {
                    doubleEndsQueueQueue.push(numq);
                    queue.offer(numq);
                } else {
                    if (Math.random() < 0.5) {
                        doubleEndsQueueQueue.push(numq);
                        queue.offer(numq);
                    } else {
                        if (!doubleEndsQueueQueue.poll().equals(queue.poll())) {
                            System.out.println("oops!");
                        }
                    }
                }
            }
        }
    }

    public static class DoubleEndsQueue<T> {
        public DoubleNode<T> head;
        public DoubleNode<T> tail;

        public void addFromHead(T value) {
            DoubleNode<T> cur = new DoubleNode<>(value);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                cur.next = head;
                head.last = cur;
                head = cur;
            }
        }

        public void addFromBottom(T value) {
            DoubleNode<T> cur = new DoubleNode<>(value);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                cur.last = tail;
                tail.next = cur;
                tail = cur;
            }
        }

        public T popFromHead() {
            if (head == null) {
                return null;
            }
            DoubleNode<T> cur = head;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                cur.next = null;
                head.last = null;
            }
            return cur.value;
        }

        public T popFromBottom() {
            if (head == null) {
                return null;
            }
            DoubleNode<T> cur = tail;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                tail = tail.last;
                tail.next = null;
                cur.last = null;
            }
            return cur.value;
        }

        public boolean isEmpty() {
            return head == null;
        }

    }

    public static class DoubleEndsQueueStack<T> {
        private DoubleEndsQueue<T> queue;

        public DoubleEndsQueueStack() {
            queue = new DoubleEndsQueue<T>();
        }

        public void push(T value) {
            queue.addFromHead(value);
        }

        public T pop() {
            return queue.popFromHead();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

    }

    public static class DoubleEndsQueueQueue<T> {
        private DoubleEndsQueue<T> queue;

        public DoubleEndsQueueQueue() {
            queue = new DoubleEndsQueue<T>();
        }

        public void push(T value) {
            queue.addFromHead(value);
        }

        public T poll() {
            return queue.popFromBottom();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

    }

}

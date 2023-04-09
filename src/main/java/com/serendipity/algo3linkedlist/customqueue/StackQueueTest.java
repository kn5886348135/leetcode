package com.serendipity.algo3linkedlist.customqueue;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/04/06/23:42
 */
public class StackQueueTest {

    public static void main(String[] args) {
        StackQueue test = new StackQueue();
        test.offer(1);
        test.offer(2);
        test.offer(3);
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
    }
}

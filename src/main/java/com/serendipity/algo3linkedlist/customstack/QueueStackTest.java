package com.serendipity.algo3linkedlist.customstack;

import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/04/06/23:47
 */
public class QueueStackTest {

    public static void main(String[] args) {
        QueueStack<Integer> queueStack = new QueueStack<>();
        Stack<Integer> test = new Stack<>();
        int testTime = 1000000;
        int max = 1000000;
        for (int i = 0; i < testTime; i++) {
            if (queueStack.isEmpty()) {
                if (!test.isEmpty()) {
                    System.out.println("Oops");
                }
                int num = (int) (Math.random() * max);
                queueStack.push(num);
                test.push(num);
            } else {
                if (Math.random() < 0.25) {
                    int num = (int) (Math.random() * max);
                    queueStack.push(num);
                    test.push(num);
                } else if (Math.random() < 0.5) {
                    if (!queueStack.peek().equals(test.peek())) {
                        System.out.println("Oops");
                    }
                } else if (Math.random() < 0.75) {
                    if (!queueStack.poll().equals(test.pop())) {
                        System.out.println("Oops");
                    }
                } else {
                    if (queueStack.isEmpty() != test.isEmpty()) {
                        System.out.println("Oops");
                    }
                }
            }
        }
    }
}

package com.serendipity.algo11dynamicprogramming.recursion1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 不使用额外空间，反转一个栈
 * @date 2022/12/19/15:35
 */
public class ReverseStackUsingRecursive {

    public static void main(String[] args) {
        // TODO 怎么避免OOM
        int maxSize = 10;
        int maxValue = 500;
        int testTimes = 10;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            Queue<Integer> queue = generateRandomQueue(maxSize, maxValue);
            Stack<Integer> stack = new Stack<>();
            while (!queue.isEmpty()) {
                stack.push(queue.peek());
            }
            reverse(stack);
            if (queue.size() != stack.size()) {
                System.out.println("reverse stack failed");
                success = false;
                break;
            }
            // stack翻转以后和queue的顺序一样
            while (!queue.isEmpty()) {
                if (!queue.poll().equals(stack.pop())) {
                    System.out.println("reverse stack failed");
                    success = false;
                    break;
                }
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    private static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }

        // 使用局部变量保存栈底元素
        int i = func(stack);
        reverse(stack);
        // 最后将栈底元素压栈
        stack.push(i);
    }

    // 将栈底元素移除并返回，其他元素按照原来顺序盖下来
    private static int func(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            int last = func(stack);
            stack.push(result);
            return last;
        }
    }

    private static Queue<Integer> generateRandomQueue(int maxSize, int maxValue) {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < maxSize; i++) {
            queue.add((int) Math.random() * maxValue + 1 - (int) Math.random() * maxValue + 1);
        }
        return queue;
    }

    public static void verifyReverse(Stack<Integer> stack) {
        reverse(stack);
        reverse(stack);
    }
}

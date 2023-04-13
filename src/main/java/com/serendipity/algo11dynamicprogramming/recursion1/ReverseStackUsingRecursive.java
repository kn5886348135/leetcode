package com.serendipity.algo11dynamicprogramming.recursion1;

import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 不使用额外空间，反转一个栈
 * @date 2022/12/19/15:35
 */
public class ReverseStackUsingRecursive {

    private static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }

        // 使用局部变量保存栈底元素
        int i = f(stack);
        reverse(stack);
        // 最后将栈底元素压栈
        stack.push(i);
    }

    // 将栈底元素移除并返回，其他元素按照原来顺序盖下来
    private static int f(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            int last = f(stack);
            stack.push(result);
            return last;
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6);
        stack.push(7);
        stack.push(8);
        reverse(stack);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}

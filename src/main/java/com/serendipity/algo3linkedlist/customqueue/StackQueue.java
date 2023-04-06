package com.serendipity.algo3linkedlist.customqueue;

import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 栈实现队列
 * @date 2023/04/06/23:35
 */
public class StackQueue<E> implements Queue<E> {

    public Stack<E> stackPush = new Stack<>();
    public Stack<E> stackPop = new Stack<>();

    // push栈向pop栈倒入数据
    private void pushToPop() {
        if (stackPop.empty()) {
            while (!stackPush.empty()) {
                stackPop.push(stackPush.pop());
            }
        }
    }

    @Override
    public boolean offer(E e) {
        stackPush.push(e);
        pushToPop();
        return false;
    }

    @Override
    public E poll() {
        if (stackPop.empty() && stackPush.empty()) {
            throw new RuntimeException("Queue is empty!");
        }
        pushToPop();
        return stackPop.pop();
    }

    @Override
    public E peek() {
        if (stackPop.empty() && stackPush.empty()) {
            throw new RuntimeException("Queue is empty!");
        }
        pushToPop();
        return stackPop.peek();
    }

}

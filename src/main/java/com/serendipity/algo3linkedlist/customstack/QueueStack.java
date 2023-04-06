package com.serendipity.algo3linkedlist.customstack;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author jack
 * @version 1.0
 * @description 队列实现栈
 * @date 2023/04/06/23:35
 */
public class QueueStack<E> implements Stack<E> {

    public Queue<E> queue = new LinkedList<>();
    public Queue<E> help = new LinkedList<>();

    @Override
    public void push(E item) {
        queue.offer(item);
    }

    @Override
    public E poll() {
        while (queue.size() > 1) {
            help.offer(queue.poll());
        }
        E ans = queue.poll();
        Queue<E> tmp = queue;
        queue = help;
        help = tmp;
        return ans;
    }

    @Override
    public E pop() {
        // TODO
        return null;
    }

    @Override
    public E peek() {
        while (queue.size() > 1) {
            help.offer(queue.poll());
        }
        E ans = queue.poll();
        help.offer(ans);
        Queue<E> tmp = queue;
        queue = help;
        help = tmp;
        return ans;
    }

    @Override
    public boolean empty() {
        // TODO
        return false;
    }

    @Override
    public int search(E item) {
        // TODO
        return 0;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

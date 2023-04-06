package com.serendipity.algo3linkedlist.customqueue;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/04/06/21:21
 */
public interface Queue<E> {

    // 加在队列尾部
    boolean offer(E e);

    // 移除队列头部元素并返回
    E poll();

    // 返回队列头部元素
    E peek();
}

package com.serendipity.algo3linkedlist.customstack;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/04/06/21:31
 */
public interface Stack<E> {

     void push(E item);

     E poll();

     E pop();

     E peek();

     boolean empty();

     int search(E item);

}

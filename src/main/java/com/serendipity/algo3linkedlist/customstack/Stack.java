package com.serendipity.algo3linkedlist.customstack;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/04/06/21:31
 */
public interface Stack<E> {

     E push(E item);

     E pop();

     E peek();

     boolean empty();

     int search(E item);

}

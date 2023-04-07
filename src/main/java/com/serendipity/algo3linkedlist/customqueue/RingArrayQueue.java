package com.serendipity.algo3linkedlist.customqueue;

/**
 * @author jack
 * @version 1.0
 * @description 环形数组实现队列
 * @date 2023/03/30/15:37
 */
public class RingArrayQueue {

    // 循环数组实现队列
    // 为什么要用环形数组
    // 不考虑扩容
    // begin、end小标的实现方式不太好
    // 考虑数组扩容应该怎么实现？

    private int[] arr;
    private int pushi;// end
    private int polli;// begin
    private int size;
    private final int limit;

    public RingArrayQueue(int limit) {
        arr = new int[limit];
        pushi = 0;
        polli = 0;
        size = 0;
        this.limit = limit;
    }

    public void push(int value) {
        if (size == limit) {
            throw new RuntimeException("队列满了，不能再加了");
        }
        size++;
        arr[pushi] = value;
        pushi = nextIndex(pushi);
    }

    public int pop() {
        if (size == 0) {
            throw new RuntimeException("队列空了，不能再拿了");
        }
        size--;
        int ans = arr[polli];
        polli = nextIndex(polli);
        return ans;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // 如果现在的下标是i，返回下一个位置
    private int nextIndex(int i) {
        return i < limit - 1 ? i + 1 : 0;
    }

}

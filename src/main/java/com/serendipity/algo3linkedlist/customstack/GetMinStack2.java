package com.serendipity.algo3linkedlist.customstack;

import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 自定义栈，要求pop、push、getMin操作的时间复杂度都是 O(1)。
 * @date 2023/03/30/16:01
 */
public class GetMinStack2 {

    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;

    public GetMinStack2() {
        this.stackData = new Stack<Integer>();
        this.stackMin = new Stack<Integer>();
    }

    public void push(int newNum) {
        if (this.stackMin.isEmpty()) {
            this.stackMin.push(newNum);
        } else if (newNum < this.getmin()) {
            this.stackMin.push(newNum);
        } else {
            int newMin = this.stackMin.peek();
            this.stackMin.push(newMin);
        }
        this.stackData.push(newNum);
    }

    public int pop() {
        if (this.stackData.isEmpty()) {
            throw new RuntimeException("Your stack is empty.");
        }
        this.stackMin.pop();
        return this.stackData.pop();
    }

    public int getmin() {
        if (this.stackMin.isEmpty()) {
            throw new RuntimeException("Your stack is empty.");
        }
        return this.stackMin.peek();
    }
}

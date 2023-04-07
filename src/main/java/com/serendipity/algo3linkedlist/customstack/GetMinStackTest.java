package com.serendipity.algo3linkedlist.customstack;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/04/06/23:56
 */
public class GetMinStackTest {

    public static void main(String[] args) {
        GetMinStack1 stack1 = new GetMinStack1();
        stack1.push(3);
        System.out.println(stack1.getmin());
        stack1.push(4);
        System.out.println(stack1.getmin());
        stack1.push(1);
        System.out.println(stack1.getmin());
        System.out.println(stack1.pop());
        System.out.println(stack1.getmin());

        System.out.println("=============");

        GetMinStack2 stack2 = new GetMinStack2();
        stack2.push(3);
        System.out.println(stack2.getmin());
        stack2.push(4);
        System.out.println(stack2.getmin());
        stack2.push(1);
        System.out.println(stack2.getmin());
        System.out.println(stack2.pop());
        System.out.println(stack2.getmin());
    }
}

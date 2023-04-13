package com.serendipity.algo10topological;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 图的深度优先搜索
 * @date 2022/12/17/17:16
 */
public class DFS {

    public static void dfs(Node node) {
        if (node == null) {
            return;
        }

        // 栈现金后出的特点，将一个路径上的所有节点依次压栈
        Stack<Node> stack = new Stack<>();
        Set<Node> set = new HashSet<>();
        stack.add(node);
        set.add(node);
        System.out.print(node.value + "\t");
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.print(next.value + "\t");
                    // 只压栈一个相邻节点，所以终止循环
                    break;
                }
            }
        }
    }
}

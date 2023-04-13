package com.serendipity.algo10topological;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author jack
 * @version 1.0
 * @description 宽度优先搜索
 * @date 2022/12/17/16:48
 */
public class BFS {

    // 宽度优先搜索，首先处理相邻节点
    public static void bfs(Node node) {
        if (node == null) {
            return;
        }

        // 队列保存当前遍历的节点
        Queue<Node> queue = new LinkedList<>();
        // set用于去重
        Set<Node> set = new HashSet<>();
        queue.add(node);
        set.add(node);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur.value);
            // 将当前节点的所有相邻节点入队
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    set.add(next);
                    queue.add(next);
                }
            }
        }
    }
}

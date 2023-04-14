package com.serendipity.algo10topological;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author jack
 * @version 1.0
 * @description 拓扑排序BFS
 *              https://www.lintcode.com/problem/topological-sorting
 *
 *              给定一个有向图，图节点的拓扑排序定义如下:
 *              对于图中的每一条有向边 A -> B , 在拓扑排序中A一定在B之前.
 *              拓扑排序中的第一个节点可以是图中的任何一个没有其他节点指向它的节点.
 *              针对给定的有向图找到任意一种拓扑排序的顺序.
 * @date 2023/04/14/3:54
 */
public class TopologicalOrderBFS {

    public static void main(String[] args) {
        // TODO 对数器
    }

    // 拓扑排序要求有向无环图DAG，graph为图中所有节点集合
    public static List<DirectedGraphNode> topoSort(List<DirectedGraphNode> graph) {
        // 将图中所有节点的入度初始化为0
        Map<DirectedGraphNode, Integer> indegreeMap = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            indegreeMap.put(cur, 0);
        }

        // 遍历计算所有节点的入度
        for (DirectedGraphNode cur : graph) {
            for (DirectedGraphNode next : cur.neighbors) {
                // 已经初始化为0，indegreeMap.get(neighbor)一定不为null
                indegreeMap.put(next, indegreeMap.get(next) + 1);
            }
        }

        // 拿到所有入度为0的节点
        Queue<DirectedGraphNode> zeroQueue = new LinkedList<>();
        for (DirectedGraphNode cur : indegreeMap.keySet()) {
            if (indegreeMap.get(cur) == 0) {
                zeroQueue.add(cur);
            }
        }

        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        while (!zeroQueue.isEmpty()) {
            DirectedGraphNode cur = zeroQueue.poll();
            ans.add(cur);
            for (DirectedGraphNode next : cur.neighbors) {
                // 所有neighbors的入度减1
                indegreeMap.put(next, indegreeMap.get(next) - 1);
                if (indegreeMap.get(next) == 0) {
                    zeroQueue.offer(next);
                }
            }
        }
        return ans;
    }
}

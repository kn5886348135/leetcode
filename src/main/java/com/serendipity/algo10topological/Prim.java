package com.serendipity.algo10topological;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author jack
 * @version 1.0
 * @description 最小生成树，无向图
 * @date 2022/12/18/18:53
 */
public class Prim {

    public static void main(String[] args) {
        // TODO 对数器
    }

    // 可以从任意节点出发来寻找最小生成树
    // 某个点加入到被选取的点中后，解锁这个点出发的所有新的边
    // 在所有解锁的边中选最小的边，然后看看这个边会不会形成环
    // 如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3
    // 如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2
    // 当所有点都被选取，最小生成树就得到了
    public static Set<Edge> primMST(Graph graph) {
        // 解锁的边进入小根堆
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
        // 解锁的点
        Set<Node> nodeSet = new HashSet<>();
        // 返回的结果集
        Set<Edge> result = new HashSet<>();

        // 任意选择一个节点
        for (Node node : graph.nodes.values()) {
            // 未解锁的点
            if (!nodeSet.contains(node)) {
                nodeSet.add(node);
                // 解锁相邻的边
                for (Edge edge : node.edges) {
                    priorityQueue.add(edge);
                }
                while (!priorityQueue.isEmpty()) {
                    // 弹出解锁的边中，最小的边
                    Edge edge = priorityQueue.poll();
                    // 弹出解锁的边中，最小的边
                    Node toNode = edge.to;
                    // 跳过已经解锁的点
                    if (!nodeSet.contains(toNode)) {
                        // 不含有的时候，就是新的点
                        nodeSet.add(toNode);
                        result.add(edge);
                        for (Edge edgeNext : toNode.edges) {
                            priorityQueue.add(edgeNext);
                        }
                    }
                }
            }
            // 如果确定不是一个森林，可以使用break跳过
            // break;
        }
        return result;
    }

    // 请保证graph是连通图
    // graph[i][j]表示点i到点j的距离，系统最大值代表无法到达
    // 返回值是最小连通图的路径之和
    public static int prim(int[][] graph) {
        int size = graph.length;
        int[] distances = new int[size];
        boolean[] visit = new boolean[size];
        visit[0] = true;
        for (int i = 0; i < size; i++) {
            distances[i] = graph[0][i];
        }
        int sum = 0;
        for (int i = 1; i < size; i++) {
            int minPath = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distances[j] < minPath) {
                    minPath = distances[j];
                    minIndex = j;
                }
            }
            if (minIndex == -1) {
                return sum;
            }
            visit[minIndex] = true;
            sum += minPath;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distances[j] > graph[minIndex][j]) {
                    distances[j] = graph[minIndex][j];
                }
            }
        }
        return sum;
    }
}

package com.serendipity.skills.otherdp2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author jack
 * @version 1.0
 * @description 最大网络流算 dinic算法
 * @date 2023/03/29/15:35
 */
public class DinicAlgorithm {

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int cases = cin.nextInt();
        for (int i = 1; i <= cases; i++) {
            int n = cin.nextInt();
            int s = cin.nextInt();
            int t = cin.nextInt();
            int m = cin.nextInt();
            Dinic dinic = new Dinic(n);
            for (int j = 0; j < m; j++) {
                int from = cin.nextInt();
                int to = cin.nextInt();
                int weight = cin.nextInt();
                dinic.addEdge(from, to, weight);
                dinic.addEdge(to, from, weight);
            }
            int ans = dinic.maxFlow(s, t);
            System.out.println("Case " + i + ": " + ans);
        }
        cin.close();
    }

    public static class Edge {
        public int from;
        public int to;
        public int available;

        public Edge(int from, int to, int available) {
            this.from = from;
            this.to = to;
            this.available = available;
        }
    }

    public static class Dinic {
        private int len;
        private ArrayList<ArrayList<Integer>> nexts;
        private ArrayList<Edge> edges;
        private int[] depth;
        private int[] cur;

        public Dinic(int len) {
            this.len = len + 1;
            this.nexts = new ArrayList<>();
            for (int i = 0; i <= this.len; i++) {
                this.nexts.add(new ArrayList<>());
            }
            this.edges = new ArrayList<>();
            this.depth = new int[this.len];
            this.cur = new int[this.len];
        }

        public void addEdge(int u, int v, int r) {
            int m = this.edges.size();
            this.edges.add(new Edge(u, v, r));
            this.nexts.get(u).add(m);
            this.edges.add(new Edge(v, u, 0));
            this.nexts.get(v).add(m + 1);
        }

        public int maxFlow(int s, int t) {
            int flow = 0;
            while (bfs(s, t)) {
                Arrays.fill(this.cur, 0);
                flow += dfs(s, t, Integer.MAX_VALUE);
                Arrays.fill(this.depth, 0);
            }
            return flow;
        }

        private boolean bfs(int s, int t) {
            LinkedList<Integer> queue = new LinkedList<>();
            queue.addFirst(s);
            boolean[] visited = new boolean[this.len];
            visited[s] = true;
            while (!queue.isEmpty()) {
                int u = queue.pollLast();
                for (int i = 0; i < this.nexts.get(u).size(); i++) {
                    Edge e = this.edges.get(this.nexts.get(u).get(i));
                    int v = e.to;
                    if (!visited[v] && e.available > 0) {
                        visited[v] = true;
                        this.depth[v] = this.depth[u] + 1;
                        if (v == t) {
                            break;
                        }
                        queue.addFirst(v);
                    }
                }
            }
            return visited[t];
        }

        // 当前来到了s点，s可变
        // 最终目标是t，t固定参数
        // r，收到的任务
        // 收集到的流，作为结果返回，ans <= r
        private int dfs(int s, int t, int r) {
            if (s == t || r == 0) {
                return r;
            }
            int f = 0;
            int flow = 0;
            // s点从哪条边开始试 -> cur[s]
            for (; this.cur[s] < this.nexts.get(s).size(); this.cur[s]++) {
                int ei = this.nexts.get(s).get(this.cur[s]);
                Edge e = this.edges.get(ei);
                Edge o = this.edges.get(ei ^ 1);
                if (this.depth[e.to] == this.depth[s] + 1 && (f = dfs(e.to, t, Math.min(e.available, r))) != 0) {
                    e.available -= f;
                    o.available += f;
                    flow += f;
                    r -= f;
                    if (r <= 0) {
                        break;
                    }
                }
            }
            return flow;
        }
    }
}

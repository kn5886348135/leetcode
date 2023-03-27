package com.serendipity.skills.statecompression;

/**
 * @author jack
 * @version 1.0
 * @description 你有无限的1*2的砖块，要铺满M*N的区域，
 *              不同的铺法有多少种?
 * @date 2023/03/27/12:56
 */
public class PavingTile {

    public static void main(String[] args) {
        int n = 8;
        int m = 6;
        System.out.println(ways1(n, m));
        System.out.println(ways2(n, m));
        System.out.println(ways3(n, m));
        System.out.println(ways4(n, m));

        n = 10;
        m = 10;
        System.out.println("=========");
        System.out.println(ways3(n, m));
        System.out.println(ways4(n, m));
    }

    public static int ways1(int n, int m) {
        if (n < 1 || m < 1 || ((n * m) & 1) != 0) {
            return 0;
        }
        if (n == 1 || m == 1) {
            return 1;
        }
        // pre代表-1行的状况
        int[] pre = new int[m];
        for (int i = 0; i < pre.length; i++) {
            pre[i] = 1;
        }
        return process1(pre, 0, n);
    }

    /**
     * 假定level-2行及 以上都摆满了
     *
     * @param pre       level-1行的状态
     * @param level     正在level行做决定
     * @param n         一共有多少航
     * @return          返回方法数
     */
    public static int process1(int[] pre, int level, int n) {
        // base case
        if (level == n) {
            for (int i = 0; i < pre.length; i++) {
                if (pre[i] == 0) {
                    return 0;
                }
            }
            return 1;
        }
        // 没到终止行，可以选择在当前的level行摆瓷砖
        int[] op = getOp(pre);
        return dfs1(op, 0, level, n);
    }

    // op[i] == 0 可以考虑摆砖
    // op[i] == 1 只能竖着向上
    public static int dfs1(int[] op, int col, int level, int n) {
        // 在列上自由发挥，深度优先遍历，当col来到终止列，i行的决定做完了
        // 轮到i+1行，做决定
        if (col == op.length) {
            return process1(op, level + 1, n);
        }
        int ans = 0;
        // col位置不横摆
        ans += dfs1(op, col + 1, level, n);
        // col位置横摆, 向右
        if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
            op[col] = 1;
            op[col + 1] = 1;
            ans += dfs1(op, col + 2, level, n);
            op[col] = 0;
            op[col + 1] = 0;
        }
        return ans;
    }

    public static int[] getOp(int[] pre) {
        int[] cur = new int[pre.length];
        for (int i = 0; i < pre.length; i++) {
            cur[i] = pre[i] ^ 1;
        }
        return cur;
    }

    // Min (N,M) 不超过 32
    public static int ways2(int n, int m) {
        if (n < 1 || m < 1 || ((n * m) & 1) != 0) {
            return 0;
        }
        if (n == 1 || m == 1) {
            return 1;
        }
        int max = Math.max(n, m);
        int min = Math.min(n, m);
        int pre = (1 << min) - 1;
        return process2(pre, 0, max, min);
    }

    // 上一行的状态，是pre，limit是用来对齐的，固定参数不用管
    // 当前来到i行，一共N行，返回填满的方法数
    public static int process2(int pre, int i, int n, int m) {
        // base case
        if (i == n) {
            return pre == ((1 << m) - 1) ? 1 : 0;
        }
        int op = ((~pre) & ((1 << m) - 1));
        return dfs2(op, m - 1, i, n, m);
    }

    public static int dfs2(int op, int col, int level, int n, int m) {
        if (col == -1) {
            return process2(op, level + 1, n, m);
        }
        int ans = 0;
        // col位置不横摆
        ans += dfs2(op, col - 1, level, n, m);
        // col位置横摆
        if ((op & (1 << col)) == 0 && col - 1 >= 0 && (op & (1 << (col - 1))) == 0) {
            ans += dfs2((op | (3 << (col - 1))), col - 2, level, n, m);
        }
        return ans;
    }

    // 记忆化搜索的解
    // min(n,m) 不超过32
    public static int ways3(int n, int m) {
        if (n < 1 || m < 1 || ((n * m) & 1) != 0) {
            return 0;
        }
        if (n == 1 || m == 1) {
            return 1;
        }
        int max = Math.max(n, m);
        int min = Math.min(n, m);
        int pre = (1 << min) - 1;
        int[][] dp = new int[1 << min][max + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = -1;
            }
        }
        return process3(pre, 0, max, min, dp);
    }

    public static int process3(int pre, int i, int n, int m, int[][] dp) {
        if (dp[pre][i] != -1) {
            return dp[pre][i];
        }
        int ans = 0;
        if (i == n) {
            ans = pre == ((1 << m) - 1) ? 1 : 0;
        } else {
            int op = ((~pre) & ((1 << m) - 1));
            ans = dfs3(op, m - 1, i, n, m, dp);
        }
        dp[pre][i] = ans;
        return ans;
    }

    public static int dfs3(int op, int col, int level, int n, int m, int[][] dp) {
        if (col == -1) {
            return process3(op, level + 1, n, m, dp);
        }
        int ans = 0;
        ans += dfs3(op, col - 1, level, n, m, dp);
        if (col > 0 && (op & (3 << (col - 1))) == 0) {
            ans += dfs3((op | (3 << (col - 1))), col - 2, level, n, m, dp);
        }
        return ans;
    }

    // 严格位置依赖的动态规划
    public static int ways4(int n, int m) {
        if (n < 1 || m < 1 || ((n * m) & 1) != 0) {
            return 0;
        }
        if (n == 1 || m == 1) {
            return 1;
        }
        int max = Math.max(n, m);
        int min = Math.min(n, m);
        int sn = 1 << min;
        int limit = sn - 1;
        int[] dp = new int[sn];
        dp[limit] = 1;
        int[] cur = new int[sn];
        for (int level = 0; level < max; level++) {
            for (int status = 0; status < sn; status++) {
                if (dp[status] != 0) {
                    int op = (~status) & limit;
                    dfs4(dp[status], op, 0, min - 1, cur);
                }
            }
            for (int i = 0; i < sn; i++) {
                dp[i] = 0;
            }
            int[] tmp = dp;
            dp = cur;
            cur = tmp;
        }
        return dp[limit];
    }

    public static void dfs4(int way, int op, int index, int end, int[] cur) {
        if (index == end) {
            cur[op] += way;
        } else {
            dfs4(way, op, index + 1, end, cur);
            // 11 << index 可以放砖
            if (((3 << index) & op) == 0) {
                dfs4(way, op | (3 << index), index + 1, end, cur);
            }
        }
    }
}

package com.serendipity.skills.statecompression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jack
 * @version 1.0
 * @description TSP问题
 *              有N个城市，任何两个城市之间的都有距离，任何一座城市到自己的距离都为0。所有点到点的距
 *              离都存在一个N*N的二维数组matrix里，也就是整张图由邻接矩阵表示。现要求一旅行商从k城市
 *              出发必须经过每一个城市且只在一个城市逗留一次，最后回到出发的k城，返回总距离最短的路的
 *              距离。参数给定一个matrix，给定k。
 * @date 2023/03/26/23:41
 */
public class TSP {

    public static void main(String[] args) {
        int len = 10;
        int value = 100;
        System.out.println("功能测试开始");
        for (int i = 0; i < 20000; i++) {
            int[][] matrix = generateGraph(len, value);
            int origin = (int) (Math.random() * matrix.length);
            int ans1 = min3(matrix);
            int ans2 = min4(matrix);
            int ans3 = tsp2(matrix, origin);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("fuck");
            }
        }
        System.out.println("功能测试结束");

        len = 22;
        System.out.println("性能测试开始，数据规模 " + len);
        int[][] matrix = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                matrix[i][j] = (int) (Math.random() * value) + 1;
            }
        }
        for (int i = 0; i < len; i++) {
            matrix[i][i] = 0;
        }
        long start;
        long end;
        start = System.currentTimeMillis();
        min4(matrix);
        end = System.currentTimeMillis();
        System.out.println("运行时间 " + (end - start) + " 毫秒");
        System.out.println("性能测试结束");
    }

    public static int min1(int[][] matrix) {
        int len = matrix.length;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(1);
        }
        return func1(matrix, list, 0);
    }

    // start一定在list中
    // 从start出发，将list中的城市走一遍，最终回到9这座城市的最小距离
    public static int func1(int[][] matrix, List<Integer> list, int start) {
        int cityNum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                cityNum++;
            }
        }
        if (cityNum == 1) {
            return matrix[start][0];
        }
        list.set(start, null);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                int cur = matrix[start][i] + func1(matrix, list, i);
                min = Math.min(min, cur);
            }
        }
        list.set(start, 1);
        return min;
    }

    public static int min2(int[][] matrix) {
        int len = matrix.length;
        int allCity = (1 << len) - 1;
        return func2(matrix, allCity, 0);
    }

    public static int func2(int[][] matrix, int cityStatus, int start) {
        // cityStatus的二进制只有一个1
        if (cityStatus == (cityStatus & (~cityStatus + 1))) {
            return matrix[start][0];
        }
        // 把start位的1去掉
        cityStatus &= (~(1 << start));
        int min = Integer.MAX_VALUE;
        for (int move = 0; move < matrix.length; move++) {
            if ((cityStatus & (1 << move)) != 0) {
                int cur = matrix[start][move] + func2(matrix, cityStatus, move);
                min = Math.min(min, cur);
            }
        }
        cityStatus |= (1 << start);
        return min;
    }

    // 先证明存在重复的组合，再使用缓存
    public static int min3(int[][] matrix) {
        int len = matrix.length;
        int allCity = (1 << len) - 1;
        int[][] dp = new int[1 << len][len];
        for (int i = 0; i < (1 << len); i++) {
            for (int j = 0; j < len; j++) {
                dp[i][j] = -1;
            }
        }
        return func3(matrix, allCity, 0, dp);
    }

    public static int func3(int[][] matrix, int cityStatus, int start, int[][] dp) {
        if (dp[cityStatus][start] != -1) {
            return dp[cityStatus][start];
        }
        // cityStatus的二进制只有一个1
        if (cityStatus == (cityStatus & (~cityStatus + 1))) {
            dp[cityStatus][start] = matrix[start][0];
        } else {
            // 把start位的1去掉
            cityStatus &= (~(1 << start));
            int min = Integer.MAX_VALUE;
            for (int move = 0; move < matrix.length; move++) {
                if (move != start && (cityStatus & (1 << move)) != 0) {
                    int cur = matrix[start][move] + func3(matrix, cityStatus, move, dp);
                    min = Math.min(min, cur);
                }
            }
            cityStatus |= (1 << start);
            dp[cityStatus][start] = min;
        }
        return dp[cityStatus][start];
    }

    public static int min4(int[][] matrix) {
        int len = matrix.length;
        int statusNums = 1 << len;
        int[][] dp = new int[statusNums][len];
        for (int status = 0; status < statusNums; status++) {
            for (int start = 0; start < len; start++) {
                if (status == (status & (~status + 1))) {
                    dp[status][start] = matrix[start][0];
                } else {
                    // 把start位的1去掉
                    int min = Integer.MAX_VALUE;
                    int pre = status & (~(1 << start));
                    for (int i = 0; i < len; i++) {
                        if ((pre & (1 << i)) != 0) {
                            int cur = matrix[start][i] + dp[pre][i];
                            min = Math.min(min, cur);
                        }
                    }
                    dp[status][start] = min;
                }
            }
        }
        return dp[statusNums - 1][0];
    }

    public static int tsp1(int[][] matrix, int origin) {
        if (matrix == null || matrix.length < 2 || origin < 0 || origin >= matrix.length) {
            return 0;
        }
        List<Integer> cities = new ArrayList<>();
        // cities[i] != null 表示i城在集合里
        for (int i = 0; i < matrix.length; i++) {
            cities.add(1);
        }
        cities.set(origin, null);
        return process1(matrix, origin, cities, origin);
    }

    public static int process1(int[][] matrix, int aim, List<Integer> cities, int cur) {
        // 集合中是否还有城市
        boolean hasCity = false;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i) != null) {
                hasCity = true;
                cities.set(i, null);
                ans = Math.min(ans, matrix[cur][i] + process1(matrix, aim, cities, i));
                cities.set(i, 1);
            }
        }
        return hasCity ? ans : matrix[cur][aim];
    }

    public static int process2(int[][] matrix, int aim, List<Integer> cities, int cur) {
        if (cities.size() == 1) {
            return matrix[cur][aim];
        }
        cities.set(cur, null);
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i) != null) {
                int dis = matrix[cur][i] + process2(matrix, aim, cities, i);
                ans = Math.min(ans, dis);
            }
        }
        cities.set(cur, 1);
        return ans;
    }

    public static int tsp2(int[][] matrix, int origin) {
        if (matrix == null || matrix.length < 2 || origin < 0 || origin >= matrix.length) {
            return 0;
        }
        // 除去origin之后是n-1个点
        int len = matrix.length - 1;
        // 状态数量
        int s = 1 << len;
        int[][] dp = new int[s][len];
        int icity = 0;
        int kcity = 0;
        for (int i = 0; i < len; i++) {
            icity = i < origin ? i : i + 1;
            dp[0][i] = matrix[icity][origin];
        }
        for (int status = 0; status < s; status++) {
            // 尝试每一种状态
            for (int i = 0; i < len; i++) {
                dp[status][i] = Integer.MAX_VALUE;
                if ((1 << i & status) != 0) {
                    // 如果这个城市是可以枚举的，i = 6  i对应的原始城市编号icity
                    icity = i < origin ? i : i + 1;
                    for (int k = 0; k < len; k++) {
                        if ((1 << k & status) != 0) {
                            kcity = k < origin ? k : k + 1;
                            dp[status][i] = Math.min(dp[status][i], dp[status ^ (1 << i)][k] + matrix[icity][kcity]);
                        }
                    }
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < len; i++) {
            icity = i < origin ? i : i + 1;
            ans = Math.min(ans, dp[s - 1][i] + matrix[origin][icity]);
        }
        return ans;
    }

    public static int[][] generateGraph(int maxSize, int maxValue) {
        int len = (int) (Math.random() * maxSize) + 1;
        int[][] matrix = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                matrix[i][j] = (int) (Math.random() * maxValue) + 1;
            }
        }
        for (int i = 0; i < len; i++) {
            matrix[i][i] = 0;
        }
        return matrix;
    }
}

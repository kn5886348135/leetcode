package com.serendipity.algo12dynamicprogramming.recursion1;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个整型数组arr，代表数值不同的纸牌排成一条线。玩家A和玩家B依次拿走每张纸牌，规定玩家A
 *              先拿，玩家B后拿。但是每个玩家每次只能拿走最左或最右的纸牌，玩家A和玩家B都绝顶聪明。请返回
 *              最后获胜者的分数。
 * @date 2022/12/20/11:17
 */
public class CardsInLine {

    public static void main(String[] args) {
        int maxSize = 20;
        int maxValue = 13;
        int testTimes = 10000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int ans1 = win1(arr);
            int ans2 = win2(arr);
            int ans3 = win3(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("sente gote failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 根据规则，返回获胜者的分数
    private static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int first = sente1(arr, 0, arr.length - 1);
        int second = gote1(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }

    // arr[left...right]，先手获得的最好分数返回
    // sente    先手
    private static int sente1(int[] arr, int left, int right) {
        if (left == right) {
            return arr[left];
        }
        int p1 = arr[left] + gote1(arr, left + 1, right);
        int p2 = arr[right] + gote1(arr, left, right - 1);
        return Math.max(p1, p2);
    }

    // arr[left...right]，后手获得的最好分数返回
    // gote 后手
    private static int gote1(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }
        // 对手拿走了left位置的数
        int p1 = sente1(arr, left + 1, right);
        // 对手拿走了right位置的数
        int p2 = sente1(arr, left, right - 1);
        return Math.min(p1, p2);
    }

    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int length = arr.length;
        int[][] senteMap = new int[length][length];
        int[][] goteMap = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                senteMap[i][j] = -1;
                goteMap[i][j] = -1;
            }
        }
        int first = sente2(arr, 0, arr.length - 1, senteMap, goteMap);
        int second = gote2(arr, 0, arr.length - 1, senteMap, goteMap);
        return Math.max(first, second);
    }

    // arr[left...right]，先手获得的最好分数返回
    // sente    先手
    private static int sente2(int[] arr, int left, int right, int[][] senteMap, int[][] goteMap) {
        if (senteMap[left][right] != -1) {
            return senteMap[left][right];
        }
        int ans = 0;
        if (left == right) {
            ans = arr[left];
        } else {
            int p1 = arr[left] + gote2(arr, left + 1, right, senteMap, goteMap);
            int p2 = arr[right] + gote2(arr, left, right - 1, senteMap, goteMap);
            ans = Math.max(p1, p2);
        }
        senteMap[left][right] = ans;
        return ans;
    }

    // arr[left...right]，后手获得的最好分数返回
    // gote 后手
    private static int gote2(int[] arr, int left, int right, int[][] senteMap, int[][] goteMap) {
        if (goteMap[left][right] != -1) {
            return goteMap[left][right];
        }
        int ans = 0;
        if (left != right) {
            // 对手拿走了left位置的数
            int p1 = sente2(arr, left + 1, right, senteMap, goteMap);
            // 对手拿走了right位置的数
            int p2 = sente2(arr, left, right - 1, senteMap, goteMap);
            ans = Math.min(p1, p2);
        }
        goteMap[left][right] = ans;
        return ans;
    }

    public static int win3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int length = arr.length;
        int[][] senteMap = new int[length][length];
        int[][] goteMap = new int[length][length];
        for (int i = 0; i < length; i++) {
            senteMap[i][i] = arr[i];
        }

        for (int startcol = 1; startcol < length; startcol++) {
            int left = 0;
            int right = startcol;
            while (right < length) {
                senteMap[left][right] = Math.max(arr[left] + goteMap[left + 1][right], arr[right] + goteMap[left][right - 1]);
                goteMap[left][right] = Math.min(senteMap[left + 1][right], senteMap[left][right - 1]);
                left++;
                right++;
            }
        }
        return Math.max(senteMap[0][length - 1], goteMap[0][length - 1]);
    }
}

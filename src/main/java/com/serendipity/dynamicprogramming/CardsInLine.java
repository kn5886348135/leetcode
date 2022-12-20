package com.serendipity.dynamicprogramming;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个整型数组arr，代表数值不同的纸牌排成一条线。玩家A和玩家B依次拿走每张纸牌，规定玩家A
 *              先拿，玩家B后拿。但是每个玩家每次只能拿走最左或最右的纸牌，玩家A和玩家B都绝顶聪明。请返回
 *              最后获胜者的分数。
 * @date 2022/12/20/11:17
 */
public class CardsInLine {

    private static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int first = sente1(arr, 0, arr.length - 1);
        int second = gote1(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }

    // arr[left..right]，先手获得的最好分数返回
    private static int sente1(int[] arr, int left, int right) {
        if (left == right) {
            return arr[left];
        }
        int p1 = arr[left] + gote1(arr, left + 1, right);
        int p2 = arr[right] + gote1(arr, left, right - 1);
        return Math.max(p1, p2);
    }

    // arr[left..right]，后手获得的最好分数返回
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


    private static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int length = arr.length;
        int[][] fmap = new int[length][length];
        int[][] gmap = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                fmap[i][j] = -1;
                gmap[i][j] = -1;
            }
        }
        int first = sente2(arr, 0, arr.length - 1, fmap, gmap);
        int second = gote2(arr, 0, arr.length - 1, fmap, gmap);
        return Math.max(first, second);
    }

    // arr[left..right]，先手获得的最好分数返回
    private static int sente2(int[] arr, int left, int right, int[][] fmap, int[][] gmap) {
        if (fmap[left][right] != -1) {
            return fmap[left][right];
        }
        int ans = 0;
        if (left == right) {
            ans = arr[left];
        } else {
            int p1 = arr[left] + gote2(arr, left + 1, right, fmap, gmap);
            int p2 = arr[right] + gote2(arr, left, right - 1, fmap, gmap);
            ans = Math.max(p1, p2);
        }
        fmap[left][right] = ans;
        return ans;
    }

    // // arr[left..right]，后手获得的最好分数返回
    private static int gote2(int[] arr, int left, int right, int[][] fmap, int[][] gmap) {
        if (gmap[left][right] != -1) {
            return gmap[left][right];
        }
        int ans = 0;
        if (left != right) {
            // 对手拿走了left位置的数
            int p1 = sente2(arr, left + 1, right, fmap, gmap);
            // 对手拿走了right位置的数
            int p2 = sente2(arr, left, right - 1, fmap, gmap);
            ans = Math.min(p1, p2);
        }
        gmap[left][right] = ans;
        return ans;
    }


    private static int win3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int length = arr.length;
        int[][] fmap = new int[length][length];
        int[][] gmap = new int[length][length];
        for (int i = 0; i < length; i++) {
            fmap[i][i] = arr[i];
        }

        for (int startcol = 1; startcol < length; startcol++) {
            int left = 0;
            int right = startcol;
            while (right < length) {
                fmap[left][right] = Math.max(arr[left] + gmap[left + 1][right], arr[right] + gmap[left][right - 1]);
                gmap[left][right] = Math.min(fmap[left + 1][right], fmap[left][right - 1]);
                left++;
                right++;
            }
        }
        return Math.max(fmap[0][length - 1], gmap[0][length - 1]);
    }

    public static void main(String[] args) {
        int[] arr = {5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));

    }
}

package com.serendipity.dynamicprogramming;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2022/12/20/11:17
 */
public class CardsInLine {

    private static int win1(int[] arr) {
        if (arr == null || arr.length == 1) {
            return 0;
        }

        int first = f(arr, 0, arr.length - 1);
        int second = g(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }

    private static int f(int[] arr, int left, int right) {
        if (left == right) {
            return arr[left];
        }
        int p1 = arr[left] + g(arr, left + 1, right);
        int p2 = arr[right] + g(arr, left, right - 1);
        return Math.max(p1, p2);
    }

    private static int g(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }

        int p1 = f(arr, left + 1, right);
        int p2 = f(arr, left, right - 1);
        return Math.min(p1, p2);
    }


    private static int win2(int[] arr) {
        if (arr == null || arr.length == 1) {
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
        int first = f2(arr, 0, arr.length - 1, fmap, gmap);
        int second = g2(arr, 0, arr.length - 1, fmap, gmap);
        return Math.max(first, second);
    }

    private static int f2(int[] arr, int left, int right, int[][] fmap, int[][] gmap) {
        if (fmap[left][right] != -1) {
            return fmap[left][right];
        }
        int ans = 0;
        if (left == right) {
            ans = arr[left];
        } else {
            int p1 = arr[left] + g2(arr, left + 1, right, fmap, gmap);
            int p2 = arr[right] + g2(arr, left, right - 1, fmap, gmap);
            ans = Math.max(p1, p2);
        }
        fmap[left][right] = ans;
        return ans;
    }

    private static int g2(int[] arr, int left, int right,int[][] fmap, int[][] gmap) {
        if (gmap[left][right] != -1) {
            return gmap[left][right];
        }
        int ans = 0;
        if (left != right) {
            int p1 = f2(arr, left + 1, right, fmap, gmap);
            int p2 = f2(arr, left, right - 1, fmap, gmap);
            ans = Math.min(p1, p2);
        }
        gmap[left][right] = ans;
        return ans;
    }


    private static int win3(int[] arr) {
        if (arr == null || arr.length == 1) {
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
                fmap[left][right] = Math.min(fmap[left + 1][right], fmap[left][right - 1]);
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

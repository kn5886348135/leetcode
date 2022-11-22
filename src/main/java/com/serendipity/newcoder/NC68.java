package com.example.newcoder.newcoder;

public class NC68 {

    private static int[] cache = new int[50];

    public static void main(String[] args) {
        System.out.println(cache[2]);

    }

    private static int jumpFloorRecursion(int target){
        if (target <= 1) {
            return 1;
        }
        return jumpFloorRecursion(target - 1) + jumpFloorRecursion(target - 2);
    }

    private static int jumpFloorSearch(int target){
        if(target <=1){
            return 1;
        }
        if (cache[target] == 0) {
            cache[target] = jumpFloorSearch(target - 1) + jumpFloorSearch(target - 2);
            return cache[target];
        } else {
            return cache[target];
        }
    }

    // 一维动态规划
    private static int jumpFloorDP(int target){
        int a = 1, b = 1, c = 1;
        for (int i = 2; i < target + 1; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }
}

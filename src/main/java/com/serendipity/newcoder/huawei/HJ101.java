package com.serendipity.newcoder.huawei;

import java.util.Arrays;
import java.util.Scanner;

public class HJ101 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int length = in.nextInt();
            Integer[] arr = new Integer[length];
            for (int i = 0; i < length; i++) {
                arr[i] = in.nextInt();
            }
            int sort = in.nextInt();
            Arrays.sort(arr, (o1, o2) -> {
                if (sort == 0) {
                    return o1 - o2;
                } else {
                    return o2 - o1;
                }
            });
            for (Integer integer : arr) {
                System.out.println(integer);
            }
        }
    }
}

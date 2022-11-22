package com.example.newcoder.newcoder;

public class NC149 {
    public static void main(String[] args) {

//        printCompleteBinaryStr(Integer.MAX_VALUE);
//        System.out.println("===================");
//        printCompleteBinaryStr(Integer.MAX_VALUE + 1);
//        System.out.println((Integer.MAX_VALUE + 1) == Integer.MIN_VALUE);

        System.out.println("===================");
        printCompleteBinaryStr(Integer.MIN_VALUE - 1);
        printCompleteBinaryStr(Integer.MIN_VALUE);
        System.out.println(Integer.MIN_VALUE - 1);
        System.out.println("===================");

        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE<<1));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE<<1));
        System.out.println(Integer.toBinaryString((Integer.MIN_VALUE+22)<<1));
        System.out.println((Integer.MAX_VALUE + 1) == Integer.MIN_VALUE);

        int a = 1024;
        System.out.println(~a + 1);

        System.out.println(~Integer.MIN_VALUE + 1);

        printCompleteBinaryStr(1024);

    }

    private static void printCompleteBinaryStr(int num){
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
            if (i < 31 && (i + 1) % 8 == 0) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    private static int kmp(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (m > n || n == 0) {
            return 0;
        }
        int cnt = 0;
        for (int i = 0, j = 0; i < n; i++) {
            while (j > 0 && t.charAt(i) != s.charAt(j)) {
                i = i - j + 1;
                j = 0;
            }
            if (t.charAt(i) == s.charAt(j)) {
                j++;
            }
            if (j == m) {
                cnt++;
                i = i - j + 2;
                j = 0;
            }
        }
        return cnt;
    }

}

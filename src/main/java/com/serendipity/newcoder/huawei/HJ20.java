package com.serendipity.newcoder.huawei;

import java.util.Scanner;

public class HJ20 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String str = in.nextLine();
            if (str.length() <= 8) {
                System.out.println("NG");
                continue;
            }
            if (!matchSymbol(str)) {
                System.out.println("NG");
                continue;
            }
            if (!notRepeat(str)) {
                System.out.println("NG");
                continue;
            }
            System.out.println("OK");
        }
    }

    private static boolean matchSymbol(String str){
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        for (char ch : str.toCharArray()) {
            if (ch >= '0' && ch <= '9') {
                a = 1;
                continue;
            }
            if (ch >= 'a' && ch <= 'z') {
                b = 1;
                continue;
            }
            if (ch >= 'A' && ch <= 'Z') {
                c = 1;
                continue;
            }
            if (ch == ' ' || ch == '\n') {
                return false;
            }
            d = 1;
        }
        if (a + b + c + d >= 3) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean notRepeat(String str){
        int length = str.length();
        for (int i = 0; i < length - 3; i++) {
            if (str.substring(i + 3).contains(str.substring(i, i + 3))) {
                return false;
            }
        }
        return true;
    }

}

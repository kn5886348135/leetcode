package com.serendipity.newcoder.huawei;

import java.util.Scanner;

public class HJ46 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String str = in.nextLine();
            int num = in.nextInt();
            System.out.println(str.substring(0, num));
        }
    }
}

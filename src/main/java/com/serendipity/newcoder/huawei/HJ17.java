package com.serendipity.newcoder.huawei;

import java.util.Scanner;

public class HJ17 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String input = in.nextLine();
            String[] arr = input.split(";");
            int x = 0;
            int y = 0;
            for (String item : arr) {
                if (!item.matches("[ASDW][0-9]{1,2}")){
                    continue;
                }
                int val = Integer.valueOf(item.substring(1));
                switch (item.charAt(0)) {
                    case 'W':
                        y += val;
                        break;
                    case 'S':
                        y -= val;
                        break;
                    case 'A':
                        x -= val;
                        break;
                    case 'D':
                        x += val;
                        break;
                    default:
                        break;
                }
            }
            System.out.println(x + ", " + y);
        }
    }
}

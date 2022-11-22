package com.serendipity.newcoder.huawei;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HJ5 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String hex = in.nextLine();
            System.out.println(hex);
            System.out.println(Integer.parseInt(hex.substring(2), 16));
            int length = hex.length();
            int dec = 0;
            for (int i = length - 1; i > 1; i--) {
                char ch = hex.charAt(i);
                int num = convertCharToInt(ch);
                if (num == -1) {
                    System.out.println("char is not hex " + ch);
                    break;
                }
                dec += hexPlus(length - i) * num;
            }
            System.out.println("hex " + hex + "convert to decimal is " + dec);
        }
    }

    private static int convertCharToInt(char ch) {
        char[] num = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Map<String, Integer> hexMap = new HashMap<>();
        hexMap.put("a", 10);
        hexMap.put("b", 11);
        hexMap.put("c", 12);
        hexMap.put("d", 13);
        hexMap.put("e", 14);
        hexMap.put("f", 15);

        for (char c : num) {
            if (ch == c) {
                return Integer.valueOf(String.valueOf(c));
            }
        }
        if (hexMap.containsKey(String.valueOf(ch).toLowerCase())) {
            return hexMap.get(String.valueOf(ch).toLowerCase());
        } else {
            return -1;
        }
    }

    private static int hexPlus(int index) {
        int dec = 1;
        for (int i = 1; i < index; i++) {
            dec = dec * 16;
        }
        return dec;
    }

}

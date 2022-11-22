package com.serendipity.newcoder.huawei;

import java.util.Scanner;

public class HJ33 {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String str = in.nextLine();
            if (str.contains(".")) {
                String[] arr = str.split("\\.");
                String binStr = "";
                for (int i = 0; i < 4; i++) {
                    binStr = binStr + toBinaryString(Integer.toBinaryString(Integer.valueOf(arr[i])));
                }
                System.out.println(Long.parseLong(binStr, 2));
            } else {
                String binStr = to32BinaryString(Long.toBinaryString(Long.valueOf(str)));
                String result = "";
                String ipStr = "";
                for (int i = 0; i < 4; i++) {
                    ipStr = String.valueOf(Integer.parseInt(binStr.substring(i * 8, (i + 1) * 8), 2));
                    if (i > 0) {
                        result = result + ".";
                    }
                    result = result + ipStr;
                }
                System.out.println(result);
            }
        }
    }

    private static String toBinaryString(String str){
        while (str.length() < 8) {
            str = "0" + str;
        }
        return str;
    }

    private static String to32BinaryString(String str){
        while (str.length() < 32) {
            str = "0" + str;
        }
        return str;
    }

}

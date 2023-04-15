package com.serendipity.newcoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NC61 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String input = in.nextLine();
            String[] splArr = input.split("],");
            String[] numbers = splArr[0].replace("[", "").split(",");
            String target = splArr[1];
            Map<String, String> map = new HashMap<>();
            int length = numbers.length;
            int indexa = -1;
            int indexb = -1;
            for (int i = 0; i < length; i++) {
                String key = String.valueOf(Long.valueOf(target) - Long.valueOf(numbers[i]));
                if (map.containsKey(key)) {
                    indexa = Integer.valueOf(map.get(key)) + 1;
                    indexb = i + 1;
                    break;
                } else {
                    map.put(numbers[i], String.valueOf(i));
                }
            }
            System.out.println("[" + indexa + ", " + indexb + "]");
        }
    }
}

package com.serendipity.newcoder.huawei;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HJ23 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String str = in.nextLine();
            Map<Character, Integer> map = new HashMap<>();
            for (Character ch : str.toCharArray()) {
                if (map.containsKey(ch)) {
                    Integer value = map.get(ch);
                    map.put(ch, value + 1);
                } else {
                    map.put(ch, 1);
                }
            }
            int min = Collections.min(map.values());
            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                if (entry.getValue() == min) {
                    str = str.replaceAll(String.valueOf(entry.getKey()), "");
                }
            }
            System.out.println(str);
        }
    }
}

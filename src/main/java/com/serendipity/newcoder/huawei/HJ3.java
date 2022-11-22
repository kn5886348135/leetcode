package com.serendipity.newcoder.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class HJ3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int a = in.nextInt();
            List<Integer> randoms = new ArrayList<>();
            Map<Integer,Integer> map = new TreeMap<>();
            for(int i = 0; i < a; i++){
                if (in.hasNextInt()) {
                    System.out.println("input index " + i);
                    int item = in.nextInt();
                    randoms.add(item);
                    map.put(item, 1);
                }
            }
            for(Integer item: map.keySet()){
                System.out.println(item);
            }
        }
    }
}

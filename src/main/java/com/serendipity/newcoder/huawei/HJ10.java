package com.serendipity.newcoder.huawei;

import java.util.BitSet;
import java.util.Scanner;

public class HJ10 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String str = in.nextLine();
            BitSet bitSet = new BitSet(128);
            for (Character item : str.toCharArray()) {
                if (!bitSet.get(item)) {
                    bitSet.set(item);
                }
            }
            System.out.println(bitSet.cardinality());
        }
    }
}

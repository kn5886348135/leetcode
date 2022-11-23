package com.serendipity.bitmap;

import java.util.HashSet;
import java.util.Set;

public class BitMap {
    private long[] bits;

    public BitMap(int max) {
        bits = new long[(max + 64) >> 6];
    }

    // 给定数字除以64的结果result，模64的结果mod分开保存，bits[result]位置的mod位设置为1
    public void add(int num){
        // num&63等于num%64
        bits[num >> 6] |= (1L << (num & 63));
    }

    public void delete(int num){
        bits[num >> 6] &= ~(1L << (num & 63));
    }

    public boolean contains(int num){
        return (bits[num >> 6] & (1L << (num & 63))) != 0;
    }

    public static void main(String[] args) {
        int max = 10000;
        BitMap bitMap = new BitMap(max);
        Set<Integer> set = new HashSet<>();
        int times = 10000000;
        for (int i = 0; i < times; i++) {
            int num = (int) (Math.random() * (max + 1));
            double decide = Math.random();
            if (decide < 0.333) {
                bitMap.add(num);
                set.add(num);
            } else if (decide < 0.666) {
                bitMap.delete(num);
                set.remove(num);
            } else {
                if (bitMap.contains(num) != set.contains(num)) {
                    System.out.println("0ops");
                    break;
                }
            }
        }

        for (int i = 0; i <= max; i++) {
            if (bitMap.contains(i) != set.contains(i)) {
                System.out.println("0ops");
            }
        }
    }
}

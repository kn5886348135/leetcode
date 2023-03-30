package com.serendipity.bitoperation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author jack
 * @version 1.0
 * @description arr中只有一个数出现了K次，其他数都出现了M次，找出出现了K次的数
 * @date 2023/03/30/15:41
 */
public class KM {
    
    // arr中只有一个数出现了K次，其他数都出现了M次，找出出现了K次的数
    public static void main(String[] args) {
        int len = 100;
        int range = 200;
        int testTime = 100000;
        int max = 9;
        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * max + 1);
            int b = (int) (Math.random() * max + 1);
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            if (k == m) {
                m++;
            }

            int[] arr = randomArr(len, range, k, m);
            int ans1 = onlyKTimes(arr, k, m);
            int ans2 = onlyKTimesMap(arr, k, m);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
    }

    private static int onlyKTimes(int[] arr, int k, int m) {
        int[] t = new int[32];
        // 假设a出现k次，b出现m次
        // 将所有数字的二进制位相加，则t[i] % m就是a的二进制在i位置次数，要么是0，要么是k
        for (int num : arr) {
            for (int i = 0; i <= 31; i++) {
                t[i] += (num >> i) & 1;
            }
        }

        int ans = 0;
        // 异或拿到a的每一位二进制
        for (int i = 0; i < 32; i++) {
            if (t[i] % m == 0) {
                continue;
            }
            if (t[i] % m == k) {
                ans |= (1 << i);
            } else {
                return -1;
            }
        }
        if (ans == 0) {
            int count = 0;
            for (int num : arr) {
                if (num == 0) {
                    count++;
                }
            }
            if (count != k) {
                return -1;
            }
        }
        return ans;
    }

    private static int onlyKTimesMap(int[] arr,int k,int m) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            }else {
                map.put(num, 1);
            }
        }
        for (Integer integer : map.keySet()) {
            if (map.get(integer) == k) {
                return integer;
            }
        }
        return -1;
    }

    private static int[] randomArr(int maxKinds, int range, int k, int m) {
        int ktimeNum = randomNumber(range);
        int numKinds = (int) (Math.random() * maxKinds) + 2;
        int[] arr = new int[k + (numKinds - 1) * m];
        int index = 0;
        while (index < k) {
            arr[index] = ktimeNum;
        }
        numKinds--;
        HashSet<Integer> set = new HashSet<>();
        set.add(ktimeNum);
        while (numKinds != 0) {
            int curNum = 0;
            do {
                curNum = randomNumber(range);
            } while (set.contains(curNum));
            set.add(curNum);
            numKinds--;
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }
        }
        // 打乱顺序
        for (int i = 0; i < arr.length; i++) {
            int j = (int) (Math.random() * arr.length);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }

    private static int randomNumber(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }
}

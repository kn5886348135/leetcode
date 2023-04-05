package com.serendipity.algo2bitoperation;

import com.serendipity.common.CommonUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author jack
 * @version 1.0
 * @description 一个数组中有一种数出现K次，其他数都出现了M次，M > 1,  K < M。找到出现了K次的数。
 *              要求，额外空间复杂度O(1)，时间复杂度O(N)
 * @date 2023/03/30/15:41
 */
public class KM {

    public static HashMap<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) {
        int kinds = 5;
        int len = 100;
        int range = 200;
        int testTime = 100000;
        int max = 9;

        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * max) + 1;
            int b = (int) (Math.random() * max) + 1;
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            // k < m
            if (k == m) {
                m++;
            }
            int[] arr = randomArr(kinds, range, k, m);
            int ans1 = verifyOnlyKTimes(arr, k, m);
            int ans2 = onlyKTimes1(arr, k, m);
            int ans3 = km1(arr, k, m);
            int ans4 = onlyKTimes(arr, k, m);
            if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
                System.out.println(ans1);
                System.out.println(ans3);
                System.out.println("出错了！");
            }
        }
    }

    // 获取所有元素的二进制，使用辅助数组或者map保存二进制中1的个数
    // 出现k次数的二进制位1的累加和对m取模一定是k的整数倍
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
            // a在i这个二进制位是0
            if (t[i] % m == 0) {
                continue;
            }
            // a在i这个二进制位是1
            if (t[i] % m == k) {
                ans |= (1 << i);
            } else {
                return -1;
            }
        }
        return ans;
    }

    // 对数器
    public static int verifyOnlyKTimes(int[] arr, int k, int m) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            }else {
                map.put(num, 1);
            }
        }
        for (Integer num : map.keySet()) {
            if (map.get(num) == k) {
                return num;
            }
        }
        return Integer.MAX_VALUE;
    }

    private static int[] randomArr(int maxKinds, int range, int k, int m) {
        int ktimeNum = randomNumber(range);
        int numKinds = (int) (Math.random() * maxKinds) + 2;
        int[] arr = new int[k + (numKinds - 1) * m];
        int index = 0;
        for (; index < k; index++) {
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
            CommonUtil.swap(arr, i, j);
        }
        return arr;
    }

    private static int randomNumber(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }

    // 请保证arr中，只有一种数出现了K次，其他数都出现了M次
    public static int onlyKTimes1(int[] arr, int k, int m) {
        if (map.size() == 0) {
            mapCreater1(map);
        }
        int[] t = new int[32];
        // t[0] 0位置的1出现了几个
        // t[i] i位置的1出现了几个
        for (int num : arr) {
            while (num != 0) {
                int rightOne = num & (-num);
                t[map.get(rightOne)]++;
                num ^= rightOne;
            }
        }
        int ans = 0;
        // 如果这个出现了K次的数，就是0
        // 那么下面代码中的 : ans |= (1 << i);
        // 就不会发生
        // 那么ans就会一直维持0，最后返回0，也是对的！
        for (int i = 0; i < 32; i++) {
            if (t[i] % m != 0) {
                ans |= (1 << i);
            }
        }
        return ans;
    }

    public static void mapCreater1(HashMap<Integer, Integer> map) {
        int value = 1;
        for (int i = 0; i < 32; i++) {
            map.put(value, i);
            value <<= 1;
        }
    }

    // 更简洁的写法
    public static int km1(int[] arr, int k, int m) {
        int[] help = new int[32];
        for (int num : arr) {
            for (int i = 0; i < 32; i++) {
                help[i] += (num >> i) & 1;
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            help[i] %= m;
            if (help[i] != 0) {
                ans |= 1 << i;
            }
        }
        return ans;
    }
}

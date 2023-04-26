package com.serendipity.algo24suffixarray;

/**
 * @author jack
 * @version 1.0
 * @description DC3算法构造后缀数组
 *              1、桶排序
 *              2、将数组取模，拆分成S0、S1、S2
 *                  如果S1S2是有序的，则可以得到S0排序，怎么证明?
 *                  因为取模划分出来S1、S2、S3
 *                  0开始的字符串可以用0+S1[1]表示
 *                  3开始的字符串可以用0+S1[2]表示
 *                  S0可以用二维的桶排序拿到顺序
 *              3、S0、S12都有序，通过merge就可以拿到整体的顺序，最多3次必定可以确定顺序
 *              4、怎么拿到S12的顺序?
 *                  a、S1、S2前三位都不相同，3维桶排序可以拿到S12的顺序
 *                  b、S1、S2前三位有重复值，S1、S2组成新数组rank，rank保存S1、S2的顺序
 *                      拿到rank的后缀数组，递归可以得到rank的排序，从而确定S12的顺序。
 *                  c、S1、S2分左右两部分是为了对前三位重复的字符串排序，从第四位开始就是S1、S2的
 *                      下一个元素，所以S1放一起，S2放一起，包括跨S1、S2的场景
 * @date 2023/03/27/12:56
 */
public class DC3 {

    // 按照顺序排列的下标
    public int[] sa;
    // 按照下标排列的顺序
    public int[] rank;
    public int[] height;

    // 构造方法的约定:
    // 数组叫nums，如果你是字符串，请转成整型数组nums
    // 数组中，最小值>=1
    // 如果不满足，处理成满足的，也不会影响使用
    // max, nums里面最大值是多少
    public DC3(int[] nums, int max) {
        this.sa = sa(nums, max);
        this.rank = rank();
        this.height = height(nums);
    }

    public static void main(String[] args) {
        int len = 100000;
        int maxValue = 100;
        long start = System.currentTimeMillis();
        new DC3(randomArray(len, maxValue), maxValue);
        long end = System.currentTimeMillis();
        System.out.println("数据量 " + len + ", 运行时间 " + (end - start) + " ms");
    }

    private int[] sa(int[] nums, int max) {
        int len = nums.length;
        // nums长度可能小于3
        int[] arr = new int[len + 3];
        for (int i = 0; i < len; i++) {
            arr[i] = nums[i];
        }
        return skew(arr, len, max);
    }

    private int[] skew(int[] nums, int n, int max) {
        int n0 = (n + 2) / 3, n1 = (n + 1) / 3, n2 = n / 3, n02 = n0 + n2;
        int[] s12 = new int[n02 + 3], sa12 = new int[n02 + 3];
        for (int i = 0, j = 0; i < n + (n0 - n1); ++i) {
            if (0 != i % 3) {
                s12[j++] = i;
            }
        }
        // 基数排序
        radixPass(nums, s12, sa12, 2, n02, max);
        radixPass(nums, sa12, s12, 1, n02, max);
        radixPass(nums, s12, sa12, 0, n02, max);
        int name = 0, c0 = -1, c1 = -1, c2 = -1;
        for (int i = 0; i < n02; ++i) {
            if (c0 != nums[sa12[i]] || c1 != nums[sa12[i] + 1] || c2 != nums[sa12[i] + 2]) {
                name++;
                c0 = nums[sa12[i]];
                c1 = nums[sa12[i] + 1];
                c2 = nums[sa12[i] + 2];
            }
            if (1 == sa12[i] % 3) {
                s12[sa12[i] / 3] = name;
            } else {
                s12[sa12[i] / 3 + n0] = name;
            }
        }
        // 递归
        if (name < n02) {
            sa12 = skew(s12, n02, name);
            for (int i = 0; i < n02; i++) {
                s12[sa12[i]] = i + 1;
            }
        } else {
            for (int i = 0; i < n02; i++) {
                sa12[s12[i] - 1] = i;
            }
        }
        // 拿到s0的顺序
        int[] s0 = new int[n0], sa0 = new int[n0];
        for (int i = 0, j = 0; i < n02; i++) {
            if (sa12[i] < n0) {
                s0[j++] = 3 * sa12[i];
            }
        }
        // 拿到全部顺序
        radixPass(nums, s0, sa0, 0, n0, max);
        int[] sa = new int[n];
        for (int p = 0, t = n0 - n1, k = 0; k < n; k++) {
            int i = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
            int j = sa0[p];
            if (sa12[t] < n0 ? leq(nums[i], s12[sa12[t] + n0], nums[j], s12[j / 3]) :
                    leq(nums[i], nums[i + 1], s12[sa12[t] - n0 + 1], nums[j], nums[j + 1], s12[j / 3 + n0])) {
                sa[k] = i;
                t++;
                if (t == n02) {
                    for (k++; p < n0; p++, k++) {
                        sa[k] = sa0[p];
                    }
                }
            } else {
                sa[k] = j;
                p++;
                if (p == n0) {
                    for (k++; t < n02; t++, k++) {
                        sa[k] = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                    }
                }
            }
        }
        return sa;
    }

    private void radixPass(int[] nums, int[] input, int[] output, int offset, int n, int k) {
        int[] cnt = new int[k + 1];
        for (int i = 0; i < n; ++i) {
            cnt[nums[input[i] + offset]]++;
        }
        for (int i = 0, sum = 0; i < cnt.length; ++i) {
            int t = cnt[i];
            cnt[i] = sum;
            sum += t;
        }
        for (int i = 0; i < n; ++i) {
            output[cnt[nums[input[i] + offset]]++] = input[i];
        }
    }

    private boolean leq(int a1, int a2, int b1, int b2) {
        return a1 < b1 || (a1 == b1 && a2 <= b2);
    }

    private boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
        return a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3));
    }

    private int[] rank() {
        int len = this.sa.length;
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[this.sa[i]] = i;
        }
        return ans;
    }

    // h[i-1] - 1 <= h[i] 怎么证明？
    // h数组做到不回退的优化，遍历一次拿到height数组
    // 用rank[]跳过了h[]数组
    private int[] height(int[] s) {
        int len = s.length;
        int[] ans = new int[len];
        for (int i = 0, k = 0; i < len; ++i) {
            if (this.rank[i] != 0) {
                if (k > 0) {
                    --k;
                }
                int j = this.sa[this.rank[i] - 1];
                while (i + k < len && j + k < len && s[i + k] == s[j + k]) {
                    ++k;
                }
                ans[this.rank[i]] = k;
            }
        }
        return ans;
    }

    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }
}

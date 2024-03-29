package com.serendipity.algo24suffixarray;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 最长公共子串问题
 *              LeetCode1143
 *              假设str1长度N，str2长度M，求str1和str2的最长公共子串。
 *              动态规划的时间复杂度O(N*M)
 *              最优解是后缀数组加height数组，可以达到O(N+M)
 *              首先需要用到DC3算法得到后缀数组(sa)
 *              然后用sa数组去生成height数组，并且在生成的时候，还有一个不回退的优化
 * @date 2023/03/28/17:49
 */
public class LongestCommonSubstringConquerByHeight {

    public static void main(String[] args) {
        functionTest();
        performanceTest();
    }

    private static void functionTest() {
        int maxSize = 30;
        int possibilities = 5;
        int testTime = 100000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            String str1 = CommonUtil.generateRandomString(possibilities, maxSize);
            String str2 = CommonUtil.generateRandomString(possibilities, maxSize);
            int ans1 = lcs1(str1, str2);
            int ans2 = lcs2(str1, str2);
            if (ans1 != ans2) {
                System.out.println(MessageFormat.format("lsc failed, str1 {0}, str2 {1}, ans1 {2} , ans2 {3}",
                        new String[]{str1, str2, String.valueOf(ans1), String.valueOf(ans2)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    private static void performanceTest() {
        int maxSize = 80000;
        int possibilities = 26;
        int testTime = 10000;
        long total1 = 0;
        long total2 = 0;
        for (int i = 0; i < testTime; i++) {
            String str1 = CommonUtil.generateRandomString(possibilities, maxSize);
            String str2 = CommonUtil.generateRandomString(possibilities, maxSize);
            long start = System.currentTimeMillis();
            lcs1(str1, str2);
            long end = System.currentTimeMillis();
            total1 += end - start;

            start = System.currentTimeMillis();
            lcs2(str1, str2);
            end = System.currentTimeMillis();
            total2 += end - start;
        }
        System.out.println(MessageFormat.format("lcs1 cost {0}, lcs2 cost {1}",
                new String[]{String.valueOf(total1), String.valueOf(total2)}));
    }

    // 动态规划 时间复杂度O(N * M)
    // 后缀数组 DC3算法 不回退优化O(N)
    // 对数器
    public static int lcs1(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();

        int row = 0;
        int col = chs2.length - 1;
        int max = 0;
        while (row < chs1.length) {
            int i = row;
            int j = col;
            int len = 0;
            while (i < chs1.length && j < chs2.length) {
                if (chs1[i] != chs2[j]) {
                    len = 0;
                } else {
                    len++;
                }
                if (len > max) {
                    max = len;
                }
                i++;
                j++;
            }
            if (col > 0) {
                col--;
            } else {
                row++;
            }
        }
        return max;
    }

    public static int lcs2(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int n = chs1.length;
        int m = chs2.length;
        int min = chs1[0];
        int max = chs1[0];
        for (int i = 1; i < n; i++) {
            min = Math.min(min, chs1[i]);
            max = Math.max(max, chs1[i]);
        }
        for (int i = 0; i < m; i++) {
            min = Math.min(min, chs2[i]);
            max = Math.max(max, chs2[i]);
        }
        int[] all = new int[n + m + 1];
        int index = 0;
        for (int i = 0; i < n; i++) {
            all[index++] = chs1[i] - min + 2;
        }
        all[index++] = 1;
        for (int i = 0; i < m; i++) {
            all[index++] = chs2[i] - min + 2;
        }
        DC3 dc3 = new DC3(all, max - min + 2);
        int len = all.length;
        int[] sa = dc3.sa;
        int[] height = dc3.height;
        int ans = 0;
        for (int i = 1; i < len; i++) {
            int p = sa[i - 1];
            int q = sa[i];
            if (Math.min(q, p) < n && Math.max(q, p) > n) {
                ans = Math.max(ans, height[i]);
            }
        }
        return ans;
    }

    public static class DC3 {

        public int[] sa;
        public int[] rank;
        public int[] height;

        public DC3(int[] nums, int max) {
            this.sa = sa(nums, max);
            this.rank = rank();
            this.height = height(nums);
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

        private int[] height(int[] s) {
            int len = s.length;
            int[] ans = new int[len];
            // 依次求h[i] , k = 0
            for (int i = 0, k = 0; i < len; ++i) {
                if (this.rank[i] != 0) {
                    if (k > 0) {
                        --k;
                    }
                    int j = this.sa[this.rank[i] - 1];
                    while (i + k < len && j + k < len && s[i + k] == s[j + k]) {
                        ++k;
                    }
                    // h[i] = k
                    ans[this.rank[i]] = k;
                }
            }
            return ans;
        }
    }
}